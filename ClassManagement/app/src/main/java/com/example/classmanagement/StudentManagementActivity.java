package com.example.classmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StudentManagementActivity extends AppCompatActivity {
    Class classInfo;
    DBHelper dbHelper;
    ClassRepo classRepo;
    StudentRepo studentRepo;
    ArrayList<Student> students;
    StudentLVAdapter studentLVAdapter;
    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_management);
        setTitle(R.string.student_management_activity_name);
        Intent intent = getIntent();
        String classId = intent.getStringExtra("classId");
        dbHelper = new DBHelper(this);
        classRepo = new ClassRepo(dbHelper);
        studentRepo = new StudentRepo(dbHelper);
        classInfo = classRepo.findById(classId);
        students = studentRepo.loadByClassId(classId);

        TextView classIdTV = findViewById(R.id.classId);
        TextView classNameIdTV = findViewById(R.id.className);
        TextView deptTV = findViewById(R.id.dept);

        classIdTV.setText(classInfo.getId());
        classNameIdTV.setText(classInfo.getName());
        deptTV.setText(classInfo.getDept());

        studentLVAdapter = new StudentLVAdapter(this, R.layout.student_lv_item, students);
        ListView studentLV = findViewById(R.id.studentLV);
        studentLV.setAdapter(studentLVAdapter);
        registerForContextMenu(studentLV);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            reloadStudentLV();
                        }
                    }
                });

        FloatingActionButton addBtn = findViewById(R.id.addStudentBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentManagementActivity.this, AddStudentActivity.class);
                intent.putExtra("classId", classInfo.getId());
                launcher.launch(intent);
            }
        });
    }

    private void reloadStudentLV() {
        students.clear();
        students.addAll(studentRepo.loadByClassId(classInfo.getId()));
        studentLVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.studentLV) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.student_list_context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        switch (item.getItemId()) {
            case R.id.deleteStudentItem:
                studentRepo.delete(students.get(position).getId());
                reloadStudentLV();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}