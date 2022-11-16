package com.example.calllogdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

interface ICallChangeListener {
    public void onCallChange();
}

public class CallReceiver extends BroadcastReceiver {
    ICallChangeListener callChangeListener;

    public CallReceiver(ICallChangeListener callChangeListener) {
        this.callChangeListener = callChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.callChangeListener.onCallChange();
    }
}