package com.example.farmingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLogin extends AppCompatActivity {

    private EditText etxtUsername, etxtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etxtUsername = findViewById(R.id.etxtUsername);
        etxtPassword = findViewById(R.id.etxtPassword);
        btnLogin = findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(this::btnLoginclick);
    }

    // Called when Login button is clicked
    public void btnLoginclick(View view) {
        String username = etxtUsername.getText().toString().trim();
        String password = etxtPassword.getText().toString().trim();

        if (username.equals("admin") && password.equals("123")) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            // Navigate to Admin Dashboard after successful login
            startActivity(new Intent(AdminLogin.this, admin_dashboard.class));
            finish(); // Close login screen
        } else {
            Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
        }
    }
}
