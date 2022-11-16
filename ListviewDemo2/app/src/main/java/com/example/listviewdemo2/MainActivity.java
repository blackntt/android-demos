package com.example.listviewdemo2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Student> students = new ArrayList<Student>();
        students.add(new Student("Nguyen Van A", 1999));
        students.add(new Student("Nguyen Tan B",  2000));
        students.add(new Student("Tran Xuan B", 2001));

        ListView list = (ListView) findViewById(R.id.nameListview);
        StudentListAdapter adapter = new StudentListAdapter(getApplicationContext(), R.layout.list_item_student, students);
        list.setAdapter(adapter);
    }
}


class StudentListAdapter extends ArrayAdapter<Student>{
    int resource;
    private List<Student> students;
    public StudentListAdapter(Context context, int resource, List<Student> students) {
        super(context, resource, students);
        this.students = students;
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
        Student s = getItem(position);

        if (s!=null){
            TextView nameTextView = (TextView) v.findViewById(R.id.name);
            TextView dobTextView = (TextView) v.findViewById(R.id.dob);

            if(nameTextView!=null)
                nameTextView.setText(s.getName());
            if(dobTextView!=null)
                dobTextView.setText(String.valueOf(s.getDoB()));
        }
        return v;
    }
}


class Student{
    private String name;
    private int dob;

    public Student(String name, int dob){
        this.name = name;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDoB() {
        return dob;
    }

    public void setDoB(int dob) {
        this.dob = dob;
    }
}