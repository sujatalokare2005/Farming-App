package com.example.farmingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlaceSeedsOrderActivity extends AppCompatActivity {

    TextView tvProductName;
    EditText etCustomerName, etCustomerMobile, etCustomerAddress, etQuantity;
    CheckBox cbCashOnDelivery;
    Button btnPlaceOrder;
    String productName, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        tvProductName = findViewById(R.id.tvProductName);
        etCustomerName = findViewById(R.id.etCustomerName);
        etCustomerMobile = findViewById(R.id.etCustomerMobile);
        etCustomerAddress = findViewById(R.id.etCustomerAddress);
        etQuantity = findViewById(R.id.etQuantity);
        cbCashOnDelivery = findViewById(R.id.cbCashOnDelivery);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        // Get product name from intent
        productName = getIntent().getStringExtra("productName");
        tvProductName.setText(productName);

        // Get logged-in user ID from SharedPreferences
        userId = getSharedPreferences("FarmingAppPrefs", MODE_PRIVATE)
                .getString("loggedInUserId", null);

        if (userId == null) {
            Toast.makeText(this, "कृपया प्रथम लॉगिन करा", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Pre-fill customer info from Users node
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    etCustomerName.setText(user.name);
                    etCustomerMobile.setText(user.mobile);
                    etCustomerAddress.setText(user.address);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });

        btnPlaceOrder.setOnClickListener(v -> placeOrder());
    }

    private void placeOrder() {
        String name = etCustomerName.getText().toString().trim();
        String mobile = etCustomerMobile.getText().toString().trim();
        String address = etCustomerAddress.getText().toString().trim();
        String quantity = etQuantity.getText().toString().trim();
        boolean cod = cbCashOnDelivery.isChecked();

        if (name.isEmpty() || mobile.isEmpty() || address.isEmpty() || quantity.isEmpty()) {
            Toast.makeText(this, "कृपया सर्व तपशील भरा", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userId == null) {
            Toast.makeText(this, "कृपया प्रथम लॉगिन करा", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create OrderModel object
        OrderModel order = new OrderModel(
                name,
                mobile,
                address,
                productName,
                quantity,
                cod ? "Cash on Delivery" : "Not selected"
        );

        // 1️⃣ Save under user's orders
        DatabaseReference userOrdersRef = FirebaseDatabase.getInstance()
                .getReference("UsersOrders")
                .child(userId);
        userOrdersRef.push().setValue(order);

        // 2️⃣ Save in general Orders node
        DatabaseReference allOrdersRef = FirebaseDatabase.getInstance()
                .getReference("Orders");
        allOrdersRef.push().setValue(order)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "ऑर्डर यशस्वी!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PlaceSeedsOrderActivity.this, UserDashboardActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "त्रुटी आली!", Toast.LENGTH_SHORT).show());
    }
}
