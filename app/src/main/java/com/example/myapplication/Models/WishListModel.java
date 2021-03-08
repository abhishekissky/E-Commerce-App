package com.example.myapplication.Models;

public class WishListModel {

    String product_name;
    String product_mrp_digit;
    int product_image;

    public WishListModel(String product_name, String product_mrp_digit,int product_image) {
        this.product_name = product_name;
        this.product_mrp_digit = product_mrp_digit;
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_mrp_digit() {
        return product_mrp_digit;
    }

    public void setProduct_mrp_digit(String product_mrp_digit) {
        this.product_mrp_digit = product_mrp_digit;
    }

    public int getProduct_image() {
        return product_image;
    }

    public void setProduct_image(int product_image) {
        this.product_image = product_image;
    }
}
