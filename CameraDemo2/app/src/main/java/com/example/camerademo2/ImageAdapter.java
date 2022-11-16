package com.example.camerademo2;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Iterator;
import java.util.List;

class ImageAdapter extends ArrayAdapter<ImageItem> {
    private boolean isSelectionMode = false;

    private List<ImageItem> items;

    public ImageAdapter(Context context, List<ImageItem> items) {
        super(context, R.layout.image_item, items);
        this.items = items;
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
            TextView titleTextView = v.findViewById(R.id.title);
            CheckBox selectCheckBox = v.findViewById(R.id.selectCheckBox);
            if (titleTextView != null)
                titleTextView.setText(item.getTitle());
            if (imageView != null) {
                imageView.setImageURI(Uri.parse(item.getPath()));
            }
            if (selectCheckBox != null) {
                selectCheckBox.setChecked(item.isSelected());
                selectCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        item.setSelected(isChecked);
                    }
                });
                if (isSelectionMode) {
                    selectCheckBox.setVisibility(View.VISIBLE);
                } else {
                    selectCheckBox.setVisibility(View.GONE);
                }

            }
        }
        return v;
    }

    public boolean isSelectionMode() {
        return isSelectionMode;
    }

    public void setSelectionMode(boolean selectionMode) {
        isSelectionMode = selectionMode;
    }
}