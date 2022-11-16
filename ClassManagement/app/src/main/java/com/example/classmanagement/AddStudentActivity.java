package com.example.classmanagement;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddStudentActivity extends AppCompatActivity {


    String classId;
    DBHelper dbHelper;
    ClassRepo classRepo;
    StudentRepo studentRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        setTitle(R.string.add_student_activity_name);
        dbHelper = new DBHelper(this);
        classRepo = new ClassRepo(dbHelper);
        studentRepo = new StudentRepo(dbHelper);
        classId = getIntent().getStringExtra("classId");
        TextInputEditText studentId = findViewById(R.id.classId);
        TextInputEditText studentName = findViewById(R.id.className);
        TextInputEditText address = findViewById(R.id.dept);
        MaterialButton addBtn = findViewById(R.id.okBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student newStudent = new Student(studentId.getText().toString(), studentName.getText().toString(), classId, address.getText().toString());
                if (!studentRepo.add(newStudent)) {
                    Toast.makeText(AddStudentActivity.this, "Add student has failed", Toast.LENGTH_LONG).show();
                } else {
                    setResult(Activity.RESULT_OK);
                    finish();
                }

            }
        });
        MaterialButton cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });


    }
}