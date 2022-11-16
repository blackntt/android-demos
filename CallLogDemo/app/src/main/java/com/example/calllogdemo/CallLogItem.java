package com.example.calllogdemo;

public class CallLogItem {
    private String phone;
    private long date;
    private int type;

    public CallLogItem(String phone, int type, long date) {
        this.setType(type);
        this.setDate(date);
        this.setPhone(phone);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

}
