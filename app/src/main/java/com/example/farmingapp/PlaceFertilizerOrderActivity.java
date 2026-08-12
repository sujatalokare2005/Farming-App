package com.example.farmingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlaceFertilizerOrderActivity extends AppCompatActivity {

    TextView tvProductName;
    EditText etCustomerName, etCustomerMobile, etCustomerAddress, etQuantity;
    CheckBox cbCashOnDelivery;
    Button btnPlaceOrder;

    DatabaseReference ordersRef;
    String productName;

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

        // Firebase path for fertilizer orders
        ordersRef = FirebaseDatabase.getInstance().getReference("Orders");

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

        String orderId = ordersRef.push().getKey();
        OrderModel order = new OrderModel(
                name,
                mobile,
                address,
                productName,
                quantity,
                cod ? "Cash on Delivery" : "Not selected"
        );

        ordersRef.child(orderId).setValue(order)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "खत ऑर्डर यशस्वी!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PlaceFertilizerOrderActivity.this, ViewFertilizerActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "त्रुटी आली!", Toast.LENGTH_SHORT).show());
    }
}
