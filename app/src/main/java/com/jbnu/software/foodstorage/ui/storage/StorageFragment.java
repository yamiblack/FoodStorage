package com.jbnu.software.foodstorage.ui.storage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jbnu.software.foodstorage.AlarmReceiver;
import com.jbnu.software.foodstorage.R;
import com.jbnu.software.foodstorage.Storage;
import com.jbnu.software.foodstorage.StorageNotificationRecyclerViewAdapter;
import com.jbnu.software.foodstorage.StorageRecyclerViewAdapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StorageFragment extends Fragment implements View.OnClickListener {

    // Context context = this;
    private ArrayList<Storage> arrayListDB;
    private RecyclerView rvStorage;
    private StorageRecyclerViewAdapter adapter;
    private StorageNotificationRecyclerViewAdapter nAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Spinner spinner;
    private TextView itemCount;
    private boolean isNotifyView;
    private AlarmManager alarmManager;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_storage, container, false);
        setHasOptionsMenu(true);

        isNotifyView = false;
        itemCount = root.findViewById(R.id.item_count);
        spinner = root.findViewById(R.id.spinner);
        rvStorage = (RecyclerView) root.findViewById(R.id.rv_storageList);
        FloatingActionButton fab = root.findViewById(R.id.fab_notification);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        arrayListDB = new ArrayList<>();
        setTestListDB();
        db.collection("FoodStorage").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        if (documentSnapshot.get("email").equals(auth.getCurrentUser().getEmail())) {
                            Storage storage = documentSnapshot.toObject(Storage.class);
                            arrayListDB.add(storage);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    itemCount.setText(Integer.toString(arrayListDB.size()));

                    if (arrayListDB.size() == 0) {
                        Toast.makeText(getActivity().getApplicationContext(), "현재 냉장고에 아무것도 없어요", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        getStorageList();
        addSpinner();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNotifyView = !isNotifyView;
                if (!isNotifyView) {
                    fab.setImageResource(R.drawable.ic_notifications_white_24dp);
                    for (int i = 0; i < arrayListDB.size(); i++) {
                        if (arrayListDB.get(i).isNotification())
                            setAlarm(arrayListDB.get(i));
                        else ;
                    }
//                    setAlarm(testArrayList.get(1));
                    getStorageList();
                    addSpinner();

                } else {
                    fab.setImageResource(R.drawable.check_mark_white);
                    getNotifyList();
                    addSpinner();
                }

            }
        });

        return root;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setAlarm(Storage storage) {
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        //AlarmReceiver에 값 전달
        Intent receiverIntent = new Intent(getContext(), AlarmReceiver.class);
        receiverIntent.putExtra("ID", storage.getRegTime());
        receiverIntent.putExtra("name", storage.getName());
        receiverIntent.putExtra("DDay", storage.getDDay());
        receiverIntent.putExtra("isOn", storage.isNotification());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), storage.getRegTime(), receiverIntent, 0);

        String from = storage.getExpiration() + " 16:00:00";
        // "2020.05.23 10:31:00"; //임의로 날짜와 시간을 지정

        //날짜 포맷을 바꿔주는 소스코드
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date datetime = null;
        try {
            datetime = dateFormat.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);

        //알람시간 설정
        calendar.roll(Calendar.DATE, -storage.getNotifyDate());
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_storage, menu);
    }

    public void addSpinner() {

        //spinner for sorting option
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.sortingOption, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    AscendingDDay ascending = new AscendingDDay();
                    Collections.sort(arrayListDB, ascending);

                } else if (position == 1) {
                    DescendingAmount ascending = new DescendingAmount();
                    Collections.sort(arrayListDB, ascending);

                } else if (position == 2) {
                    AscendingRegTime ascending = new AscendingRegTime();
                    Collections.sort(arrayListDB, ascending);
                }
                if (!isNotifyView) {
                    getStorageList();
                } else {
                    getNotifyList();
                    notificationInit(nAdapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void getStorageList() {
//        arrayList = new ArrayList<>();
      //  arrayList.addAll(arrayList);
        layoutManager = new LinearLayoutManager(getActivity());
        rvStorage.setLayoutManager(layoutManager);
        adapter = new StorageRecyclerViewAdapter(getActivity(), arrayListDB);
        rvStorage.setAdapter(adapter);

        adapter.setOnItemClickListener(new StorageRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClearClick(View v, int position) {
                arrayListDB.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCheckClick(View v, int position) {
                arrayListDB.get(position).setAmount(adapter.getExAmount());
                arrayListDB.get(position).setExpanded(false);
                adapter.notifyDataSetChanged();
            }
        });

    }


    public void getNotifyList() {
    //    arrayList = new ArrayList<>();
      //  arrayList.addAll(arrayList);
        layoutManager = new LinearLayoutManager(getActivity());
        rvStorage.setLayoutManager(layoutManager);
        nAdapter = new StorageNotificationRecyclerViewAdapter(getActivity(), arrayListDB);
        rvStorage.setAdapter(nAdapter);

    }

    private void notificationInit(StorageNotificationRecyclerViewAdapter nAdapter) {

        nAdapter.setOnItemClickListener(new StorageNotificationRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onSwitchChange(View v, int position, boolean isChecked) {

                if (isChecked) {
                    arrayListDB.get(position).setNotification(true);
                } else {
                    arrayListDB.get(position).setNotification(false);
                }
                //
                rvStorage.post(new Runnable() {
                    @Override
                    public void run() {
                        nAdapter.notifyDataSetChanged();
                    }
                });

            }

            @Override
            public void onSelectNotifyDate(View v, int position, int sPosition) {
                arrayListDB.get(position).setNotifyDate(sPosition);
            }
        });
    }

    public void setTestListDB() {


            Map<String, Object> data = new HashMap<>();
            data.put("email", auth.getCurrentUser().getEmail());
            data.put("name", "콜라");
            data.put("amount", 3);
         //   data.put("dDay", 6);
            data.put("regTime", 2);
            data.put("notifyDate", 1);
            data.put("expanded", false);
            data.put("notification", true);
            data.put("expiration", "2021.05.33");

            db.collection("FoodStorage").document("콜라").set(data);
//                    .add(data)
//                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                        }
//                    });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fab_notification:
                isNotifyView = !isNotifyView;
                break;
        }

    }
}

class AscendingDDay implements Comparator<Storage> {

    @Override
    public int compare(Storage o1, Storage o2) {
        if (o1.getDDay() < o2.getDDay()) {
            return -1;
        } else if (o1.getDDay() > o2.getDDay()) {
            return 1;
        }
        return 0;

    }

}

class AscendingRegTime implements Comparator<Storage> {

    @Override
    public int compare(Storage o1, Storage o2) {
        if (o1.getRegTime() < o2.getRegTime()) {
            return -1;
        } else if (o1.getRegTime() > o2.getRegTime()) {
            return 1;
        }
        return 0;
    }
}

class DescendingAmount implements Comparator<Storage> {

    @Override
    public int compare(Storage o1, Storage o2) {
        if (o2.getAmount() < o1.getAmount()) {
            return -1;
        } else if (o2.getAmount() > o1.getAmount()) {
            return 1;
        }
        return 0;
    }
}
