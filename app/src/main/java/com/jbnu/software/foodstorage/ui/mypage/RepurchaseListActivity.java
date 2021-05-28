package com.jbnu.software.foodstorage.ui.mypage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.jbnu.software.foodstorage.R;
import com.jbnu.software.foodstorage.adapter.RepurchaseRecyclerViewAdapter;
import com.jbnu.software.foodstorage.model.Repurchase;

import java.util.ArrayList;

public class RepurchaseListActivity extends AppCompatActivity {

    private Context context = this;

    private ArrayList<Repurchase> arrayList;
    private RecyclerView rvRepurchase;
    private RepurchaseRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repurchase);
        getSupportActionBar().setTitle("재구매 리스트");

        rvRepurchase = (RecyclerView) findViewById(R.id.rv_repurchase);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        getRepurchase();

    }

    public void getRepurchase() {
        arrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(context);
        rvRepurchase.setLayoutManager(layoutManager);
        adapter = new RepurchaseRecyclerViewAdapter(context, arrayList);
        rvRepurchase.setAdapter(adapter);

        db.collection("Repurchase").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        if (documentSnapshot.get("email").equals(auth.getCurrentUser().getEmail())) {
                            Repurchase repurchase = documentSnapshot.toObject(Repurchase.class);
                            arrayList.add(repurchase);
                        }
                    }

                    for (int i = 0; i < arrayList.size(); i++) {
                        arrayList.get(i).setNumber(String.valueOf(i + 1));
                    }

                    adapter.notifyDataSetChanged();

                    if (arrayList.size() == 0) {
                        Toast.makeText(getApplicationContext(), "현재까지 추가된 재구매 리스트가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

}
