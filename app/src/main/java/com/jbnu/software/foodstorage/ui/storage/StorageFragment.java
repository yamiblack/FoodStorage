package com.jbnu.software.foodstorage.ui.storage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jbnu.software.foodstorage.R;
import com.jbnu.software.foodstorage.RecyclerDecoration;
import com.jbnu.software.foodstorage.Storage;
import com.jbnu.software.foodstorage.StorageRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StorageFragment extends Fragment {

    // Context context = this;
    private ArrayList<Storage> arrayList;
    private RecyclerView rvStorage;
    private StorageRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Spinner spinner;
    private TextView itemCount;
    //test list
    private ArrayList<Storage> testArrayList;
    private Storage testlist;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_storage, container, false);
        setHasOptionsMenu(true);

        //test list
        testArrayList = new ArrayList<Storage>();
        testlist = new Storage();
        testlist.setName("우유");
        testlist.setAmount(1);
        testlist.setExpiration(2021, 05, 30);
        testlist.setRegTime(3);
        testArrayList.add(testlist);
        testlist = new Storage();
        testlist.setName("콜라");
        testlist.setAmount(3);
        testlist.setExpiration(2021, 05, 29);
        testlist.setRegTime(2);
        testArrayList.add(testlist);
        testlist = new Storage();
        testlist.setName("냉동삼겹살");
        testlist.setAmount(5);
        testlist.setExpiration(2022, 07, 23);
        testlist.setRegTime(1);
        testArrayList.add(testlist);
        //

        itemCount = root.findViewById(R.id.item_count);
        rvStorage = (RecyclerView) root.findViewById(R.id.rv_storageList);

        RecyclerDecoration spaceDecoration = new RecyclerDecoration(20);
        rvStorage.addItemDecoration(spaceDecoration);

        spinner = root.findViewById(R.id.spinner);

        getStorageList();
        addOptionBar();

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_storage, menu);
    }

    public void addOptionBar() {
        //get item count
        itemCount.setText(Integer.toString(testArrayList.size()));

        //sorting option spinner
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.sortingOption, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    AscendingDDay ascending = new AscendingDDay();
                    Collections.sort(testArrayList, ascending);
                    getStorageList();

                } else if (position == 1) {
                    DescendingAmount ascending = new DescendingAmount();
                    Collections.sort(testArrayList, ascending);
                    getStorageList();

                } else if (position == 2) {
                    AscendingRegTime ascending = new AscendingRegTime();
                    Collections.sort(testArrayList, ascending);
                    getStorageList();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void getStorageList() {
        arrayList = new ArrayList<>();
        arrayList.addAll(testArrayList);
        layoutManager = new LinearLayoutManager(getActivity());
        rvStorage.setLayoutManager(layoutManager);
        adapter = new StorageRecyclerViewAdapter(getActivity(), arrayList);
        rvStorage.setAdapter(adapter);

        adapter.setOnItemClickListener(new StorageRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClearClick(View v, int position) {
                testArrayList.remove(position);
                getStorageList();
            }

            @Override
            public void onCheckClick(View v, int position) {
                testArrayList.get(position).setAmount(adapter.getExAmount());
                testArrayList.get(position).setExpanded(false);
                getStorageList();
            }
        });
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
