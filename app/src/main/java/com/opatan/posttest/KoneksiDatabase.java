package com.opatan.posttest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class KoneksiDatabase extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Catatan";
    private static final String TABLE_NAME = "tbl_catatan";
    private static final String KEY_ID = "id";

    public KoneksiDatabase(Context c) {
        super(c, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY, judul TEXT, deskripsi TEXT, waktu TEXT)";
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        String sql = ("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(sql);
        onCreate(db);
    }

    public void insert(DataPostingan dp) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, dp.getId());
        values.put("judul", dp.getJudul());
        values.put("deskripsi", dp.getDeksripsi());
        values.put("waktu", getDateTime());
        db.insert(TABLE_NAME, null, values);
    }

    private String getDateTime(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return sdf.format(date);

    }

    public List<DataPostingan> selectUserData() {
        ArrayList<DataPostingan> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {KEY_ID, "judul", "deskripsi", "waktu"};
        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
        while (c.moveToNext()) {
            String id = c.getString(0);
            String judul = c.getString(1);
            String deskrisi = c.getString(2);
            String waktu = c.getString(3);
            DataPostingan dp = new DataPostingan();
            dp.setId(id);
            dp.setJudul(judul);
            dp.setDeksripsi(deskrisi);
            dp.setWaktu(waktu);
            list.add(dp);
        }
        return list;
    }

    public void delete(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id ='" + id + "'";
        db.delete(TABLE_NAME, whereClause, null);
    }

    public void update(DataPostingan dp) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("judul", dp.getJudul());
        values.put("deskripsi", dp.getDeksripsi());
        values.put("waktu", getDateTime());
        String whereClause = "id ='" + dp.getId() + "'";
        db.update(TABLE_NAME, values, whereClause, null);
    }

}
