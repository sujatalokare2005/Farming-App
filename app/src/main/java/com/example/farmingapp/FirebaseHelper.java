package com.example.farmingapp;

public class FirebaseHelper {
    private String strProductName, strProductPrice, strProductDescription, strProductQuantity;

    public FirebaseHelper() {
        // Default constructor required for Firebase
    }
    private String id;
    public FirebaseHelper(String strProductName, String strProductPrice, String strProductDescription, String strProductQuantity) {
        this.strProductName = strProductName;
        this.strProductPrice = strProductPrice;
        this.strProductDescription = strProductDescription;
        this.strProductQuantity = strProductQuantity;
    }

    // Getters and setters (your existing ones are fine)
    public String getStrProductName() { return strProductName; }
    public void setStrProductName(String strProductName) { this.strProductName = strProductName; }

    public String getStrProductPrice() { return strProductPrice; }
    public void setStrProductPrice(String strProductPrice) { this.strProductPrice = strProductPrice; }

    public String getStrProductDescription() { return strProductDescription; }
    public void setStrProductDescription(String strProductDescription) { this.strProductDescription = strProductDescription; }

    public String getStrProductQuantity() { return strProductQuantity; }
    public void setStrProductQuantity(String strProductQuantity) { this.strProductQuantity = strProductQuantity; }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + strProductName + '\'' +
                ", price='" + strProductPrice + '\'' +
                ", description='" + strProductDescription + '\'' +
                ", quantity='" + strProductQuantity + '\'' +
                '}';
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}