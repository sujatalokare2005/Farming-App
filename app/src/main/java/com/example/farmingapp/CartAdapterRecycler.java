package com.example.farmingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartAdapterRecycler extends RecyclerView.Adapter<CartAdapterRecycler.CartViewHolder> {

    private Context context;
    private ArrayList<CartModel> cartList;
    private String userId;

    public CartAdapterRecycler(Context context, ArrayList<CartModel> cartList) {
        this.context = context;
        this.cartList = cartList;
        this.userId = context.getSharedPreferences("FarmingAppPrefs", Context.MODE_PRIVATE)
                .getString("loggedInUserId", null);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartModel item = cartList.get(position);

        holder.tvProductName.setText("उत्पादनाचे नाव: " + item.productName);
        holder.tvProductPrice.setText("किंमत: ₹" + item.productPrice);
        holder.tvProductQuantity.setText("प्रमाण: " + item.productQuantity);
        holder.tvProductDescription.setText("वर्णन: " + item.productDescription);

        // Keep Buy Now functionality
        holder.btnBuyNow.setOnClickListener(v -> {
            Intent intent = new Intent(context, LoginForBuyActivity.class);
            intent.putExtra("productName", item.productName);
            intent.putExtra("productPrice", item.productPrice);
            intent.putExtra("productQuantity", item.productQuantity);
            intent.putExtra("productDescription", item.productDescription);
            context.startActivity(intent);
        });

        // Delete button
        holder.btnDelete.setOnClickListener(v -> {
            if (userId == null) {
                Toast.makeText(context, "वापरकर्ता लॉग इन केलेला नाही", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("UsersCart")
                    .child(userId)
                    .child(item.id);

            ref.removeValue()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(context, "उत्पादन कार्टमधून काढले गेले", Toast.LENGTH_SHORT).show();
                        cartList.remove(position);
                        notifyItemRemoved(position);
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(context, "काढताना त्रुटी आली", Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductPrice, tvProductQuantity, tvProductDescription;
        Button btnBuyNow, btnDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductQuantity = itemView.findViewById(R.id.tvProductQuantity);
            tvProductDescription = itemView.findViewById(R.id.tvProductDescription);
            btnBuyNow = itemView.findViewById(R.id.btnBuyNow);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
