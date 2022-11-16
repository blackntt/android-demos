package com.example.todo_sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class MainActivity extends AppCompatActivity {


    TodoListAdapter todoListAdapter;
    ActivityResultLauncher<Intent> activityResultLaunch;
    ArrayList<TodoItem> todoList;
    ListView listView;

    TodoRepo todoRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoRepo = new TodoRepo(MainActivity.this);
        listView = findViewById(R.id.todoList);
        todoList = todoRepo.loadAll();
        todoListAdapter = new TodoListAdapter(getApplicationContext(), R.layout.todo_item, todoList, todoRepo);
        listView.setAdapter(todoListAdapter);
        registerForContextMenu(listView);

        activityResultLaunch = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        switch (result.getResultCode()){
                            case 1:
                            case 2:
                                reloadTodoList();
                                break;
                        }
                    }
                });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditTodoActivity.class);
                TodoItem selectedItem = (TodoItem) parent.getItemAtPosition(position);
                intent.putExtra("id", selectedItem.getId());
                activityResultLaunch.launch(intent);
            }
        });

    }

    private void reloadTodoList(){
        todoList.clear();
        todoList.addAll(todoRepo.loadAll());
        todoListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.todoList) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_context, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        switch (item.getItemId()) {
            case R.id.delete:
                todoRepo.delete(todoList.get(position).getId());
                reloadTodoList();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                activityResultLaunch.launch(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}