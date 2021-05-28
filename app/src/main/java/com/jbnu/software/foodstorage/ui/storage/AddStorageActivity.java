package com.jbnu.software.foodstorage.ui.storage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jbnu.software.foodstorage.MainActivity;
import com.jbnu.software.foodstorage.R;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AddStorageActivity extends AppCompatActivity {

    private Context context = this;

    private EditText etName;
    private EditText etAmount;
    private EditText etExpirationDate;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private static int regTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_add);
        getSupportActionBar().setTitle("식품 추가");

        etName = (EditText) findViewById(R.id.et_name);
        etAmount = (EditText) findViewById(R.id.et_amount);
        etExpirationDate = (EditText) findViewById(R.id.et_expirationDate);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_storage_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (etName.getText().toString().length() == 0 || etAmount.getText().toString().length() == 0 || etExpirationDate.getText().toString().length() == 0) {
            Toast.makeText(context, "양식을 모두 채워주세요.", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, Object> data = new HashMap<>();
            data.put("name", etName.getText().toString());
            String expirationDate = etExpirationDate.getText().toString();
            data.put("expiration", expirationDate.replaceAll(" ", ""));
            data.put("email", auth.getCurrentUser().getEmail());
            data.put("amount", Integer.parseInt(etAmount.getText().toString()));
            data.put("regTime", regTime);
            data.put("notifyDate", 1);
            data.put("notification", true);

            db.collection("FoodStorage").document(etName.getText().toString().toString() + regTime).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(context, "성공적으로 추가됐습니다.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(context, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            });

            regTime++;
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
