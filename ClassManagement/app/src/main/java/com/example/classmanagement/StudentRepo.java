package com.example.classmanagement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

class StudentRepo {
    DBHelper dbHelper;

    public StudentRepo(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }


    public boolean add(Student item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.StudentTable.COLUMN_ID, item.getId());
        values.put(DBHelper.StudentTable.COLUMN_NAME, item.getName());
        values.put(DBHelper.StudentTable.COLUMN_CLASS_ID, item.getClassId());
        values.put(DBHelper.StudentTable.COLUMN_ADDRESS, item.getAddress());
        long rowAffected = db.insert(DBHelper.StudentTable.TABLE_NAME, null, values);
        db.close();
        return rowAffected > 0;
    }

    public ArrayList<Student> loadByClassId(String classId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DBHelper.StudentTable.COLUMN_ID,
                DBHelper.StudentTable.COLUMN_NAME,
                DBHelper.StudentTable.COLUMN_ADDRESS
        };
        Cursor cursor = db.query(DBHelper.StudentTable.TABLE_NAME, projection, DBHelper.StudentTable.COLUMN_CLASS_ID + " = ?", new String[]{classId}, null, null, null);
        ArrayList<Student> items = new ArrayList<Student>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String addr = cursor.getString(2);
            items.add(new Student(id, name, classId, addr));
        }
        db.close();
        return items;
    }

    public boolean delete(String id) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        int rowAffected = db.delete(DBHelper.StudentTable.TABLE_NAME, DBHelper.StudentTable.COLUMN_ID + "= ?", new String[]{id});
        db.close();
        return rowAffected > 0;
    }
}
