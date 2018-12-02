package com.lcrt.dontforgetme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelperTask extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelperProject";
    private static final String TABLE_NAME = "Tasks";
    private static final String COL1 = "Id_Task";
    private static final String COL2 = "Id_Project";
    private static final String COL3 = "Name";
    private static final String COL4 = "Priority";
    private static final String COL5 = "Location";
    private static final String COL6 = "Init_Date";
    private static final String COL7 = "Init_Hour";
    private static final String COL8 = "Final_Date";
    private static final String COL9 = "Final_Hour";

    public DataBaseHelperTask(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL2+" INTEGER, "+COL3+" TEXT, "+COL4 +" TEXT, "+COL5+" TEXT, "+COL6+" TEXT, "+COL7+" TEXT, "+COL8+" TEXT, "+COL9+" TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        db.execSQL(dropTable);
    }
}
