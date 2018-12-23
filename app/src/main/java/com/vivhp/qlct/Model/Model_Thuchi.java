package com.vivhp.qlct.Model;

/**
 * Created by vivhp on 10/22/2016.
 */

public class Model_Thuchi {

    int mathuchi;
    int sotien;
    String ngay, lydo;
    int id_tk;
    int manhom;


    public Model_Thuchi() {
    }

    public Model_Thuchi(int mathuchi, int sotien, String ngay, String lydo, int id_tk, int manhom) {
        this.mathuchi = mathuchi;
        this.sotien = sotien;
        this.ngay = ngay;
        this.lydo = lydo;
        this.id_tk = id_tk;
        this.manhom = manhom;
    }

    public Model_Thuchi(int sotien, String ngay, String lydo, int id_tk, int manhom) {
        this.sotien = sotien;
        this.ngay = ngay;
        this.lydo = lydo;
        this.id_tk = id_tk;
        this.manhom = manhom;
    }

    public Model_Thuchi(int sotien) {
        this.sotien = sotien;
    }

    public int getMathuchi() {
        return mathuchi;
    }

    public void setMathuchi(int mathuchi) {
        this.mathuchi = mathuchi;
    }

    public int getSotien() {
        return sotien;
    }

    public void setSotien(int sotien) {
        this.sotien = sotien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getLydo() {
        return lydo;
    }

    public void setLydo(String lydo) {
        this.lydo = lydo;
    }

    public int getId_tk() {
        return id_tk;
    }

    public void setId_tk(int id_tk) {
        this.id_tk = id_tk;
    }

    public int getManhom() {
        return manhom;
    }

    public void setManhom(int manhom) {
        this.manhom = manhom;
    }
}
