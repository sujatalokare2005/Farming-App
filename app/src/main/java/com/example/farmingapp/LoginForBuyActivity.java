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

public class LoginForBuyActivity extends AppCompatActivity {

    EditText edtName, edtPassword;
    Button btnLogin;
    DatabaseReference usersRef;
    String productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_for_buy);

        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        usersRef = FirebaseDatabase.getInstance().getReference("Users");
        productName = getIntent().getStringExtra("productName");

        btnLogin.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if(name.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Enter Name & Password", Toast.LENGTH_SHORT).show();
                return;
            }

            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    boolean found = false;
                    String userId = null;

                    for(DataSnapshot data : snapshot.getChildren()){
                        User user = data.getValue(User.class);
                        if(user != null && user.name.equals(name) && user.password.equals(password)){
                            found = true;
                            userId = data.getKey();
                            break;
                        }
                    }

                    if(found){
                        // Save logged-in user in SharedPreferences
                        getSharedPreferences("FarmingAppPrefs", MODE_PRIVATE)
                                .edit()
                                .putString("loggedInUserId", userId)
                                .putString("loggedInUserName", name)
                                .apply();

                        Toast.makeText(LoginForBuyActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        // Go to PlaceSeedsOrderActivity with product
                        Intent intent = new Intent(LoginForBuyActivity.this, PlaceSeedsOrderActivity.class);
                        intent.putExtra("productName", productName);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(LoginForBuyActivity.this, "Invalid Name or Password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {}
            });
        });
    }
}
