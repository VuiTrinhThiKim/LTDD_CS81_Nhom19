package com.example.hotelhunter.ui.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hotelhunter.R;

public class DNCussess extends AppCompatActivity {
        Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_n_cussess);

        btnLogout = findViewById(R.id.btn_Logout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DNCussess.this,MHDangNhap.class);
                startActivity(intent);
            }
        });
    }
}