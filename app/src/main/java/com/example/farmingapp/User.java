package com.example.farmingapp;

public class User {
    public String name, address, mobile, password;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String name, String address, String mobile, String password) {
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.password = password;
    }
}
