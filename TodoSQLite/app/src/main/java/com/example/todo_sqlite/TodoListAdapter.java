package com.example.todo_sqlite;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

class TodoListAdapter extends ArrayAdapter<com.example.todo_sqlite.TodoItem> {
    int resource;
    private List<com.example.todo_sqlite.TodoItem> todoList;
    private TodoRepo todoRepo;
    public TodoListAdapter(Context context, int resource, List<com.example.todo_sqlite.TodoItem> todoList, TodoRepo todoRepo) {
        super(context, resource, todoList);
        this.todoList = todoList;
        this.resource = resource;
        this.todoRepo = todoRepo;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(this.getContext());
            v = vi.inflate(this.resource,null);
        }
        com.example.todo_sqlite.TodoItem todoItem = getItem(position);
        if (todoItem!=null){
            TextView titleTextView = (TextView) v.findViewById(R.id.title);
            TextView indexTextView = (TextView) v.findViewById(R.id.index);
            CheckBox isDoneCheckBox = (CheckBox) v.findViewById(R.id.isDone);
            if(titleTextView!=null)
                titleTextView.setText(todoItem.getTitle());
            if(isDoneCheckBox !=null)
                isDoneCheckBox.setChecked(todoItem.isDone());
            if(indexTextView!=null)
                indexTextView.setText("#"+String.valueOf(position+1));

            isDoneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    todoItem.setDone(isChecked);
                    todoRepo.update(todoItem);
                }
            });

        }
        return v;
    }
}