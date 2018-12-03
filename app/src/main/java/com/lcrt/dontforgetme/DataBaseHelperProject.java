package com.lcrt.dontforgetme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    public boolean addProject(String Name, String Color, String Client, String Description, String Deadline){
        Log.d("Hey", Color);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put(COL2, Name);
        contentValues.put(COL3, Client);
        contentValues.put(COL4, Description);
        contentValues.put(COL5, Deadline);
        contentValues.put(COL6, Color);
        long result = db.insert(TABLE_NAME, null, contentValues );
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getLastString(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+COL1+" FROM "+TABLE_NAME+" ORDER BY "+COL1+" DESC LIMIT 1";
        Cursor datos =  db.rawQuery(query,null);
        return datos;
    }
    public boolean deleteProject(String ProjectId, DataBaseHelperTask TasksDb){
        SQLiteDatabase dbp = this.getWritableDatabase();
        SQLiteDatabase dbt = TasksDb.getWritableDatabase();
        long resulttask = dbt.delete("Tasks","Id_Project="+ProjectId,null);
        long resultproject = dbp.delete(TABLE_NAME,COL1+"="+ProjectId,null);
        Log.d("Task","Valor"+resulttask);
        Log.d("Project","Valor"+resultproject);
        if((resulttask != -1 || resulttask ==0) && resultproject != -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getAllProjects(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;
        Log.d(TAG, query);
        Cursor datos = db.rawQuery(query, null);
        return datos;
    }

    public boolean updateProject(String ProjectId, String Name, String Client, String Description, String Color, String Deadline){
        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "UPDATE "+TABLE_NAME+" SET "+COL2 + " = "+Name+", "+COL3 + " = "+ Client+", "+COL4+" = "+Description+", "+COL5+" = "+Deadline+", "+COL6+" = "+Color+" WHERE "+COL1+" = "+ProjectId;
        //Log.d("Update Query",query);
        ContentValues contentValues  = new ContentValues();
        contentValues.put(COL2,Name);
        contentValues.put(COL3,Client);
        contentValues.put(COL4,Description);
        contentValues.put(COL5, Deadline);
        contentValues.put(COL6, Color);
        long result = db.update(TABLE_NAME,contentValues,COL1+"="+ProjectId,null);
        if (result == -1){
            return false;
        }else{
            return true;
        }

    }
}
