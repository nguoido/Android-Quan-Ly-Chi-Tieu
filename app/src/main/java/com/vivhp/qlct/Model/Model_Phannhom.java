package com.vivhp.qlct.Model;

import java.io.Serializable;

/**
 * Created by vivhp on 10/22/2016.
 */

public class Model_Phannhom implements Serializable{

    //Initializing Variable
    int manhom;
    String tennhom;
    String tenkhoan;

    //Constructor
    public Model_Phannhom() {

    }

    public Model_Phannhom(int manhom, String tennhom, String tenkhoan) {
        this.manhom = manhom;
        this.tennhom = tennhom;
        this.tenkhoan = tenkhoan;
    }

    public Model_Phannhom(String tennhom, String tenkhoan) {
        this.tennhom = tennhom;
        this.tenkhoan = tenkhoan;
    }

    public Model_Phannhom(int manhom) {
        this.manhom = manhom;
    }

    //Getter and Setter Method
    public int getManhom() {
        return manhom;
    }

    public void setManhom(int manhom) {
        this.manhom = manhom;
    }

    public String getTennhom() {
        return tennhom;
    }

    public void setTennhom(String tennhom) {
        this.tennhom = tennhom;
    }

    public String getTenkhoan() {
        return tenkhoan;
    }

    public void setTenkhoan(String tenkhoan) {
        this.tenkhoan = tenkhoan;
    }
}
