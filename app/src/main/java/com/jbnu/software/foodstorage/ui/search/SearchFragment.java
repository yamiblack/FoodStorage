package com.jbnu.software.foodstorage.ui.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.jbnu.software.foodstorage.R;
import com.jbnu.software.foodstorage.adapter.SearchRecyclerViewAdapter;
import com.jbnu.software.foodstorage.model.Storage;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private Button searchItem;
    private Button searchExpiration;
    private RecyclerView.LayoutManager layoutManager;
    private SearchRecyclerViewAdapter adapter;
    private RecyclerView rvResult;
    private ArrayList<Storage> arrayList;

    private static int REQUEST_CODE =1;
    private static int TYPE_ITEM =3;
    private static int TYPE_EXPIRATION =4;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        searchItem = root.findViewById(R.id.search_item);
        searchExpiration = root.findViewById(R.id.search_expiration);
        searchExpiration = root.findViewById(R.id.search_expiration);
//        rvResult = (RecyclerView) root.findViewById(R.id.rv_result_list);

        searchItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("search_type", TYPE_ITEM);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        searchExpiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("search_type", TYPE_EXPIRATION);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                Log.e("LOG", "결과 받기 성공");
                ArrayList<Storage> resultList = (ArrayList<Storage>) data.getSerializableExtra("Result");
                getResultList(resultList);
            }
        }
    }

    public void getResultList(ArrayList<Storage> resultList) {
        arrayList = new ArrayList<>();
        arrayList.addAll(resultList);
        layoutManager = new LinearLayoutManager(getActivity());
        rvResult.setLayoutManager(layoutManager);
        adapter = new SearchRecyclerViewAdapter(getActivity(), resultList);
        rvResult.setAdapter(adapter);
    }

}