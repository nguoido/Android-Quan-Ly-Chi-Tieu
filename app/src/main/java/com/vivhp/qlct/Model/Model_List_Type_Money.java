package com.vivhp.qlct.Model;

/**
 * Created by vivhp on 10/26/2016.
 */

public class Model_List_Type_Money {
    int image;
    String name;

    public Model_List_Type_Money() {

    }

    public Model_List_Type_Money(String name) {
        this.name = name;
    }

    public Model_List_Type_Money(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
