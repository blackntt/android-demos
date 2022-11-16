package com.example.sayhello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText;
    private Button sayHelloButon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        sayHelloButon = (Button) findViewById(R.id.sayHelloButton);

        sayHelloButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                Toast.makeText(getApplicationContext(),"Hello "+name, Toast.LENGTH_LONG).show();
            }
        });

    }
}