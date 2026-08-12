package com.example.farmingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText edtName, edtAddress, edtMobile, edtPassword;
    Button btnSignUp;
    DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtMobile = findViewById(R.id.edtMobile);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        usersRef = FirebaseDatabase.getInstance().getReference("Users");

        btnSignUp.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();
            String mobile = edtMobile.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if(name.isEmpty() || address.isEmpty() || mobile.isEmpty() || password.isEmpty()){
                Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save user details in Firebase Realtime Database
            String userId = usersRef.push().getKey(); // auto-generate unique key
            User user = new User(name, address, mobile, password);
            usersRef.child(userId).setValue(user)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                            // Go back to ProfileActivity
                            startActivity(new Intent(SignUpActivity.this, ProfileActivity.class));
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Failed to save details", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
