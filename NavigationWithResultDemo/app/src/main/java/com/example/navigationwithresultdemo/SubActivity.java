package com.example.navigationwithresultdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Button okBtn = findViewById(R.id.okBtn);
        EditText nameET = findViewById(R.id.nameET);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, MainActivity.class);
                intent.putExtra("name",nameET.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}