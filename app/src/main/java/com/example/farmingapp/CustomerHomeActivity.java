package com.example.farmingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerHomeActivity extends AppCompatActivity {

    Button btnViewProducts, btnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        btnViewProducts = findViewById(R.id.btnProducts);
        btnCart = findViewById(R.id.btnCart);

        if (btnViewProducts != null) {
            btnViewProducts.setOnClickListener(v -> {
                // Go to SeeProductsActivity instead of ProductsActivity
               Intent intent = new Intent(CustomerHomeActivity.this, SeeProductsActivity.class);
               startActivity(intent);
            });
        } else {
            Log.e("CustomerHomeActivity", "btnProducts not found!");
        }

        if (btnCart != null) {
            btnCart.setOnClickListener(v -> {
               Intent intent = new Intent(CustomerHomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            });
        } else {
            Log.e("CustomerHomeActivity", "btnCart not found!");
}
}
}
