package com.example.classmanagement;

class Student {
    private String id;
    private String name;
    private String classId;
    private String address;

    public Student(String id, String name, String classId, String address) {
        this.setId(id);
        this.setName(name);
        this.setClassId(classId);
        this.setAddress(address);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}