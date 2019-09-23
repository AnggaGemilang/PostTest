package com.opatan.posttest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InputData extends AppCompatActivity {

    private EditText judul, deskripsi;
    private Button save;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Input Data");
        ab.setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        judul = findViewById(R.id.judul_edittext);
        deskripsi = findViewById(R.id.deskripsi_edittext);
        save = findViewById(R.id.tambah_data);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KoneksiDatabase k = new KoneksiDatabase(getApplicationContext());
                DataPostingan m = new DataPostingan();
                String judul_data = judul.getText().toString();
                String deskripsi_data = deskripsi.getText().toString();
                String waktu_data  = getDateTime();
                if (judul_data.isEmpty() || deskripsi_data.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Isi semua data terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    m.setJudul(judul_data);
                    m.setDeksripsi(deskripsi_data);
                    m.setWaktu(waktu_data);
                    k.insert(m);
                    InputData.super.onBackPressed();
                    Toast.makeText(getApplicationContext(),"Data "+judul_data+" Masuk", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    k.selectUserData();
                }
            }
        });

    }

    private String getDateTime(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return sdf.format(date);

    }


}
