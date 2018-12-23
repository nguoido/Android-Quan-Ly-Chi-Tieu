package com.vivhp.qlct.Model;

/**
 * Created by vivhp on 10/22/2016.
 */

public class Model_Taikhoan {

    int _id;
    String tentk;
    String loaihinh;
    int sotien;

    public Model_Taikhoan() {
    }

    public Model_Taikhoan(int _id, String tentk, String loaihinh, int sotien) {
        this._id = _id;
        this.tentk = tentk;
        this.loaihinh = loaihinh;
        this.sotien = sotien;
    }

    public Model_Taikhoan(String tentk, String loaihinh, int sotien) {
        this.tentk = tentk;
        this.loaihinh = loaihinh;
        this.sotien = sotien;
    }

    public Model_Taikhoan(int _id, int sotien) {
        this._id = _id;
        this.sotien = sotien;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTentk() {
        return tentk;
    }

    public void setTentk(String tentk) {
        this.tentk = tentk;
    }

    public String getLoaihinh() {
        return loaihinh;
    }

    public void setLoaihinh(String loaihinh) {
        this.loaihinh = loaihinh;
    }

    public int getSotien() {
        return sotien;
    }

    public void setSotien(int sotien) {
        this.sotien = sotien;
    }
}
