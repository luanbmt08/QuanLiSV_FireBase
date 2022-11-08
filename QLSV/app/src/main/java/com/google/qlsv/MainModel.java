package com.google.qlsv;

public class MainModel {
    String MaSV, Ten,Gmail,Surl;

    MainModel() {

    }

    public MainModel(String maSV, String ten, String gmail, String surl) {
        MaSV = maSV;
        Ten = ten;
        Gmail = gmail;
        Surl = surl;
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String maSV) {
        MaSV = maSV;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getGmail() {
        return Gmail;
    }

    public void setGmail(String gmail) {
        Gmail = gmail;
    }

    public String getSurl() {
        return Surl;
    }

    public void setSurl(String surl) {
        Surl = surl;
    }
}

