package com.example.farmingapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserOrdersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    OrdersAdapter adapter;
    ArrayList<OrderModel> ordersList;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_orders);

        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ordersList = new ArrayList<>();
        adapter = new OrdersAdapter(this, ordersList);
        recyclerView.setAdapter(adapter);

        // Get logged-in user ID
        userId = getSharedPreferences("FarmingAppPrefs", MODE_PRIVATE)
                .getString("loggedInUserId", null);

        if(userId == null){
            Toast.makeText(this, "No logged-in user found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadUserOrders();
    }

    private void loadUserOrders() {
        DatabaseReference userOrdersRef = FirebaseDatabase.getInstance()
                .getReference("UsersOrders")
                .child(userId);

        userOrdersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ordersList.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    OrderModel order = data.getValue(OrderModel.class);
                    if(order != null){
                        ordersList.add(order);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserOrdersActivity.this, "Failed to load orders", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
