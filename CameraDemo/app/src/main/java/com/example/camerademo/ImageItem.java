package com.example.camerademo;

public class ImageItem {
    private String path;
    private String title;

    public ImageItem(String title, String path) {
        this.setTitle(title);
        this.setPath(path);
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
}
