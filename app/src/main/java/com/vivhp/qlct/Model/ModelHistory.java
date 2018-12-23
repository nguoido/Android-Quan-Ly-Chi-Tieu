package com.vivhp.qlct.Model;

/**
 * Created by vivhp on 10/29/2016.
 */

public class ModelHistory {
    int sotien;
    String tenkhoan;
    String lydo;
    String tentk;
    String tennhom;

    public ModelHistory() {

    }

    public ModelHistory(int sotien, String tenkhoan, String lydo, String tentk, String tennhom) {
        this.sotien = sotien;
        this.tenkhoan = tenkhoan;
        this.lydo = lydo;
        this.tentk = tentk;
        this.tennhom = tennhom;
    }

    public int getSotien() {
        return sotien;
    }

    public void setSotien(int sotien) {
        this.sotien = sotien;
    }

    public String getTenkhoan() {
        return tenkhoan;
    }

    public void setTenkhoan(String tenkhoan) {
        this.tenkhoan = tenkhoan;
    }

    public String getLydo() {
        return lydo;
    }

    public void setLydo(String lydo) {
        this.lydo = lydo;
    }

    public String getTentk() {
        return tentk;
    }

    public void setTentk(String tentk) {
        this.tentk = tentk;
    }

    public String getTennhom() {
        return tennhom;
    }

    public void setTennhom(String tennhom) {
        this.tennhom = tennhom;
    }
}
