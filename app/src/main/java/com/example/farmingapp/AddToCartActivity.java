package com.example.farmingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddToCartActivity extends AppCompatActivity {

    TextView tvProductName, tvProductPrice, tvProductQuantity, tvProductDescription;
    Button btnAddToCart;
    String productName, productPrice, productQuantity, productDescription, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvProductQuantity = findViewById(R.id.tvProductQuantity);
        tvProductDescription = findViewById(R.id.tvProductDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        // उत्पादनाची माहिती मिळवा
        productName = getIntent().getStringExtra("productName");
        productPrice = getIntent().getStringExtra("productPrice");
        productQuantity = getIntent().getStringExtra("productQuantity");
        productDescription = getIntent().getStringExtra("productDescription");

        // माहिती दाखवा मराठीत
        tvProductName.setText("उत्पादनाचे नाव: " + safe(productName));
        tvProductPrice.setText("किंमत: ₹" + safe(productPrice));
        tvProductQuantity.setText("प्रमाण: " + safe(productQuantity));
        tvProductDescription.setText("वर्णन: " + safe(productDescription));

        // लॉग इन केलेला वापरकर्ता तपासा
        SharedPreferences prefs = getSharedPreferences("FarmingAppPrefs", MODE_PRIVATE);
        userId = prefs.getString("loggedInUserId", null);

        btnAddToCart.setOnClickListener(v -> {
            if (userId == null) {
                Toast.makeText(this, "वापरकर्ता लॉग इन केलेला नाही", Toast.LENGTH_SHORT).show();
                return;
            }

            // डेटा UsersCart/{userId} मध्ये जतन करा
            DatabaseReference cartRef = FirebaseDatabase.getInstance()
                    .getReference("UsersCart")
                    .child(userId);

            CartModel cartItem = new CartModel(productName, productPrice, productQuantity, productDescription);
            cartRef.push().setValue(cartItem)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "उत्पादन कार्टमध्ये जोडले गेले!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "कार्टमध्ये उत्पादन जोडताना त्रुटी आली", Toast.LENGTH_SHORT).show());
        });
    }

    private String safe(String text) {
        return (text == null || text.trim().isEmpty()) ? "उपलब्ध नाही" : text;
    }
}
