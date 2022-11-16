package com.example.classmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class EditClassActivity extends AppCompatActivity {

    Class selectedClass;
    DBHelper dbHelper;
    ClassRepo classRepo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);
        setTitle(R.string.edit_class_activity_name);
        Intent intent = getIntent();
        String id = intent.getStringExtra("classId");
        dbHelper = new DBHelper(this);
        classRepo = new ClassRepo(dbHelper);

        selectedClass = classRepo.findById(id);
        TextView classTV = findViewById(R.id.classId);
        TextInputEditText classNameIdTE = findViewById(R.id.className);
        TextInputEditText deptTE = findViewById(R.id.dept);
        classTV.setInputType(InputType.TYPE_NULL);
        classTV.setText(selectedClass.getId());
        classNameIdTE.setText(selectedClass.getName());
        deptTE.setText(selectedClass.getDept());


        MaterialButton cancelBtn = findViewById(R.id.cancelBtn);
        MaterialButton okBtn = findViewById(R.id.okBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newClassName = classNameIdTE.getText().toString();
                String newDept = deptTE.getText().toString();
                selectedClass.setDept(newDept);
                selectedClass.setName(newClassName);
                if (!classRepo.update(selectedClass)) {
                    Toast.makeText(EditClassActivity.this, "Edit class has failed", Toast.LENGTH_LONG).show();
                } else {
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        });
    }

}