package com.example.hotelhunter.dbForRent;

public class ForRent {
    private String address;
    private String type;
    private String price;
    private String area;
    private String contact;
    private String description;

    public ForRent(String address, String type, String price, String area, String contact,String description) {
        this.address = address;
        this.type = type;
        this.price = price;
        this.area = area;
        this.contact = contact;
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public String getArea() {
        return area;
    }

    public String getContact() {
        return contact;
    }

    public String getDescription() {
        return description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
