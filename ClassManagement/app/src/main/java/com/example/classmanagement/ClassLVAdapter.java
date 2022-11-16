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

public class ClassLVAdapter extends ArrayAdapter<Class> {
    int resource;
    private final List<Class> items;

    public ClassLVAdapter(Context context, int resource, List<Class> items) {
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
        Class data = getItem(position);
        if (data != null) {
            TextView idTV = v.findViewById(R.id.classId);
            TextView nameTV = v.findViewById(R.id.className);
            TextView deptTV = v.findViewById(R.id.dept);
            TextView itemIndex = v.findViewById(R.id.itemIndex);
            if (idTV != null)
                idTV.setText(data.getId());
            if (nameTV != null)
                nameTV.setText(data.getName());
            if (deptTV != null)
                deptTV.setText(data.getDept());
            if (itemIndex != null)
                itemIndex.setText("#" + (position + 1));
        }
        return v;
    }
}