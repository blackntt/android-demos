package com.example.contactdemo;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

class ContactListAdapter extends ArrayAdapter<ContactItem> {
    int resource;
    private List<ContactItem> contacts;
    public ContactListAdapter(Context context, int resource, List<ContactItem> students) {
        super(context, resource, students);
        this.contacts = students;
        this.resource = resource;
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
        ContactItem s = getItem(position);

        if (s!=null){
            TextView nameTextView = (TextView) v.findViewById(R.id.name);
            TextView phoneTextView = (TextView) v.findViewById(R.id.phone);


            if(nameTextView!=null)
                nameTextView.setText(s.getName());
            if(phoneTextView !=null)
                phoneTextView.setText(s.getPhone());
        }
        return v;
    }

}