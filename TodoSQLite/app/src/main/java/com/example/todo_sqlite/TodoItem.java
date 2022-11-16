package com.example.todo_sqlite;

class TodoItem {
    private String id;
    private String title;
    private String desc;
    private boolean isDone;


    public TodoItem(String id, String title, String desc, boolean isDone){
        this.setId(id);
        this.desc = desc;
        this.isDone = isDone;
        this.setTitle(title);
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}