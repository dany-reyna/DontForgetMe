package com.lcrt.dontforgetme;

import android.content.ContentValues;
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
    private static final String COL7 = "Final_Date";
    private static final String COL8 = "NotificationTime";

    public DataBaseHelperTask(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ""+COL2+" INTEGER, "+COL3+" TEXT, "+COL4 +" TEXT, "+COL5+" TEXT, "+COL6+" TEXT, " +
                ""+COL7+" TEXT, "+COL8+" TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        db.execSQL(dropTable);
    }

    public boolean addTask(String Name, String Priority, String Location, String StartDate, String EndDate,
                           String NotificationTime, String ProjectId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put(COL2, ProjectId);
        contentValues.put(COL3, Name);
        contentValues.put(COL4, Priority);
        contentValues.put(COL5, Location);
        contentValues.put(COL6, StartDate);
        contentValues.put(COL7, EndDate);
        contentValues.put(COL8, NotificationTime);
        long result = db.insert(TABLE_NAME, null, contentValues );
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

}
