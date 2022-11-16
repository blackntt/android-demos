package com.example.todo_sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class AddTodoActivity extends AppCompatActivity {

    Intent inputIntent;
    TodoRepo todoRepo;
    TodoItem todoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo_detail);


        EditText descEditText = (EditText) findViewById(R.id.desc);
        EditText titleEditText = (EditText) findViewById(R.id.title);
        CheckBox isDoneCheckBox = (CheckBox) findViewById(R.id.isDone);

        todoRepo = new TodoRepo(AddTodoActivity.this);
        inputIntent = getIntent();

        Button saveBtn = (Button) findViewById(R.id.save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newId = UUID.randomUUID().toString();
                todoRepo.addNew(new TodoItem(newId, titleEditText.getText().toString(), descEditText.getText().toString(), isDoneCheckBox.isChecked()));
                setResult(1);
                finish();
            }
        });
        Button cancelBtn = (Button) findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });
    }
}