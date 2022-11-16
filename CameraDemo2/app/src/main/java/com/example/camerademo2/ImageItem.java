package com.example.camerademo2;

import android.util.Log;

public class ImageItem {
    private String path;
    private String title;
    private boolean isSelected;

    public ImageItem(String title, String path) {
        this.setTitle(title);
        this.setPath(path);
        this.setSelected(false);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
