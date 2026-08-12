package com.example.farmingapp;

public class ProductModel {
    String name, quantity, description, price, imageUrl, category;

    public ProductModel() { }

    public ProductModel(String name, String quantity, String description, String price, String imageUrl, String category) {
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    // getters
    public String getName() { return name; }
    public String getQuantity() { return quantity; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public String getCategory() { return category; }
}