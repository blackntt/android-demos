package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditTodoActivity extends AppCompatActivity {

    String id;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo_detail);

        Intent inputIntent = getIntent();

        id = inputIntent.getStringExtra("id");
        index = inputIntent.getIntExtra("index", -1);
        String title = inputIntent.getStringExtra("title");
        String desc = inputIntent.getStringExtra("desc");
        boolean isDone = inputIntent.getBooleanExtra("isDone", false);

        EditText descEditText = findViewById(R.id.desc);
        EditText titleEditText = findViewById(R.id.title);
        CheckBox isDoneCheckBox = findViewById(R.id.isDone);

        descEditText.setText(desc);
        titleEditText.setText(title);
        isDoneCheckBox.setChecked(isDone);

        Button saveBtn = findViewById(R.id.save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("index",index);
                intent.putExtra("id", id);
                intent.putExtra("title", titleEditText.getText().toString());
                intent.putExtra("desc", descEditText.getText().toString());
                intent.putExtra("isDone", isDoneCheckBox.isChecked());
                setResult(2, intent);
                finish();
            }
        });
        Button cancelBtn = findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });
    }
}