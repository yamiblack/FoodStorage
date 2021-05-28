package com.jbnu.software.foodstorage.ui.storage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jbnu.software.foodstorage.MainActivity;
import com.jbnu.software.foodstorage.R;
import com.jbnu.software.foodstorage.ui.membership.LoginActivity;
import com.jbnu.software.foodstorage.ui.mypage.RepurchaseListActivity;

import java.util.HashMap;
import java.util.Map;

public class EvaluateActivity extends AppCompatActivity {
    private Context context = this;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private Button btnLike;
    private Button btnDisLike;

    private Intent intent;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);

        btnLike = (Button) findViewById(R.id.btn_like);
        btnDisLike = (Button) findViewById(R.id.btn_dislike);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        intent = getIntent();
        name = intent.getStringExtra("product");
        Log.e("intent", name);

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> data = new HashMap<>();
                data.put("email", auth.getCurrentUser().getEmail());
                data.put("name", name);

                db.collection("Repurchase")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                startMainActivity();
                                Toast.makeText(context, "성공적으로 재구매 리스트에 추가됐습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });

                startMainActivity();
            }
        });

        btnDisLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
