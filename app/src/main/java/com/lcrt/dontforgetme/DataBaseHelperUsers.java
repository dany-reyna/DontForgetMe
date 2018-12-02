package com.lcrt.dontforgetme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelperUsers extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelperUsers";
    private static final String TABLE_NAME = "Users";
    private static final String COL1 = "Id_User";
    private static final String COL2 = "Username";
    private static final String COL3 = "Password";

    public DataBaseHelperUsers(Context context){
        //El constructor de SQLiteOneHelper ocupa varios parámetros, el contexto, el nombre de la tabla,
        //factory para crear cursosres (por lo general se usa null para el default), y la version (numero de la BD)
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ""+COL2+" TEXT, " +
                ""+COL3+" TEXT)";
        /*CREATE TABLE Users (Id_User INTEGER PRIMARY KEY AUTOINCREMENT,
        * Username TEXT,
        * Password TEXT)*/
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        db.execSQL(dropTable);
    }

    public boolean addUser(String Username, String Password){
        //Se llama para abrir la base de datos en modo escritura
        SQLiteDatabase db = this.getWritableDatabase();
        //ContenValues son un auxiliar que nos va a permitir retener el valor que vamos a insertar por columna
        ContentValues contentValues  = new ContentValues();
        //El método put recibe la columna y el valor que va asociar a ella
        contentValues.put(COL2, Username);
        contentValues.put(COL3, Password);
        //Ahora insertamos finalmente nuestro ContentValues en la BD
        //Insert tiene 3 parámetros, el nombre de la tabla a insertar, el nullColumnHack que le da un valor nulo a las columnas que no se llenen correctamente,
        // y por ultimo nuestro ContentValue con todos los valores que vamos a ponerle
        long result = db.insert(TABLE_NAME, null, contentValues );
        //Cuando el valor no se inserta bien, devuelve un -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean checkUser(String Username){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COL2+" = "+"'"+Username+"'";
        Log.d(TAG, query);
        Cursor datos = db.rawQuery(query, null);
        if (datos.moveToFirst() == false){
            return false;
        }else{
            return true;
        }
    }
    public boolean checkPassword(String Username, String Password){
        String dbpass = "";
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT Password FROM "+TABLE_NAME+ " WHERE "+COL2+" = "+"'"+Username+"'";
        Log.d(TAG, query);
        Cursor pass = db.rawQuery(query, null);
        while (pass.moveToNext()){
            dbpass = pass.getString(0).toString();
        }
        if (dbpass.equals(Password)){
            return false;
        }else{
            return true;
        }
    }
    public void changePassword (String Username, String Password){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "+TABLE_NAME+ " SET Password = '"+Password+"' WHERE "+COL2+" = "+"'"+Username+"'";
        Log.d(TAG,query);
        db.execSQL(query);
    }
}
