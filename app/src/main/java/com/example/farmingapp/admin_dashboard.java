package com.example.farmingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class admin_dashboard extends AppCompatActivity {

    Button btnAddProduct, btnDeleteProduct, btnUpdateProduct, btnShowProducts, btnShowOrders, btnFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Initialize buttons
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnDeleteProduct = findViewById(R.id.btnDeleteProduct);
        btnUpdateProduct = findViewById(R.id.btnUpdateProduct);
        btnShowProducts = findViewById(R.id.btnShowProducts);
        btnShowOrders = findViewById(R.id.btnShowOrders);
        btnFeedback = findViewById(R.id.btnFeedback);

        // Add Product Button
        btnAddProduct.setOnClickListener(v -> {
           Intent intent = new Intent(admin_dashboard.this, AddProductChoiceActivity.class);
            startActivity(intent);
        });

        // Delete Product Button
        btnDeleteProduct.setOnClickListener(v -> {
           Intent intent = new Intent(admin_dashboard.this, DeleteProductChoiceActivity.class);
           startActivity(intent);
        });

        // Update Product Button
        btnUpdateProduct.setOnClickListener(v -> {
          Intent intent = new Intent(admin_dashboard.this, UpdateProductChoiceActivity.class);
          startActivity(intent);
        });


        btnShowProducts.setOnClickListener(v -> {
            Intent intent = new Intent(admin_dashboard.this, SeeProductsActivity.class);
            startActivity(intent);
        });


        btnShowOrders.setOnClickListener(v -> {
            Intent intent = new Intent(admin_dashboard.this, ViewAllOrdersActivity.class);
            startActivity(intent);
        });
        btnFeedback.setOnClickListener(v -> {
            startActivity(new Intent(admin_dashboard.this, ViewFeedbackActivity.class));
        });

    }
}
