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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ClassLVAdapter classLVAdapter;
    ActivityResultLauncher<Intent> classLaucher;
    private DBHelper dbHelper;
    private ClassRepo classRepo;
    private ArrayList<Class> classes;
    private StudentRepo studentRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);

        //DB initialization
        dbHelper = new DBHelper(this);
        classRepo = new ClassRepo(dbHelper);
        studentRepo = new StudentRepo(dbHelper);

        classes = classRepo.loadAll();
        classLVAdapter = new ClassLVAdapter(MainActivity.this, R.layout.class_lv_item, classes);

        ListView classLV = findViewById(R.id.classLV);
        classLV.setAdapter(classLVAdapter);
        registerForContextMenu(classLV);


        classLaucher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            reloadClassLV();
                        }
                    }
                });

        FloatingActionButton addClassBtn = findViewById(R.id.addClassBtn);
        addClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddClassActivity.class);
                classLaucher.launch(intent);
            }
        });

        classLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class selectedClass = (Class) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, EditClassActivity.class);
                intent.putExtra("classId", selectedClass.getId());
                classLaucher.launch(intent);
            }
        });
    }

    private void reloadClassLV() {
        classes.clear();
        classes.addAll(classRepo.loadAll());
        classLVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.classLV) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        switch (item.getItemId()) {
            case R.id.deleteClassItem:
                classRepo.delete(classes.get(position).getId());
                reloadClassLV();
                return true;
            case R.id.editStudentMenuItem:
                Intent intent = new Intent(MainActivity.this, StudentManagementActivity.class);
                intent.putExtra("classId", classes.get(position).getId());
                classLaucher.launch(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}




