package com.example.farmingapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSeedsActivity extends AppCompatActivity {

    private static final String TAG = "AddSeedsActivity";
    ImageView imgSeed;
    EditText etName, etQuantity, etDescription, etPrice;
    Button btnSave;
    DatabaseReference reference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seeds);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        etName = findViewById(R.id.etProductName);
        etQuantity = findViewById(R.id.etAvailableQuantity);
        etDescription = findViewById(R.id.etProductDescription);
        etPrice = findViewById(R.id.etSeedPrice);
        btnSave = findViewById(R.id.btnSaveSeed);
    }

    private void setupClickListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProductToFirebase();
            }
        });
    }

    private void saveProductToFirebase() {
        // Get input values
        String name = etName.getText().toString().trim();
        String quantity = etQuantity.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String price = etPrice.getText().toString().trim();

        // Validate inputs
        if (name.isEmpty()) {
            etName.setError("Product name is required");
            etName.requestFocus();
            return;
        }

        if (quantity.isEmpty()) {
            etQuantity.setError("Quantity is required");
            etQuantity.requestFocus();
            return;
        }

        if (price.isEmpty()) {
            etPrice.setError("Price is required");
            etPrice.requestFocus();
            return;
        }

        // Show progress dialog
        showProgressDialog();

        try {
            // Initialize Firebase Database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            reference = database.getReference("Seeds");

            // Create unique key for each product
            String productId = reference.push().getKey();

            if (productId == null) {
                hideProgressDialog();
                Toast.makeText(this, "Error: Could not generate product ID", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create product object - NOTE: Parameter order matches constructor
            FirebaseHelper product = new FirebaseHelper(name, price, description, quantity);

            Log.d(TAG, "Saving product: " + product.toString());
            Log.d(TAG, "Product ID: " + productId);

            // Save to Firebase
            reference.child(productId).setValue(product)
                    .addOnSuccessListener(aVoid -> {
                        hideProgressDialog();
                        Log.d(TAG, "Product saved successfully!");
                        Toast.makeText(AddSeedsActivity.this, "Product saved successfully!", Toast.LENGTH_SHORT).show();
                        clearForm();
                    })
                    .addOnFailureListener(e -> {
                        hideProgressDialog();
                        Log.e(TAG, "Failed to save product: " + e.getMessage());
                        Toast.makeText(AddSeedsActivity.this, "Failed to save product: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });

        } catch (Exception e) {
            hideProgressDialog();
            Log.e(TAG, "Exception: " + e.getMessage());
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving product to database...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void clearForm() {
        etName.setText("");
        etQuantity.setText("");
        etDescription.setText("");
        etPrice.setText("");
        etName.requestFocus();
    }
}