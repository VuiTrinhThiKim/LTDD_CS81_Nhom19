package com.example.hotelhunter.dbForRent;

public class ForRent {
    private String address;
    private String type;
    private int price;
    private String area;
    private String contact;
    private String description;

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
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

    public void setPrice(int price) {
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
