package com.example.loginfragmentdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    final String ACCEPTED_USERNAME = "user";
    final String ACCEPTED_PASSWORD = "123456";

    public LoginFragment(){
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
                if(username.compareTo(ACCEPTED_USERNAME) ==0 && pass.compareTo(ACCEPTED_PASSWORD) ==0 ){
                    Toast.makeText(getContext(), "Login successfully", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Login failed", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
