package com.example.farmingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCustomer = findViewById(R.id.btnCustomer);
        Button btnAdmin = findViewById(R.id.btnAdmin);

        // Navigate to Customer Home
        btnCustomer.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CustomerHomeActivity.class));
        });

        // Navigate to Admin Login
        btnAdmin.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AdminLogin.class)));
    }
}
