package com.example.farmingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PesticidesAdapter extends RecyclerView.Adapter<PesticidesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<FirebaseHelper> pesticidesList;

    public PesticidesAdapter(Context context, ArrayList<FirebaseHelper> pesticidesList) {
        this.context = context;
        this.pesticidesList = pesticidesList;
    }

    @NonNull
    @Override
    public PesticidesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pesticide, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PesticidesAdapter.ViewHolder holder, int position) {
        FirebaseHelper pesticide = pesticidesList.get(position);

        holder.tvName.setText("नाव: " + pesticide.getStrProductName());
        holder.tvPrice.setText("किंमत: ₹" + pesticide.getStrProductPrice());
        holder.tvQuantity.setText("प्रमाण: " + pesticide.getStrProductQuantity());
        holder.tvDescription.setText("वर्णन: " + pesticide.getStrProductDescription());

        holder.btnBuyNow.setOnClickListener(v -> {
            // Go to LoginForBuyActivity and pass product name
            Intent intent = new Intent(context, LoginForBuyActivity.class);
            intent.putExtra("productName", pesticide.getStrProductName());
            context.startActivity(intent);
        });

        holder.btnAddToCart.setOnClickListener(v -> {
            Intent intent = new Intent(context, CartLoginActivity.class);
            intent.putExtra("productName", pesticide.getStrProductName());
            intent.putExtra("productPrice", pesticide.getStrProductPrice());
            intent.putExtra("productQuantity", pesticide.getStrProductQuantity());
            intent.putExtra("productDescription", pesticide.getStrProductDescription());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return pesticidesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQuantity, tvDescription;
        Button btnBuyNow, btnAddToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvPesticideName);
            tvPrice = itemView.findViewById(R.id.tvPesticidePrice);
            tvQuantity = itemView.findViewById(R.id.tvPesticideQuantity);
            tvDescription = itemView.findViewById(R.id.tvPesticideDescription);
            btnBuyNow = itemView.findViewById(R.id.btnBuyNow);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
