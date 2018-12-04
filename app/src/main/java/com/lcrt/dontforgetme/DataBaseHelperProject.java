package com.lcrt.dontforgetme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelperProject extends SQLiteOpenHelper{
    //PROJECTS
    private static final String TAG = "DatabaseHelperProject";
    private static final String TABLE_NAME = "Projects";
    private static final String COL1 = "Id_Project";
    private static final String COL2 = "Name";
    private static final String COL3 = "Client";
    private static final String COL4 = "Description";
    private static final String COL5 = "Dead_Line";
    private static final String COL6 = "Color";

    //TASKS
    private static final String TABLE_NAMET = "Tasks";
    private static final String COLT1 = "Id_Task";
    private static final String COLT2 = "Id_Project";
    private static final String COLT3 = "Name";
    private static final String COLT4 = "Priority";
    private static final String COLT5 = "Location";
    private static final String COLT6 = "Init_Date";
    private static final String COLT7 = "Final_Date";
    private static final String COLT8 = "NotificationTime";

    public DataBaseHelperProject(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL2+" TEXT, "+COL3+" TEXT, "+COL4 +" TEXT, "+COL5+" TEXT, "+COL6+" TEXT)";
        String createTableTask = "CREATE TABLE " + TABLE_NAMET + " (" + COLT1+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ""+COLT2+" INTEGER, "+COLT3+" TEXT, "+COLT4 +" TEXT, "+COLT5+" TEXT, "+COLT6+" TEXT, " +
                ""+COLT7+" TEXT, "+COLT8+" TEXT)";
        db.execSQL(createTable);
        db.execSQL(createTableTask);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        String dropTableTask = "DROP TABLE IF EXISTS "+ TABLE_NAMET;
        db.execSQL(dropTable);
        db.execSQL(dropTableTask);
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

    public Cursor getProjectById(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE Id_Project="+id;
        Cursor datos = db.rawQuery(query,null);
        return datos;
    }

    public boolean deleteProject(String ProjectId, DataBaseHelperTask TasksDb){
        SQLiteDatabase dbp = this.getWritableDatabase();
        SQLiteDatabase dbt = TasksDb.getWritableDatabase();
        long resulttask = dbt.delete("Tasks","Id_Project="+ProjectId,null);
        long resultproject = dbp.delete(TABLE_NAME,COL1+"="+ProjectId,null);
        Log.d("Task","Valor"+resulttask);
        Log.d("Project","Valor"+resultproject);
        if(resulttask != -1  || resultproject != -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean deleteTask(String TaskId){
        SQLiteDatabase dbp = this.getWritableDatabase();
        long result = dbp.delete(TABLE_NAMET,COLT1+"="+TaskId,null);
        if(result != -1){
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

    public boolean updateTask(String TaskId, String name, String priority, String location, String startDate, String endDate, String notificationTime, String ProjectId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put(COLT2,ProjectId);
        contentValues.put(COLT3,name);
        contentValues.put(COLT4,priority);
        contentValues.put(COLT5,location);
        contentValues.put(COLT6,startDate);
        contentValues.put(COLT7,endDate);
        contentValues.put(COLT8,notificationTime);
        long result = db.update(TABLE_NAMET,contentValues,COLT1+"="+TaskId,null);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean addTask(String Name, String Priority, String Location, String StartDate, String EndDate,
                           String NotificationTime, String ProjectId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put(COLT2, ProjectId);
        contentValues.put(COLT3, Name);
        contentValues.put(COLT4, Priority);
        contentValues.put(COLT5, Location);
        contentValues.put(COLT6, StartDate);
        contentValues.put(COLT7, EndDate);
        contentValues.put(COLT8, NotificationTime);
        long result = db.insert(TABLE_NAMET, null, contentValues );
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllTasks(){
        SQLiteDatabase db = this.getWritableDatabase();
        String All = "SELECT a.*, b.* FROM Tasks a, Projects b WHERE a.Id_Project=b.Id_Project";
        Log.d(TAG, All);
        Cursor datos = db.rawQuery(All, null);
        return datos;
    }

    public Cursor getTodayTasks(){
        SQLiteDatabase db = this.getWritableDatabase();
        String All = "SELECT a.Id_Task, a.Name, a.Priority, a.Location, a.Init_Date, a.Final_Date, a.NotificationTime, b.Id_Project, b.Name, b.Color FROM Tasks a, Projects b WHERE a.Id_Project=b.Id_Project";
        Log.d(TAG, All);
        Cursor datos = db.rawQuery(All, null);
        return datos;
    }

    public Cursor getMonthTask(String Year, String Month, String Day){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAMET+" WHERE StartDate LIKE '%'"+Year+"-"+Month+"-"+Day+"'%'";
        Cursor datos = db.rawQuery(query, null);
        return datos;
    }
}
