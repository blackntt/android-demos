package com.example.calllogdemo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, ICallChangeListener {
    final int CALL_LOGS_LOADER = 0;
    CallLogListViewAdapter callLogListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CALL_LOG
        }, 0);
        BroadcastReceiver br = new CallReceiver(this);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PHONE_STATE");
        intentFilter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
        this.registerReceiver(br, intentFilter);
        callLogListViewAdapter = new CallLogListViewAdapter(this, new ArrayList<>());
        ListView callLogLV = findViewById(R.id.callLogLV);
        callLogLV.setAdapter(callLogListViewAdapter);
        LoaderManager.getInstance(this).initLoader(CALL_LOGS_LOADER, null, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Permission denied to read your contacts",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == CALL_LOGS_LOADER)
            return new CursorLoader(this, CallLog.Calls.CONTENT_URI, new String[]{
                    CallLog.Calls.NUMBER,
                    CallLog.Calls.TYPE,
                    CallLog.Calls.DATE
            }, CallLog.Calls.TYPE + "  in (?,?,?,?)",
                    new String[]{String.valueOf(CallLog.Calls.OUTGOING_TYPE),
                            String.valueOf(CallLog.Calls.INCOMING_TYPE),
                            String.valueOf(CallLog.Calls.MISSED_TYPE),
                            String.valueOf(CallLog.Calls.REJECTED_TYPE)}, CallLog.Calls.DATE + " " +
                    "DESC");
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == CALL_LOGS_LOADER && data != null) {
            List<CallLogItem> logs = new ArrayList<>();
            while (data.moveToNext()) {
                String phone = data.getString(0);
                int type = data.getInt(1);
                long date = data.getLong(2);
                CallLogItem logItem = new CallLogItem(phone, type, date);
                logs.add(logItem);
            }
            callLogListViewAdapter.clear();
            callLogListViewAdapter.addAll(logs);
            callLogListViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        loader = null;
    }

    @Override
    public void onCallChange() {
        LoaderManager.getInstance(this).restartLoader(this.CALL_LOGS_LOADER, null, this);
    }
}