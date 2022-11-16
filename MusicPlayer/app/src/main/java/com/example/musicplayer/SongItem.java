package com.example.musicplayer;

public class SongItem {
    private String title;


    public SongItem(){}
    public SongItem(String title, boolean isSelected){
        this.setTitle(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
