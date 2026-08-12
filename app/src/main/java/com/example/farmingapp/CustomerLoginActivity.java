package com.example.farmingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerLoginActivity extends AppCompatActivity {

    private EditText etName, etAddress, etMobile;
    private Button btnLogin;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etMobile = findViewById(R.id.etMobile);
        btnLogin = findViewById(R.id.btnLogin);

        databaseReference = FirebaseDatabase.getInstance().getReference("CustomerDetails");

        btnLogin.setOnClickListener(v -> saveDetails());
    }

    private void saveDetails() {
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty() || mobile.isEmpty()) {
            Toast.makeText(this, "कृपया सर्व तपशील भरा", Toast.LENGTH_SHORT).show();
            return;
        }

        // Use mobile number as unique ID
        String userId = mobile;

        // Check if user already exists
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                CustomerModel model = new CustomerModel(name, address, mobile);
                databaseReference.child(userId).setValue(model)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(CustomerLoginActivity.this, "लॉगिन यशस्वी!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CustomerLoginActivity.this, CustomerHomeActivity.class));
                            finish();
                        })
                        .addOnFailureListener(e -> Toast.makeText(CustomerLoginActivity.this, "त्रुटी आली!", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CustomerLoginActivity.this, "डेटाबेस त्रुटी!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
