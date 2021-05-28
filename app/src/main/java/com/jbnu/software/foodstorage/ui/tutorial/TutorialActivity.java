package com.jbnu.software.foodstorage.ui.tutorial;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jbnu.software.foodstorage.R;

public class TutorialActivity extends AppCompatActivity {

    private Context context = this;

    TextView tvGuideClose;
    TextView tvGuidCloseForever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        tvGuideClose = (TextView) findViewById(R.id.tv_guideClose);
        tvGuidCloseForever = (TextView) findViewById(R.id.tv_guideCloseForever);

        SharedPreferences sharedPreferences = getSharedPreferences("isNew", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        tvGuideClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvGuidCloseForever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("isNew", false);
                editor.apply();
                finish();
            }
        });
    }

}
