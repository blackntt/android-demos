package com.example.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SongListViewAdapter extends ArrayAdapter<SongItem> {

    ArrayList<SongItem> songs;
    public SongListViewAdapter(Context context, ArrayList<SongItem> songs) {
        super(context, R.layout.song_view_item, songs);
        this.songs = songs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(this.getContext());
            v = vi.inflate(R.layout.song_view_item, null);
        }
        SongItem song = getItem(position);
        if(song != null){
            CheckBox songCB = v.findViewById(R.id.songCB);
            songCB.setText(song.getTitle());
        }
        return v;
    }
}
