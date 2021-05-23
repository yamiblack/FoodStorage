package com.jbnu.software.foodstorage;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private static int TYPE_ITEM = 3;
    private static int TYPE_EXPIRATION = 4;

    private EditText editSearch;
    private Button btnResult;
    private RecyclerView rvSearch;
    private SearchRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Storage> searchList;
    private int type;

    private Storage testlist;
    private ArrayList<Storage> dbArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dbArrayList = new ArrayList<Storage>();
        testlist = new Storage();
        testlist.setName("우유");
        testlist.setAmount(1);
        testlist.setExpiration(2021, 05, 24);
        testlist.setRegTime(3);
        dbArrayList.add(testlist);
        testlist = new Storage();
        testlist.setName("콜라");
        testlist.setAmount(3);
        testlist.setExpiration(2021, 05, 24);
        testlist.setRegTime(2);
        dbArrayList.add(testlist);
        testlist = new Storage();
        testlist.setName("냉동삼겹살");
        testlist.setAmount(5);
        testlist.setExpiration(2022, 07, 23);
        testlist.setRegTime(1);
        testlist.setNotification(false);
        dbArrayList.add(testlist);

        Intent intent = getIntent();
        type = intent.getExtras().getInt("search_type");

        editSearch = (EditText) findViewById(R.id.editSearch);
        rvSearch = (RecyclerView) findViewById(R.id.rv_searchList);
        btnResult = findViewById(R.id.btn_result);

        searchList = new ArrayList<Storage>();
        searchList.addAll(dbArrayList);


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
                    intentR.putExtra("Result", dbArrayList);
                    setResult(RESULT_OK, intentR);
                    finish();
                }

                if (type == TYPE_EXPIRATION) {
                    Intent intentR = new Intent();
                    intentR.putExtra("Result", dbArrayList);
                    setResult(RESULT_OK, intentR);
                    finish();
                }
            }
        });

    }

    public void search(String charText) {
        if (type == TYPE_ITEM) {
            dbArrayList.clear();

            if (charText.length() == 0) {
                dbArrayList.addAll(searchList);
            } else {

                for (int i = 0; i < searchList.size(); i++) {

                    if (searchList.get(i).getName().toLowerCase().contains(charText)) {

                        dbArrayList.add(searchList.get(i));
                    }
                }
            }

            adapter.notifyDataSetChanged();
        }

        if (type == TYPE_EXPIRATION) {
            dbArrayList.clear();

            if (charText.length() == 0) {
                dbArrayList.addAll(searchList);
            } else {

                for (int i = 0; i < searchList.size(); i++) {

                    if (searchList.get(i).getExpiration().toLowerCase().contains(charText)) {

                        dbArrayList.add(searchList.get(i));
                    }
                }
            }

            adapter.notifyDataSetChanged();
        }

    }

    public void getSearchList() {
        searchList = new ArrayList<>();
        searchList.addAll(dbArrayList);
        layoutManager = new LinearLayoutManager(this);
        rvSearch.setLayoutManager(layoutManager);
        adapter = new SearchRecyclerViewAdapter(this, dbArrayList);
        rvSearch.setAdapter(adapter);
    }


}

