package com.example.logindialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class LoginDialog extends DialogFragment {

    final String ACCEPTED_USERNAME = "user";
    final String ACCEPTED_PASSWORD = "123456";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View loginView = requireActivity().getLayoutInflater().inflate(R.layout.fragment_login,null);
        builder.setView(loginView);
        EditText usernameET = loginView.findViewById(R.id.username);
        EditText passwordET = loginView.findViewById(R.id.password);
        builder.setTitle("Log in form");
        builder.setPositiveButton("Log in", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(usernameET.getText().toString().compareTo(ACCEPTED_USERNAME)==0 && passwordET.getText().toString().compareTo(ACCEPTED_PASSWORD)==0)
                    Toast.makeText(LoginDialog.this.requireActivity(), "Log-in successfully", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(LoginDialog.this.requireActivity(), "Log-in failed", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}
