package com.example.classmanagement;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddClassActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private ClassRepo classRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        setTitle(R.string.add_class_activity_name);
        dbHelper = new DBHelper(this);
        classRepo = new ClassRepo(dbHelper);

        MaterialButton cancelBtn = findViewById(R.id.cancelBtn);
        MaterialButton okBtn = findViewById(R.id.okBtn);


        TextInputEditText classIdTE = findViewById(R.id.classId);
        TextInputEditText classNameIdTE = findViewById(R.id.className);
        TextInputEditText deptTE = findViewById(R.id.dept);


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = classIdTE.getText().toString();
                String name = classNameIdTE.getText().toString();
                String dept = deptTE.getText().toString();

                if (!classRepo.add(new Class(id, name, dept))) {
                    Toast.makeText(AddClassActivity.this, "Add class has failed", Toast.LENGTH_LONG).show();
                } else {
                    setResult(Activity.RESULT_OK);
                    finish();
                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }
}