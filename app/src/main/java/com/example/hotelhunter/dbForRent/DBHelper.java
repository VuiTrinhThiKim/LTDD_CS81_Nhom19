package com.example.hotelhunter.dbForRent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    Cursor cursorS;
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

    /*public ArrayList<ForRent> viewDBDataSort(String areaS, String priceS, String typeS) {
        ArrayList<ForRent> forRentList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", typeS);
        contentValues.put("area", areaS);
        contentValues.put("price", priceS);

        if (areaS != "Tất cả" && priceS == "Tăng dần" && typeS != "Tất cả"){
            cursorS = db.rawQuery("select * from ForRentDetails where type = ? AND area = ? order by price ASC",
                    new String[] {typeS,areaS});
        }
        if (areaS != "Tất cả" && priceS == "Giảm dần" && typeS != "Tất cả"){
            cursorS = db.rawQuery("select * from ForRentDetails where type = ? AND area = ? order by price DESC",
                    new String[] {typeS,areaS});
        }
        if (areaS == "Tất cả" && priceS == "Tăng dần" && typeS != "Tất cả"){
            cursorS = db.rawQuery("select * from ForRentDetails where area = ? order by price ASC",
                    new String[] {typeS,areaS});
        }
        if (areaS == "Tất cả" && priceS == "Giảm dần" && typeS != "Tất cả"){
            cursorS = db.rawQuery("select * from ForRentDetails where area = ? order by price DESC",
                    new String[] {typeS,areaS});
        }
        if (areaS != "Tất cả" && priceS == "Tăng dần" && typeS == "Tất cả"){
            cursorS = db.rawQuery("select * from ForRentDetails where type = ? order by price ASC",
                    new String[] {typeS,areaS});
        }
        if (areaS != "Tất cả" && priceS == "Giảm dần" && typeS == "Tất cả"){
            cursorS = db.rawQuery("select * from ForRentDetails where type = ? order by price DESC",
                    new String[] {typeS,areaS});
        }
        if (areaS == "Tất cả" && priceS == "Tăng dần" && typeS == "Tất cả"){
            cursorS = db.rawQuery("select * from ForRentDetails order by price ASC",
                    new String[] {typeS,areaS});
        }
        if (areaS == "Tất cả" && priceS == "Giảm dần" && typeS == "Tất cả"){
            cursorS = db.rawQuery("select * from ForRentDetails order by price DESC",
                    new String[] {typeS,areaS});
        }

        while(cursorS.moveToNext()) {
            String address = cursorS.getString(0);   //0 is the number of address column database table
            String type = cursorS.getString(1);
            int price = Integer.parseInt(cursorS.getString(2));
            String area = cursorS.getString(3);
            String contact = cursorS.getString(4);
            String description =  cursorS.getString(5);



            ForRent newForRent = new ForRent(address, type, price, area, contact, description);
            forRentList.add(newForRent);
        }
        return forRentList;
    }*/
}
