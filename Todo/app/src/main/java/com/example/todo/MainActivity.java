package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
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

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    TodoListAdapter todoListAdapter;
    ActivityResultLauncher<Intent> activityResultLaunch;
    ArrayList<TodoItem> todoList;
    ListView listView;
    boolean isSelectionMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoList = new ArrayList<TodoItem>();
        listView = findViewById(R.id.todoList);
        todoListAdapter = new TodoListAdapter(getApplicationContext(), R.layout.todo_item, todoList);
        listView.setAdapter(todoListAdapter);
        registerForContextMenu(listView);

        activityResultLaunch = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == 1) {
                            Intent intent = result.getData();
                            String title = intent.getStringExtra("title");
                            String desc = intent.getStringExtra("desc");
                            Boolean isDone = intent.getBooleanExtra("isDone", false);
                            String newId = UUID.randomUUID().toString();
                            TodoItem todoItem = new TodoItem(newId, title, desc, isDone);
                            todoList.add(todoItem);
                            todoListAdapter.notifyDataSetChanged();
                        }else if (result.getResultCode() == 2){
                            Intent intent = result.getData();
                            int index = intent.getIntExtra("index",-1);
                            String title = intent.getStringExtra("title");
                            String desc = intent.getStringExtra("desc");
                            Boolean isDone = intent.getBooleanExtra("isDone", false);
                            TodoItem todoItem = (TodoItem) listView.getItemAtPosition(index);
                            todoItem.setDesc(desc);
                            todoItem.setTitle(title);
                            todoItem.setDone(isDone);
                            todoListAdapter.notifyDataSetChanged();
                        }
                    }
                });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditTodoActivity.class);
                TodoItem selectedItem = (TodoItem) parent.getItemAtPosition(position);
                intent.putExtra("id", selectedItem.getId());
                intent.putExtra("index", position);
                intent.putExtra("title", selectedItem.getTitle());
                intent.putExtra("desc", selectedItem.getDesc());
                intent.putExtra("isDone", selectedItem.isDone());
                activityResultLaunch.launch(intent);
            }
        });

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
                todoList.remove(position);
                todoListAdapter.notifyDataSetChanged();
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