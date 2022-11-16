package com.example.classmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class StudentLVAdapter extends ArrayAdapter<Student> {
    int resource;
    private final List<Student> items;

    public StudentLVAdapter(Context context, int resource, List<Student> items) {
        super(context, resource, items);
        this.items = items;
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
        Student data = getItem(position);
        if (data != null) {
            TextView idTV = v.findViewById(R.id.classId);
            TextView nameTV = v.findViewById(R.id.className);
            TextView addressTV = v.findViewById(R.id.address);
            TextView indexTV = v.findViewById(R.id.itemIndex);
            if (indexTV != null)
                indexTV.setText("#" + (position + 1));
            if (idTV != null)
                idTV.setText(data.getId());
            if (nameTV != null)
                nameTV.setText(data.getName());
            if (addressTV != null)
                addressTV.setText(data.getAddress());
        }
        return v;
    }
}