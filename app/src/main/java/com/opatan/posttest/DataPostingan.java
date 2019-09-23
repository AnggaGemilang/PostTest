package com.opatan.posttest;

public class DataPostingan {

    private String judul, deksripsi, id, waktu;

    public DataPostingan(){


    }

    public DataPostingan(String judul, String deksripsi, String id, String waktu) {
        this.judul = judul;
        this.deksripsi = deksripsi;
        this.id = id;
        this.waktu = waktu;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeksripsi() {
        return deksripsi;
    }

    public void setDeksripsi(String deksripsi) {
        this.deksripsi = deksripsi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
