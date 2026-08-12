package com.example.farmingapp;

public class CartModel {

    public String id;               // Firebase push ID for deletion
    public String productName;      // उत्पादनाचे नाव
    public String productPrice;     // किंमत
    public String productQuantity;  // प्रमाण
    public String productDescription; // वर्णन

    // Default constructor required for Firebase
    public CartModel() {}

    // Constructor to create a new Cart item
    public CartModel(String productName, String productPrice, String productQuantity, String productDescription) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productDescription = productDescription;
    }
}
