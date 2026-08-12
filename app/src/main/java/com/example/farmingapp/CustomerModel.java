package com.example.farmingapp;

public class CustomerModel {
    String name, address, mobile;

    public CustomerModel() {
    }

    public CustomerModel(String name, String address, String mobile) {
        this.name = name;
        this.address = address;
        this.mobile = mobile;
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getMobile() { return mobile; }
}
