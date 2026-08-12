package com.example.farmingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SeedsAdapter extends RecyclerView.Adapter<SeedsAdapter.SeedViewHolder> {

    private Context context;
    private ArrayList<FirebaseHelper> seedsList;

    public SeedsAdapter(Context context, ArrayList<FirebaseHelper> seedsList) {
        this.context = context;
        this.seedsList = seedsList;
    }

    @NonNull
    @Override
    public SeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.seed_item_layout, parent, false);
        return new SeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeedViewHolder holder, int position) {
        FirebaseHelper seed = seedsList.get(position);

        holder.tvName.setText("नाव: " + seed.getStrProductName());
        holder.tvPrice.setText("किंमत: ₹" + seed.getStrProductPrice());
        holder.tvQuantity.setText("प्रमाण: " + seed.getStrProductQuantity());
        holder.tvDescription.setText("वर्णन: " + seed.getStrProductDescription());


        holder.seedImage.setImageResource(R.drawable.ic_seed_placeholder);

        holder.btnBuyNow.setOnClickListener(v -> {
            // Go to LoginForBuyActivity and pass product name
            Intent intent = new Intent(context, LoginForBuyActivity.class);
            intent.putExtra("productName", seed.getStrProductName());
            context.startActivity(intent);
        });

        holder.btnAddToCart.setOnClickListener(v -> {
            Intent intent = new Intent(context, CartLoginActivity.class);
            intent.putExtra("productName", seed.getStrProductName());
            intent.putExtra("productPrice", seed.getStrProductPrice());
            intent.putExtra("productQuantity", seed.getStrProductQuantity());
            intent.putExtra("productDescription", seed.getStrProductDescription());
            context.startActivity(intent);
        });
    }
        @Override
    public int getItemCount() {
        return seedsList.size();
    }

    static class SeedViewHolder extends RecyclerView.ViewHolder {
        ImageView seedImage;
        TextView tvName, tvPrice, tvQuantity, tvDescription;
        Button btnBuyNow, btnAddToCart;

        public SeedViewHolder(@NonNull View itemView) {
            super(itemView);
            seedImage = itemView.findViewById(R.id.seedImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            btnBuyNow = itemView.findViewById(R.id.btnBuyNow);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
