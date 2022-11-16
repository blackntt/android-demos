package com.example.classmanagement;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ClassManagement";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClassTable.SQL_CREATE_TABLE);
        db.execSQL(StudentTable.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ClassTable.SQL_DROP_TABLE);
        db.execSQL(StudentTable.SQL_DROP_TABLE);
        onCreate(db);
    }

    public static class ClassTable {
        public static final String TABLE_NAME = "Classes";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DEPT = "dept";
        public static final String COLUMN_NAME = "name";
        public static final String SQL_CREATE_TABLE = "Create table " + TABLE_NAME + "(" + COLUMN_ID + " text primary key, " + COLUMN_DEPT + " text, " + COLUMN_NAME + " text)";
        public static final String SQL_DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    }

    public static class StudentTable {
        public static final String TABLE_NAME = "Students";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CLASS_ID = "classId";
        public static final String COLUMN_ADDRESS = "address";
        public static final String SQL_CREATE_TABLE = "Create table " + TABLE_NAME + "(" + COLUMN_ID + " text primary key, " + COLUMN_NAME + " text, " + COLUMN_ADDRESS + " text, " + COLUMN_CLASS_ID + " text, CONSTRAINT fk_class FOREIGN KEY (" + COLUMN_CLASS_ID + ") REFERENCES " + ClassTable.TABLE_NAME + "(" + ClassTable.COLUMN_ID + ") ON DELETE CASCADE)";
        public static final String SQL_DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    }

}

