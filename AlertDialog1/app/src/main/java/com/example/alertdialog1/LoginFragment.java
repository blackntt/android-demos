package com.example.alertdialog1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    final String ACCEPTED_USERNAME = "user";
    final String ACCEPTED_PASSWORD = "123456";

    public LoginFragment() {
        super(R.layout.fragment_login);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        EditText usernameET = view.findViewById(R.id.username);
        EditText passwordET = view.findViewById(R.id.password);
        Button signinBtn = view.findViewById(R.id.login);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameET.getText().toString();
                String pass = passwordET.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                if (username.compareTo(ACCEPTED_USERNAME) == 0 && pass.compareTo(ACCEPTED_PASSWORD) == 0) {
                    builder.setMessage("Đăng nhập thành công");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                } else {
                    builder.setMessage("Đăng nhập thất bại");
                    builder.setPositiveButton("Đăng nhập lại", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }
                builder.create().show();
            }
        });

    }
}
