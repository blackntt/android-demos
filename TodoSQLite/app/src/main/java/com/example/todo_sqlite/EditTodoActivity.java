package com.example.todo_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

public class EditTodoActivity extends AppCompatActivity {


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

        todoRepo = new TodoRepo(EditTodoActivity.this);
        inputIntent = getIntent();
        String id = inputIntent.getStringExtra("id");
        if(id != null){
            todoItem= todoRepo.getById(id);
            if(todoItem!=null){
                titleEditText.setText(todoItem.getTitle());
                descEditText.setText(todoItem.getDesc());
                isDoneCheckBox.setChecked(todoItem.isDone());
            }else{
                Toast.makeText(getApplicationContext(), "Item: not found", Toast.LENGTH_LONG).show();
            }
        }

        Button saveBtn = (Button) findViewById(R.id.save);
        saveBtn.setEnabled(todoItem != null);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoRepo.update(new TodoItem(todoItem.getId(), titleEditText.getText().toString(), descEditText.getText().toString(), isDoneCheckBox.isChecked() ));
                setResult(2);
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