package com.example.airplanebroadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements IAirplaneModeProcessable {

    AirplaneBroadcastReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //check the first state of airplane when launching the activity
        boolean isAirplane = Settings.System.getInt(this.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        this.airplaneProcess(isAirplane);


        //register a broadcast receiver for updating the state of airplane mode
        receiver = new AirplaneBroadcastReceiver(this);
        IntentFilter intentFilter = new
                IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(receiver, intentFilter);

    }

    @Override
    public void airplaneProcess(boolean isAirplaneMode) {
        String text = "Airplane mode is off";
        if (isAirplaneMode) {
            text = "Airplane mode is on";
        }
        TextView status = findViewById(R.id.airplaneStatus);
        status.setText(text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}

interface IAirplaneModeProcessable {
    public void airplaneProcess(boolean isAirplaneMode);
}

class AirplaneBroadcastReceiver extends BroadcastReceiver {
    IAirplaneModeProcessable processor;

    AirplaneBroadcastReceiver(IAirplaneModeProcessable processor) {
        this.processor = processor;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //only process the ACTION_AIRPLANE_MODE_CHANGED
        if(intent.getAction().compareTo( Intent.ACTION_AIRPLANE_MODE_CHANGED)==0){
            this.processor.airplaneProcess(intent.getBooleanExtra("state", false));
        }
    }
}
