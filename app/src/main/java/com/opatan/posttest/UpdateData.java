package com.opatan.posttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UpdateData extends AppCompatActivity {

    private EditText judul, deskripsi;
    private Button save;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Edit Data");
        ab.setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        judul = findViewById(R.id.judul_edittext);
        deskripsi = findViewById(R.id.deskripsi_edittext);
        save = findViewById(R.id.tambah_data);

        Intent intent = getIntent();
        final String id_data = intent.getStringExtra("id");
        String judul_data = intent.getStringExtra("judul");
        String deskripsi_data = intent.getStringExtra("deskripsi");

        judul.setText(judul_data);
        deskripsi.setText(deskripsi_data);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KoneksiDatabase k = new KoneksiDatabase(getApplicationContext());
                DataPostingan dataPostingan = new DataPostingan();
                String judul_data = judul.getText().toString();
                String deskripsi_data = deskripsi.getText().toString();
                String waktu_data  = getDateTime();
                if (judul_data.isEmpty() || deskripsi_data.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Isi semua data terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    dataPostingan.setId(id_data);
                    dataPostingan.setJudul(judul_data);
                    dataPostingan.setDeksripsi(deskripsi_data);
                    dataPostingan.setWaktu(waktu_data);
                    k.update(dataPostingan);
                    UpdateData.super.onBackPressed();
                    Toast.makeText(getApplicationContext(),"Data "+ judul_data +" Diubah", Toast.LENGTH_SHORT).show();
                    k.selectUserData();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });

    }

    private String getDateTime(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return sdf.format(date);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.format_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.btn_haus:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                KoneksiDatabase k = new KoneksiDatabase(getApplicationContext());
                                Intent jaja = getIntent();
                                Toast.makeText(getApplicationContext(),"Data "+jaja.getStringExtra("judul")+" sukses dihapus",Toast.LENGTH_SHORT).show();
                                k.delete(jaja.getStringExtra("id"));
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Yakin Akan Menghapus?")
                        .setPositiveButton("Ya", dialogClickListener)
                        .setNegativeButton("Tidak", dialogClickListener)
                        .show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
