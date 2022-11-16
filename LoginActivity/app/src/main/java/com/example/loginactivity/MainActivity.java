package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private final String validUsername = "guest";
    private final String validPassword = "123456";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = (EditText) findViewById(R.id.usernameEdittext);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(username.compareTo(validUsername) == 0 && password.compareTo(validPassword)==0){
                    Toast.makeText(getApplicationContext(),username + "login successfully", Toast.LENGTH_LONG).show();
                    startHomeActivity(username);
                }else{
                    Toast.makeText(getApplicationContext(),username + "login failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected void startHomeActivity(String username){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}