package com.opatan.posttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PostinganAdapter.OnUserClickListener {

    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    Context context;
    FloatingActionButton input_data;
    Context con;

    List<DataPostingan> dataPostinganList;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        getSupportActionBar().setTitle("Notes");

        con = getApplicationContext();

        getSupportActionBar().setTitle("Daftar Notes");

        setupRecyclerView();

        input_data = findViewById(R.id.tambah_data);
        input_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), InputData.class);
                startActivity(i);
            }
        });

        refreshLayout = findViewById(R.id.swipeToRefresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        refreshLayout.setRefreshing(false);

                        setupRecyclerView();

                    }
                }, 2000);
            }
        });
    }


    private void setupRecyclerView() {
        KoneksiDatabase db = new KoneksiDatabase(this);

        dataPostinganList = new ArrayList<>();
        dataPostinganList = db.selectUserData();
        PostinganAdapter adapter = new PostinganAdapter(this, this, dataPostinganList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void openDialogInputData(String judul, String deksripsi, String waktu, String id) {
        DialogJava exampleDialog = new DialogJava();
        Bundle args = new Bundle();
        args.putString("judul",judul);
        args.putString("deskripsi",deksripsi);
        args.putString("waktu",waktu);
        args.putString("id",id);
        if (exampleDialog.getDialog() != null && exampleDialog.getDialog().getWindow() != null) {
            exampleDialog.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            exampleDialog.getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        exampleDialog.setArguments(args);
        exampleDialog.show(getSupportFragmentManager(), "Dialog Ketentuan");
    }

    @Override
    public void onUserClick(String judul, String deskripsi, String waktu, String id) {
        openDialogInputData(judul, deskripsi, waktu, id);
    }
}
