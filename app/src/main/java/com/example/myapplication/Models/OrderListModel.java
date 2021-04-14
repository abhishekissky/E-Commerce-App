package com.example.myapplication.Models;

public class OrderListModel {

        String customerUid;
        String discountPrice;
        String dispatchIn;
        String gst;
        String offerPrice;
        String productMrp;
        String productUnit;
        String product_name;
        String product_qty_i_d;
        String customerAddress;
        String customerName;
        String discountPercentage;


        public OrderListModel() {
        }

        public OrderListModel(String customerUid, String discountPrice, String dispatchIn, String gst, String offerPrice, String productMrp, String productUnit, String product_name, String product_qty_i_d) {
            this.customerUid = customerUid;
            this.discountPrice = discountPrice;
            this.dispatchIn = dispatchIn;
            this.gst = gst;
            this.offerPrice = offerPrice;
            this.productMrp = productMrp;
            this.productUnit = productUnit;
            this.product_name = product_name;
            this.product_qty_i_d = product_qty_i_d;
        }

        public String getCustomerUid() {
            return customerUid;
        }

        public void setCustomerUid(String customerUid) {
            this.customerUid = customerUid;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getDispatchIn() {
            return dispatchIn;
        }

        public void setDispatchIn(String dispatchIn) {
            this.dispatchIn = dispatchIn;
        }

        public String getGst() {
            return gst;
        }

        public void setGst(String gst) {
            this.gst = gst;
        }

        public String getOfferPrice() {
            return offerPrice;
        }

        public void setOfferPrice(String offerPrice) {
            this.offerPrice = offerPrice;
        }

        public String getProductMrp() {
            return productMrp;
        }

        public void setProductMrp(String productMrp) {
            this.productMrp = productMrp;
        }

        public String getProductUnit() {
            return productUnit;
        }

        public void setProductUnit(String productUnit) {
            this.productUnit = productUnit;
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

        public String getCustomerAddress() {
            return customerAddress;
        }

        public void setCustomerAddress(String customerAddress) {
            this.customerAddress = customerAddress;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
