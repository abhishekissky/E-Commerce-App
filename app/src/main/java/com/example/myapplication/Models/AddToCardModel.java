package com.example.myapplication.Models;

import java.util.List;

public class AddToCardModel {

    String product_name;
    String product_qty_i_d;
    String productMrp;
    String gst;
    String seller_uid;
    String offerPrice;
    String discountPrice;
    String discountPercentage;
    String dispatchIn;
    String productUnit;
    List<String>imageUrl;
    String totalPrice;

    public AddToCardModel(String product_name, String product_qty_i_d, String productMrp, String gst, String seller_uid, String offerPrice, String discountPrice, String discountPercentage, String dispatchIn, String productUnit, List<String> imageUrl) {
        this.product_name = product_name;
        this.product_qty_i_d = product_qty_i_d;
        this.productMrp = productMrp;
        this.gst = gst;
        this.seller_uid = seller_uid;
        this.offerPrice = offerPrice;
        this.discountPrice = discountPrice;
        this.discountPercentage = discountPercentage;
        this.dispatchIn = dispatchIn;
        this.productUnit = productUnit;
        this.imageUrl = imageUrl;
    }

    public AddToCardModel() {
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_qty_i_d() {
        return product_qty_i_d;
    }

    public void setProduct_qty_i_d(String product_qty_i_d) {
        this.product_qty_i_d = product_qty_i_d;
    }

    public String getProductMrp() {
        return productMrp;
    }

    public void setProductMrp(String productMrp) {
        this.productMrp = productMrp;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
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

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
