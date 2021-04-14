package com.example.myapplication.Models;

import java.util.List;

public class WishListModel {

    String gst;
    String productMrp;
    String product_name;
//    String product_qty;
//    String product_qty_i_d;
    String seller_uid;
    String offerPrice;
    String discountPrice;
    String discountPercentage;
    String dispatchIn;
    String  productUnit;
    String cod;
    List<String>imageUrl;

    public WishListModel(String gst, String productMrp, String product_name, String product_qty, String product_qty_i_d, String seller_uid) {
        this.gst = gst;
        this.productMrp = productMrp;
        this.product_name = product_name;


        this.seller_uid = seller_uid;
    }

    public WishListModel() {
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getProductMrp() {
        return productMrp;
    }

    public void setProductMrp(String productMrp) {
        this.productMrp = productMrp;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSeller_uid() {
        return seller_uid;
    }

    public void setSeller_uid(String seller_uid) {
        this.seller_uid = seller_uid;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getDispatchIn() {
        return dispatchIn;
    }

    public void setDispatchIn(String dispatchIn) {
        this.dispatchIn = dispatchIn;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }
}
