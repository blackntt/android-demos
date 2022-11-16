package com.example.calllogdemo;

import android.content.Context;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CallLogListViewAdapter extends ArrayAdapter<CallLogItem> {
    List<CallLogItem> logs;

    public CallLogListViewAdapter(@NonNull Context context, List<CallLogItem> logs) {
        super(context, R.layout.calllog_item);
        this.logs = logs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(this.getContext());
            v = vi.inflate(R.layout.calllog_item, null);
        }
        CallLogItem log = getItem(position);

        if (log != null) {
            TextView phoneTV = v.findViewById(R.id.phone);
            TextView dateTV = v.findViewById(R.id.date);
            ImageView image = v.findViewById(R.id.icon);
            if (phoneTV != null)
                phoneTV.setText(log.getPhone());
            if (dateTV != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd-MM-yy HH:mm");
                String dateStr = formatter.format(new Date(log.getDate()));
                dateTV.setText(dateStr);
            }
            if (image != null) {
                int imageSrc;
                switch (log.getType()) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        imageSrc = R.drawable.outcoming;
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        imageSrc = R.drawable.missed;
                        break;
                    case CallLog.Calls.REJECTED_TYPE:
                        imageSrc = R.drawable.rejected;
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                    default:
                        imageSrc = R.drawable.incoming;
                }
                image.setImageResource(imageSrc);
            }
        }
        return v;
    }
}