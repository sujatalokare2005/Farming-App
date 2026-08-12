package com.example.farmingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAllOrdersActivity extends AppCompatActivity {

    private ListView ordersListView;
    private ArrayAdapter<String> adapter;
    private DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_orders);

        ordersListView = findViewById(R.id.ordersListView);
        adapter = new ArrayAdapter<>(this, R.layout.order_list_item, R.id.orderItemText, new ArrayList<>());
        ordersListView.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference("Orders");
        loadOrdersInMarathi();
    }

    private void loadOrdersInMarathi() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                adapter.clear();

                if (!snapshot.exists()) {
                    Toast.makeText(ViewAllOrdersActivity.this, "कोणतीही ऑर्डर्स सापडल्या नाहीत", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    OrderModel order = orderSnapshot.getValue(OrderModel.class);
                    if (order != null) {
                        String orderDetails =
                                "नाव: " + safe(order.getCustomerName()) + "\n" +
                                        "मोबाइल क्रमांक: " + safe(order.getMobile()) + "\n" +
                                        "पत्ता: " + safe(order.getAddress()) + "\n" +
                                        "उत्पादनाचे नाव: " + safe(order.getProductName()) + "\n" +
                                        "प्रमाण: " + safe(order.getQuantity()) + "\n" +
                                        "पेमेंट प्रकार: " + safe(order.getPaymentMode());
                        adapter.add(orderDetails);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ViewAllOrdersActivity.this, "त्रुटी: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // सुरक्षित null हाताळणी
    private String safe(String text) {
        return text == null ? "उपलब्ध नाही" : text;
    }
}
