package com.example.hotelhunter.ui.account;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelhunter.R;

//
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelhunter.dbForRent.DBHelper;
import com.example.hotelhunter.ui.account.DBTaiKhoan;
//


public class MHDangKy extends AppCompatActivity {

    private SQLiteDatabase dbSql;
    DBTaiKhoan dbTaiKhoan;

    TextView tvData;

    private String nameData;
    private String passData;


    EditText name2, pass2;
    Button btnDK2;

    String strName2;
    String strPass2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_h_dang_ky);


        OpenData();
        SaveObject();
        dbTaiKhoan = new DBTaiKhoan(this);




        btnDK2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strName2 = name2.getText().toString();
                strPass2 = pass2.getText().toString();

/*  Code chạy
                boolean  add =  dbHelper.addUserdata(strName2, strPass2);
                if (add == true){
                    Toast.makeText(MHDangKy.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(MHDangKy.this, "Đăng ký Thất bại!", Toast.LENGTH_SHORT).show();

 */



                if(strName2.length()==0 || strPass2.length()==0){
                    Toast.makeText(MHDangKy.this, "Vui lòng nhập Tên và Pass",Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean ktra = KiemTra();
                    if (ktra == true) {
                        Toast.makeText(MHDangKy.this, "User đã tồn tại!", Toast.LENGTH_SHORT).show();
                    } else {
                        dbTaiKhoan.addUserdata(strName2, strPass2);
                        Toast.makeText(MHDangKy.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    }

                }




            }


        });

    }


    public boolean KiemTra() {

        String sql = "SELECT * FROM UserDetails1";
        Cursor cursor = dbSql.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            nameData = cursor.getString(0);
            passData = cursor.getString(1);
            if (strName2.equals(nameData)) {
                return true;
            }
            cursor.moveToNext();
        }
        return false;
    }




    private void OpenData () {
        dbSql = openOrCreateDatabase("Userdata1.db", MODE_PRIVATE, null);
    }


    private void SaveObject () {
        name2 = findViewById(R.id.et_nameDK2);
        pass2 = findViewById(R.id.et_passDK2);
        btnDK2 = findViewById(R.id.btnDK2);
        tvData = findViewById(R.id.tvData);
    }


}