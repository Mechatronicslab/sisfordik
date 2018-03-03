package com.example.juli_soep.sekolah.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.juli_soep.sekolah.home.MainActivity;
import com.example.juli_soep.sekolah.R;

public class AdminActivity extends AppCompatActivity {

    ImageButton daftarguru, daftarstaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        daftarguru = (ImageButton)findViewById(R.id.btnDaftarGuru);
        daftarguru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this, DaftarGuru.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AdminActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
