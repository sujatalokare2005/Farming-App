package com.example.farmingapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import java.util.ArrayList;

public class UserCartActivity extends AppCompatActivity {

    RecyclerView rvCartProducts;
    CartAdapterRecycler adapter;
    ArrayList<CartModel> cartList;
    DatabaseReference cartRef;
    String userId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);

        rvCartProducts = findViewById(R.id.rvCartProducts);
        rvCartProducts.setLayoutManager(new LinearLayoutManager(this));

        cartList = new ArrayList<>();
        adapter = new CartAdapterRecycler(this, cartList);
        rvCartProducts.setAdapter(adapter);

        SharedPreferences prefs = getSharedPreferences("FarmingAppPrefs", MODE_PRIVATE);
        userId = prefs.getString("loggedInUserId", null);

        if (userId == null) {
            Toast.makeText(this, "वापरकर्ता लॉग इन केलेला नाही", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        cartRef = FirebaseDatabase.getInstance().getReference("UsersCart").child(userId);
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                cartList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    CartModel item = data.getValue(CartModel.class);
                    if (item != null) {
                        item.id = data.getKey(); // store firebase push ID
                        cartList.add(item);
                    }
                }
                adapter.notifyDataSetChanged();

                if (cartList.isEmpty()) {
                    Toast.makeText(UserCartActivity.this, "कार्ट रिकामे आहे", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(UserCartActivity.this, "डेटा लोड करताना त्रुटी आली", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
