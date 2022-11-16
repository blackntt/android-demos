package com.example.listviewdemo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String[] names = {"Nguyen Van A", "Tran Van B", "Nguyen Ho Van D"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView nameListview = (ListView) findViewById(R.id.nameListview);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, names);
        nameListview.setAdapter(arrayAdapter);
    }
}