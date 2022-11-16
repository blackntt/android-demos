package com.example.classmanagement;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

class ClassRepo {

    DBHelper dbHelper;

    public ClassRepo(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public boolean add(Class item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.ClassTable.COLUMN_ID, item.getId());
        values.put(DBHelper.ClassTable.COLUMN_DEPT, item.getDept());
        values.put(DBHelper.ClassTable.COLUMN_NAME, item.getName());
        long rowAffected = db.insert(DBHelper.ClassTable.TABLE_NAME, null, values);
        db.close();
        return rowAffected > 0;
    }

    public ArrayList<Class> loadAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DBHelper.ClassTable.COLUMN_ID,
                DBHelper.ClassTable.COLUMN_NAME,
                DBHelper.ClassTable.COLUMN_DEPT,
        };
        Cursor cursor = db.query(DBHelper.ClassTable.TABLE_NAME, projection, null, null, null, null, null);
        ArrayList<Class> items = new ArrayList<Class>();

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String dept = cursor.getString(2);
            items.add(new Class(id, name, dept));
        }
        db.close();
        return items;
    }

    public Class findById(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DBHelper.ClassTable.COLUMN_ID,
                DBHelper.ClassTable.COLUMN_NAME,
                DBHelper.ClassTable.COLUMN_DEPT,
        };
        Cursor cursor = db.query(DBHelper.ClassTable.TABLE_NAME, projection, DBHelper.ClassTable.COLUMN_ID + " = ?", new String[]{id}, null, null, null);
        ArrayList<Class> items = new ArrayList<Class>();
        Class item = null;
        if (cursor.moveToNext()) {
            String name = cursor.getString(1);
            String dept = cursor.getString(2);
            item = new Class(id, name, dept);
        }
        db.close();
        return item;
    }

    public boolean update(Class item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.ClassTable.COLUMN_NAME, item.getName());
        values.put(DBHelper.ClassTable.COLUMN_DEPT, item.getDept());
        int rowAffected = db.update(DBHelper.ClassTable.TABLE_NAME, values, DBHelper.ClassTable.COLUMN_ID + " = ?", new String[]{item.getId()});
        db.close();
        return rowAffected > 0;
    }

    public boolean delete(String id) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        int rowAffected = db.delete(DBHelper.ClassTable.TABLE_NAME, DBHelper.ClassTable.COLUMN_ID + "= ?", new String[]{id});
        db.close();
        return rowAffected > 0;
    }
}

