package com.example.hotelhunter.ui.account;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBTaiKhoan extends SQLiteOpenHelper {


    public DBTaiKhoan(@Nullable Context context) {
        super(context, "Userdata1.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Userdetails1(name TEXT, pass TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists Userdetails1");

    }

    public Boolean addUserdata(String name, String pass) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("pass", pass);
        long result = DB.insert("Userdetails1", null, contentValues);
        /* if (result == -1) false */   //So sanh da co trong dataBase.
        return true;
    }

    public Boolean updateUserdata(String name, String pass) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("pass", pass);
        Cursor cursor = DB.rawQuery("Select * from Userdetails1 where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.update("Userdetails1", contentValues, "name?", new String[]{name});
            if (result == -1) {
                return false;
            } else
                return true;
        } else {
            return false;
        }

    }


    public Boolean deleteUserdata(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("Select * from Userdetails1 where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Userdetails1", null, new String[]{name});
            if (result == -1) {
                return false;
            } else
                return true;
        } else {
            return false;
        }

    }


    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails1",null);
        return cursor;
    }


}
