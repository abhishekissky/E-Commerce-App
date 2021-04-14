package com.example.myapplication.Models;

public class AddressModel {

    String name;
    String mobile_no;
    String alternate_mobile_no;
    String address;
    String landmark;
    String pinCode;
    String city;
    String country;
    String state;
    String address_type;

    public AddressModel(String name, String mobile_no, String alternate_mobile_no, String address, String landmark, String pinCode, String city, String country, String state, String address_type) {
        this.name = name;
        this.mobile_no = mobile_no;
        this.alternate_mobile_no = alternate_mobile_no;
        this.address = address;
        this.landmark = landmark;
        this.pinCode = pinCode;
        this.city = city;
        this.country = country;
        this.state = state;
        this.address_type = address_type;
    }

    public AddressModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAlternate_mobile_no() {
        return alternate_mobile_no;
    }

    public void setAlternate_mobile_no(String alternate_mobile_no) {
        this.alternate_mobile_no = alternate_mobile_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress_type() {
        return address_type;
    }

    public void setAddress_type(String address_type) {
        this.address_type = address_type;
    }
}
