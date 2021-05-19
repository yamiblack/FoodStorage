package com.jbnu.software.foodstorage.ui.membership;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jbnu.software.foodstorage.R;

public class SignupActivity extends Activity {
    private static final String TAG = "SignUpActivity";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_signUp).setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_signUp:
                    signUp();
                    break;
            }
        }
    };

    private void signUp() {
        final String email = ((EditText) findViewById(R.id.et_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.et_password)).getText().toString();
        String passwordCheck = ((EditText) findViewById(R.id.et_verifyPassword)).getText().toString();

        if (email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0) {

            if (passwordCheck.equals(password)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("회원가입이 성공적으로 진행됐습니다!");

                                    AlertDialog.Builder ad = new AlertDialog.Builder(SignupActivity.this);
                                    ad.setTitle("환영합니다!");
                                    ad.setMessage("가입해주셔서 감사합니다.");
                                    ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        }
                                    });

                                    ad.show();

                                } else {
                                    Log.e(TAG, "createUserWithEmail:failure", task.getException());
                                }
                            }
                        });

            } else {
                Toast.makeText(this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
            }

        } else {
            startToast("이메일 또는 비밀번호를 입력해주세요. ");
        }

    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

