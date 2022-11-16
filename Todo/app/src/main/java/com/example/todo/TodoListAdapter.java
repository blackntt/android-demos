package com.example.todo;


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

class TodoListAdapter extends ArrayAdapter<TodoItem> {
    int resource;
    private final List<TodoItem> todoList;

    public TodoListAdapter(Context context, int resource, List<TodoItem> todoList) {
        super(context, resource, todoList);
        this.todoList = todoList;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(this.getContext());
            v = vi.inflate(this.resource, null);
        }
        TodoItem s = getItem(position);
        if (s != null) {
            TextView titleTextView = v.findViewById(R.id.title);
            TextView indexTextView = v.findViewById(R.id.index);
            CheckBox isDoneCheckBox = v.findViewById(R.id.isDone);
            if (titleTextView != null)
                titleTextView.setText(s.getTitle());
            if (isDoneCheckBox != null)
                isDoneCheckBox.setChecked(s.isDone());
            if (indexTextView != null)
                indexTextView.setText("#" + (position + 1));

            isDoneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    s.setDone(isChecked);
                }
            });

        }
        return v;
    }

    @Override
    public TodoItem getItem(int position) {
        return todoList.get(position);
    }
}