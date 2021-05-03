 package com.example.hotelhunter.models;

public class PhongTro {
    private String ID;
    private String type;
    private String address;
    private String price;
    private String phoneNumber;
    private String area;
    private String detail;

    public PhongTro() {
    }
    // Construct Phong Tro
    public PhongTro(String type, String address, String price, String area, String phoneNumber, String detail) {
        this.type = type;
        this.address = address;
        this.price = price;
        this.area = area;
        this.phoneNumber = phoneNumber;
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public String getArea() {
        return area;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDetail() {
        return detail;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
