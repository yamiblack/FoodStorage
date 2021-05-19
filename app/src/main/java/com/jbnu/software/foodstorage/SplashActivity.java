package com.jbnu.software.foodstorage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.jbnu.software.foodstorage.ui.membership.LoginActivity;

public class SplashActivity extends Activity {
    private final int SPLASH_TIME = 1500;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent((getApplication()), LoginActivity.class));
                } else {
                    startActivity(new Intent((getApplication()), MainActivity.class));
                }
            }
        }, SPLASH_TIME);

    }

}
