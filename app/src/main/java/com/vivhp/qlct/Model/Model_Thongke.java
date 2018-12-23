package com.vivhp.qlct.Model;

import java.io.Serializable;

/**
 * Created by vivhp on 11/8/2016.
 */

public class Model_Thongke implements Serializable {

    int soTien;
    String tenNhom, tenKhoan;

    public Model_Thongke(int soTien, String tenNhom, String tenKhoan) {
        this.soTien = soTien;
        this.tenNhom = tenNhom;
        this.tenKhoan = tenKhoan;
    }

    public Model_Thongke() {

    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    public String getTenKhoan() {
        return tenKhoan;
    }

    public void setTenKhoan(String tenKhoan) {
        this.tenKhoan = tenKhoan;
    }

}
