package com.example.myapplication.Models;

import java.util.List;

public class ProductViewListModel {

    String product_sub_category;

    String product_category;

    String product_address;

    String product_name;

    String product_price;

    String product_quantity;

    String product_qty;

    String uid;

    String discount;
    String dispatch_in;
    String gst;
    String discountUnit;

    String price_unit;
    String quantity_unit;
    String  cod;

    List<String>image_url;

    public ProductViewListModel(){
    }

    public ProductViewListModel(String product_sub_category, String product_category, String product_address
            , String product_name, String product_price,
                                String product_quantity) {
        this.product_sub_category = product_sub_category;
        this.product_category = product_category;
        this.product_address = product_address;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_quantity = product_quantity;

        this.product_qty = product_qty;
    }

    public String getProduct_sub_category() {
        return product_sub_category;
    }

    public void setProduct_sub_category(String product_sub_category) {
        this.product_sub_category = product_sub_category;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_address() {
        return product_address;
    }

    public void setProduct_address(String product_address) {
        this.product_address = product_address;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(String product_qty) {
        this.product_qty = product_qty;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getImage_url() {
        return image_url;
    }

    public void setImage_url(List<String> image_url) {
        this.image_url = image_url;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String  getCod() {
        return cod;
    }

    public void setCod(String  cod) {
        this.cod = cod;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDispatch_in() {
        return dispatch_in;
    }

    public void setDispatch_in(String dispatch_in) {
        this.dispatch_in = dispatch_in;
    }

    public String getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(String price_unit) {
        this.price_unit = price_unit;
    }

    public String getQuantity_unit() {
        return quantity_unit;
    }

    public void setQuantity_unit(String quantity_unit) {
        this.quantity_unit = quantity_unit;
    }

    public String getDiscountUnit() {
        return discountUnit;
    }

    public void setDiscountUnit(String discountUnit) {
        this.discountUnit = discountUnit;
    }
}
