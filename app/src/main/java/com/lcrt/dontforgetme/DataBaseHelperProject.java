package com.lcrt.dontforgetme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelperProject extends SQLiteOpenHelper{

    private static final String TAG = "DatabaseHelperProject";
    private static final String TABLE_NAME = "Projects";
    private static final String COL1 = "Id_Project";
    private static final String COL2 = "Name";
    private static final String COL3 = "Client";
    private static final String COL4 = "Description";
    private static final String COL5 = "Dead_Line";
    private static final String COL6 = "Color";

    public DataBaseHelperProject(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL2+" TEXT, "+COL3+" TEXT, "+COL4 +" TEXT, "+COL5+" TEXT, "+COL6+" TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        db.execSQL(dropTable);
    }

}
