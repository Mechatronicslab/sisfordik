package com.example.juli_soep.sekolah.BAK;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.juli_soep.sekolah.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Bak extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bak);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnGajihStaf)
    void Gajistaff(){
        Intent i = new Intent(Bak.this , InputGajiPegawai.class);
        startActivity(i);
        finish();
    }

    @OnClick(R.id.btnGajihSatpam)
    void Gajisatpam(){
        Intent i = new Intent(Bak.this , InputGajiSatpam.class);
        startActivity(i);
        finish();
    }

    @OnClick(R.id.btnDataSatpam)
    void ShowGajiSatpam(){
        Intent i = new Intent(Bak.this , ShowGajiSatpam.class);
        startActivity(i);
        finish();
    }
}
