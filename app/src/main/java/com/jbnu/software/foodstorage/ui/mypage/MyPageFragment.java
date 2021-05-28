package com.jbnu.software.foodstorage.ui.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.jbnu.software.foodstorage.R;
import com.jbnu.software.foodstorage.ui.membership.LoginActivity;
import com.jbnu.software.foodstorage.ui.storage.AddStorageActivity;

import java.text.ParseException;

public class MyPageFragment extends Fragment {

    private Button btnRepurchaseList;
    private Button btnLogOut;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mypage, container, false);

        btnRepurchaseList = (Button) root.findViewById(R.id.btn_repurchaseList);
        btnLogOut = (Button) root.findViewById(R.id.btn_logout);

        btnRepurchaseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RepurchaseListActivity.class));
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startLogInActivity();
            }
        });

        return root;
    }

    private void startLogInActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}