package com.example.camerademo;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

class ImageAdapter extends ArrayAdapter<ImageItem> {
    private List<ImageItem> items;

    public ImageAdapter(Context context, List<ImageItem> todoList) {
        super(context, R.layout.image_item, todoList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(this.getContext());
            v = vi.inflate(R.layout.image_item, null);
        }
        ImageItem item = getItem(position);
        if (item != null) {
            ImageView imageView = v.findViewById(R.id.imageIV);
            TextView titleTextView = (TextView) v.findViewById(R.id.title);
            if (titleTextView != null)
                titleTextView.setText(item.getTitle());
            if (imageView != null) {
                imageView.setImageURI(Uri.parse(item.getPath()));
            }
        }
        return v;
    }
}