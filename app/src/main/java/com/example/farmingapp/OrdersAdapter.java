package com.example.farmingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private Context context;
    private ArrayList<OrderModel> ordersList;

    public OrdersAdapter(Context context, ArrayList<OrderModel> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item_layout, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel order = ordersList.get(position);

        // Marathi labels + null safety
        holder.tvProduct.setText("उत्पादन: " + safe(order.getProductName()));
        holder.tvQuantity.setText("प्रमाण: " + safe(order.getQuantity()));
        holder.tvPayment.setText("पेमेंट प्रकार: " + safe(order.getPaymentMode()));
        holder.tvAddress.setText("पत्ता: " + safe(order.getAddress()));
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvProduct, tvQuantity, tvPayment, tvAddress;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProduct = itemView.findViewById(R.id.tvProduct);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPayment = itemView.findViewById(R.id.tvPayment);
            tvAddress = itemView.findViewById(R.id.tvAddress);
        }
    }

    // Handle null values gracefully
    private String safe(String text) {
        return (text == null || text.trim().isEmpty()) ? "उपलब्ध नाही" : text;
    }
}
