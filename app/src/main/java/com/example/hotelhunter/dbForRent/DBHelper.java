package com.example.hotelhunter.dbForRent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "ForRentData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table ForRentDetails(address TEXT primary key, type TEXT, price int, area TEXT, contact TEXT, description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists ForRentDetails");
    }

    public boolean insertDBData(String address, String type, int price, String area, String contact,String description) {
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
    public boolean updateDBData(String address, String type, int price, String area, String contact,String details) {
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

    public boolean deleteDBData(String address) {
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
    public ArrayList<ForRent> viewDBData() {
        ArrayList<ForRent> forRentList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from ForRentDetails",null);

        while(cursor.moveToNext()) {
            String address = cursor.getString(0);   //0 is the number of id column in your database table
            String type = cursor.getString(1);
            int price = Integer.parseInt(cursor.getString(2));
            String area = cursor.getString(3);
            String contact = cursor.getString(4);
            String description =  cursor.getString(5);

            ForRent newForRent = new ForRent(address, type, price, area, contact, description);
            forRentList.add(newForRent);
        }
        return forRentList;
    }

    public ArrayList<ForRent> searchDBData(String text) {
        ArrayList<ForRent> forRentListSearch = new ArrayList<>();
        String textS = text.trim();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from ForRentDetails " +
                "where address like '%" + textS + "%' OR type like'%" + textS + "%' OR " +
                "price like '%" + Integer.parseInt(textS) + "%' OR area like'%" + textS + "%' OR " +
                "contact like '%" + textS + "%' OR description like'%"+ textS + "%'";
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()) {
            String address = cursor.getString(0);   //0 is the number of id column in your database table
            String type = cursor.getString(1);
            int price = Integer.parseInt(cursor.getString(2));
            String area = cursor.getString(3);
            String contact = cursor.getString(4);
            String description =  cursor.getString(5);

            ForRent newForRent = new ForRent(address, type, price, area, contact, description);
            forRentListSearch.add(newForRent);
        }
        return forRentListSearch;
    }
}
