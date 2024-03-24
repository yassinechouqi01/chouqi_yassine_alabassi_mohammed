package com.example.myprj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBhelper(Context context) {
        super(context, "Login.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users (email TEXT primary key,password TEXT)");
        MyDB.execSQL("create Table form_data (id INTEGER PRIMARY KEY AUTOINCREMENT, city TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists form_data");
        onCreate(MyDB);
    }

    public boolean insertdata(String email, String password) {
         SQLiteDatabase MyDB = this.getWritableDatabase();
         ContentValues contentValues = new ContentValues();
         contentValues.put("email", email);
         contentValues.put("password", password);
         long result = MyDB.insert("users", null, contentValues);
        return result != -1; // Return true if insertion is successful, false otherwise
    }

    public boolean checkusername(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE LOWER(email) = ?", new String[]{email.toLowerCase()});
        return cursor.getCount() > 0;
    }

    public boolean checkpassword(String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE LOWER(email) = ? AND password = ?", new String[]{email.toLowerCase(), password});
        return cursor.getCount() > 0;
    }

    public boolean insertFormData(String city) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("city", city);
        long result = MyDB.insert("form_data", null, contentValues);
        return result != -1; // Return true if insertion is successful, false otherwise
    }
}
