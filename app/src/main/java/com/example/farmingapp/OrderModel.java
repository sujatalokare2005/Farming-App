package com.example.farmingapp;

public class OrderModel {
    private String customerName;
    private String mobile;
    private String address;
    private String productName;
    private String quantity;
    private String paymentMode;

    public OrderModel() {
        // Default constructor required for Firebase
    }

    public OrderModel(String customerName, String mobile, String address,
                      String productName, String quantity, String paymentMode) {
        this.customerName = customerName;
        this.mobile = mobile;
        this.address = address;
        this.productName = productName;
        this.quantity = quantity;
        this.paymentMode = paymentMode;
    }

    // ✅ Getters
    public String getCustomerName() { return customerName; }
    public String getMobile() { return mobile; }
    public String getAddress() { return address; }
    public String getProductName() { return productName; }
    public String getQuantity() { return quantity; }
    public String getPaymentMode() { return paymentMode; }

    // ✅ Setters (optional, but good for Firebase)
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public void setAddress(String address) { this.address = address; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
}
