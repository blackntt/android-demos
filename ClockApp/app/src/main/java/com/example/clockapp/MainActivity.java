package com.example.clockapp;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AnalogClock;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.time_zones, android.R.layout.simple_spinner_item);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);

        AnalogClock analogClock = findViewById(R.id.analogClock);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.S)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    analogClock.setTimeZone(TimeZone.getDefault().getID());
                } else {
                    String timezone = parent.getItemAtPosition(position).toString();
                    analogClock.setTimeZone(timezone);
                }
                analogClock.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}