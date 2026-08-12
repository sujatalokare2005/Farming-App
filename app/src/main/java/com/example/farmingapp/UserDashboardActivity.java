package com.example.farmingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserDashboardActivity extends AppCompatActivity {

    Button btnOrders, btnCart, btnFeedback;  // Added feedback button

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        // Initialize buttons
        btnOrders = findViewById(R.id.btnOrders);
        btnCart = findViewById(R.id.btnCart);
        btnFeedback = findViewById(R.id.btnFeedback); // Feedback button

        // Orders button click
        btnOrders.setOnClickListener(v -> {
            startActivity(new Intent(UserDashboardActivity.this, UserOrdersActivity.class));
        });

        // Cart button click
        btnCart.setOnClickListener(v -> {
            startActivity(new Intent(UserDashboardActivity.this, UserCartActivity.class));
        });

        // Feedback button click → open feedback activity
        btnFeedback.setOnClickListener(v -> {
            startActivity(new Intent(UserDashboardActivity.this, com.example.farmingapp.UserFeedback.class));
        });
    }
}
