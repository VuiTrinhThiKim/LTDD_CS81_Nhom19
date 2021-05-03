package com.example.hotelhunter.dbForRent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "ForRentData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table ForRentDetails(address TEXT primary key, type TEXT, price double, area TEXT, contact int, description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists ForRentDetails");
    }

    public boolean insertPhongTroData(String address, String type, double price, String area, int contact,String description) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", address);
        contentValues.put("type", type);
        contentValues.put("price", price);
        contentValues.put("area", area);
        contentValues.put("contact", contact);
        contentValues.put("description", description);

        long result = DB.insert("ForRentDetails", null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }
    public boolean updatePhongTroData(String address, String type, double price, double area, int contact,String details) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", type);
        contentValues.put("price", price);
        contentValues.put("area", area);
        contentValues.put("contact", contact);
        contentValues.put("details", details);

        Cursor cursor = DB.rawQuery("select * from ForRentDetails where address = ?", new String[] {address});

        if (cursor.getCount()> 0) {
            long result = DB.update("ForRentDetails", contentValues,"address = ?", new String[]{address});
            if (result == -1) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    public boolean deletePhongTroData(String address) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from ForRentDetails where address = ?", new String[] {address});

        if (cursor.getCount()> 0) {
            long result = DB.delete("ForRentDetails", null, new String[]{address});
            if (result == -1) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }
}
