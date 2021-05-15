package com.example.hotelhunter.ui.account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hotelhunter.R;
import com.example.hotelhunter.dbForRent.DBHelper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;





public class MHDangNhap extends AppCompatActivity {


    private SQLiteDatabase dbSql1;
    DBTaiKhoan dbTaiKhoan;

    TextView tvData;

    private String nameData1;
    private String passData1;


    EditText name1, pass1;
    Button btnLogin1;

    String strName1;
    String strPass1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_h_dang_nhap);

            OpenData();
            SaveObject();

            dbTaiKhoan = new DBTaiKhoan(this);



        /*
        if (name.getText().toString() == ""){
            isLogined = false;
            btnLogout.setVisibility(View.INVISIBLE);
        }
        else {
            isLogined = true;
            btnLogin.setVisibility(View.INVISIBLE);
            btnRegister.setVisibility(View.INVISIBLE);
        }

         */

/*
            btnLogin1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MHDangNhap.this, "Login", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MHDangNhap.this, DNCussess.class);
                    startActivity(intent);


                AddForRentFragment forRentFragment = new AddForRentFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), forRentFragment, "findThisFragment")
                        .commit();

                }
            });

 */

        btnLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strName1 = name1.getText().toString();
                strPass1 = pass1.getText().toString();


                String sql = "SELECT * FROM UserDetails1";
                Cursor cursor = dbSql1.rawQuery(sql, null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    nameData1 = cursor.getString(0);
                    passData1 = cursor.getString(1);
                    String dt1 = "name: " + nameData1 + "\nPass: " + passData1 + "\n\n";
                    tvData.append(dt1);
                    if (nameData1.equals(strName1) && passData1.equals(strPass1)){
                        Intent intent = new Intent(MHDangNhap.this, MHDangNhap.class);
                        startActivity(intent);
                    }
                    cursor.moveToNext();
                }
                Toast.makeText(MHDangNhap.this, "Tên Tài Khoản Hoặc Mật Khẩu Không Chính Xác!", Toast.LENGTH_SHORT).show();
            }



/*
                if (KiemTraDN() == false) {
                    Toast.makeText(MHDangNhap.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                    Out();
                } else {

                    Intent intent = new Intent(MHDangNhap.this, DNCussess.class);
                    startActivity(intent);
                }

 */





        });





        }






    public boolean KiemTraDN () {

        String sql1 = "SELECT * FROM UserDetails1";
        Cursor cursor = dbSql1.rawQuery(sql1, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            nameData1 = cursor.getString(0);
            passData1 = cursor.getString(1);
            if (strName1.equals(nameData1) && strPass1.equals(passData1)) {
                return true;
            }
            cursor.moveToNext();
        }
        return false;
    }



    public boolean KiemTra () {

            String sql = "SELECT * FROM UserDetails1";
            Cursor cursor = dbSql1.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                nameData1 = cursor.getString(0);
                passData1 = cursor.getString(1);
                if (strName1.equals(nameData1)) {
                    return true;
                }
                cursor.moveToNext();
            }
            return false;
        }


        public void Out () {
            String sql = "SELECT * FROM UserDetails1";
            Cursor cursor = dbSql1.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                nameData1 = cursor.getString(0);
                passData1 = cursor.getString(1);
                String dt1 = "name: " + nameData1 + "\nPass: " + passData1 + "\n\n";
                tvData.append(dt1);
                cursor.moveToNext();
            }
        }


        private void OpenData() {
            dbSql1 = openOrCreateDatabase("Userdata1.db", MODE_PRIVATE, null);
        }


    private void SaveObject () {
        tvData = findViewById(R.id.tvData);
        name1 = (EditText)findViewById(R.id.et_nameDN1);
        pass1 = (EditText)findViewById(R.id.et_passDN1);
        btnLogin1 = (Button)findViewById(R.id.btnDN1);
    }

}