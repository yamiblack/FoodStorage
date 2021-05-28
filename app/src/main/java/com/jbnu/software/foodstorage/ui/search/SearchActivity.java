package com.jbnu.software.foodstorage.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jbnu.software.foodstorage.MainActivity;
import com.jbnu.software.foodstorage.R;
import com.jbnu.software.foodstorage.adapter.SearchRecyclerViewAdapter;
import com.jbnu.software.foodstorage.model.Storage;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private static int TYPE_ITEM = 3;
    private static int TYPE_EXPIRATION = 4;

    private Context context = this;

    private EditText editSearch;
    private Button btnResult;
    private RecyclerView rvSearch;
    private SearchRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Storage> searchList;
    private int type;

    private Storage testlist;
    MyCallback myCallback;

    private ArrayList<Storage> arrayListDB;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setTitle("식품 검색");

        arrayListDB = new ArrayList<Storage>();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        type = intent.getExtras().getInt("search_type");

        editSearch = (EditText) findViewById(R.id.editSearch);
        rvSearch = (RecyclerView) findViewById(R.id.rv_searchList);
        btnResult = findViewById(R.id.btn_result);

        readData(new MyCallback() {
            @Override
            public void onCallback(ArrayList<Storage> eventList) {
                arrayListDB.addAll(eventList);
                if (arrayListDB.isEmpty()) {
                    Toast.makeText(context, "현재 냉장고에 아무것도 없어요", Toast.LENGTH_SHORT).show();
                } else {
                    searchList.addAll(arrayListDB);
                }

                Log.e("TAG123", arrayListDB.get(0).getName());
            }
        });

        getSearchList();

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String text = editSearch.getText().toString();
                search(text);
            }
        });

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == TYPE_ITEM) {
                    Intent intentR = new Intent();
                    intentR.putExtra("Result", arrayListDB);
                    setResult(RESULT_OK, intentR);
                    finish();
                }

                if (type == TYPE_EXPIRATION) {
                    Intent intentR = new Intent();
                    intentR.putExtra("Result", arrayListDB);
                    setResult(RESULT_OK, intentR);
                    finish();
                }
            }
        });

    }

    public void search(String charText) {
        if (type == TYPE_ITEM) {
            arrayListDB.clear();

            if (charText.length() == 0) {
                arrayListDB.addAll(searchList);
            } else {

                for (int i = 0; i < searchList.size(); i++) {

                    if (searchList.get(i).getName().toLowerCase().contains(charText)) {

                        arrayListDB.add(searchList.get(i));
                    }
                }
            }

            adapter.notifyDataSetChanged();
        }

        if (type == TYPE_EXPIRATION) {
            arrayListDB.clear();

            if (charText.length() == 0) {
                arrayListDB.addAll(searchList);
            } else {

                for (int i = 0; i < searchList.size(); i++) {

                    if (searchList.get(i).getExpiration().toLowerCase().contains(charText)) {

                        arrayListDB.add(searchList.get(i));
                    }
                }
            }

            adapter.notifyDataSetChanged();
        }

    }

    public void getSearchList() {
        searchList = new ArrayList<>();
        searchList.addAll(arrayListDB);
        layoutManager = new LinearLayoutManager(this);
        rvSearch.setLayoutManager(layoutManager);
        adapter = new SearchRecyclerViewAdapter(this, arrayListDB);
        rvSearch.setAdapter(adapter);

    }

    public void readData(MyCallback myCallback) {
        db.collection("FoodStorage").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    ArrayList<Storage> eventList = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        if (documentSnapshot.get("email").equals(auth.getCurrentUser().getEmail())) {
                            Storage storage = documentSnapshot.toObject(Storage.class);
                            eventList.add(storage);
                        }

                    }
                    adapter.notifyDataSetChanged();

                    if (!eventList.isEmpty()) {
                        myCallback.onCallback(eventList);
                    }

                }

            }
        });
    }

    public interface MyCallback {
        void onCallback(ArrayList<Storage> eventList);
    }

}

