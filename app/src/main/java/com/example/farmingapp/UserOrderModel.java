package com.example.farmingapp;

public class UserOrderModel {
    public String name, mobile, address, productName, quantity, paymentType;

    public UserOrderModel() {}

    public UserOrderModel(String name, String mobile, String address, String productName, String quantity, String paymentType) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.productName = productName;
        this.quantity = quantity;
        this.paymentType = paymentType;
    }
}
