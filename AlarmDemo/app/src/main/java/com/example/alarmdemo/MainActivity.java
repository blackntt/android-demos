package com.example.alarmdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private AlarmManager alarmMgr;
    private TimePicker timePicker;
    private Button alarmButton;
    private ArrayAdapter<String> arrayAdapter;
    private int notificationId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timePicker = findViewById(R.id.timePicker);
        alarmButton = findViewById(R.id.alarm);

        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, new ArrayList<>());
        ListView alarmList = findViewById(R.id.alarmList);
        alarmList.setAdapter(arrayAdapter);

        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());
                Intent intent = new Intent(v.getContext(), AlarmReceiver.class);
                intent.setAction("ALARM_ACTION");
                intent.putExtra("id", notificationId++);
                PendingIntent alarmIntent = PendingIntent.getBroadcast(v.getContext(), 0, intent,
                        PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_ONE_SHOT);
                alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        alarmIntent);
                arrayAdapter.add("An alarm at " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(v.getContext(), "Alarm has been set", Toast.LENGTH_LONG).show();
            }
        });

    }
}