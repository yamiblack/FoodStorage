package com.jbnu.software.foodstorage.ui.tutorial;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jbnu.software.foodstorage.R;

public class TutorialActivity extends AppCompatActivity {

    private Context context = this;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private static int regTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_add);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

    }

}
