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

public class FertilizerAdapter extends RecyclerView.Adapter<FertilizerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<FirebaseHelper> fertilizerList;

    public FertilizerAdapter(Context context, ArrayList<FirebaseHelper> fertilizerList) {
        this.context = context;
        this.fertilizerList = fertilizerList;
    }

    @NonNull
    @Override
    public FertilizerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fertilizer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FertilizerAdapter.ViewHolder holder, int position) {
        FirebaseHelper fertilizer = fertilizerList.get(position);

        holder.tvName.setText("नाव: " + fertilizer.getStrProductName());
        holder.tvPrice.setText("किंमत: ₹" + fertilizer.getStrProductPrice());
        holder.tvQuantity.setText("प्रमाण: " + fertilizer.getStrProductQuantity());
        holder.tvDescription.setText("वर्णन: " + fertilizer.getStrProductDescription());

        holder.btnBuyNow.setOnClickListener(v -> {
            // Go to LoginForBuyActivity and pass product name
            Intent intent = new Intent(context, LoginForBuyActivity.class);
            intent.putExtra("productName", fertilizer.getStrProductName());
            context.startActivity(intent);
        });

        holder.btnAddToCart.setOnClickListener(v -> {
            Intent intent = new Intent(context, CartLoginActivity.class);
            intent.putExtra("productName", fertilizer.getStrProductName());
            intent.putExtra("productPrice", fertilizer.getStrProductPrice());
            intent.putExtra("productQuantity", fertilizer.getStrProductQuantity());
            intent.putExtra("productDescription", fertilizer.getStrProductDescription());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return fertilizerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fertilizerImage;
        TextView tvName, tvPrice, tvQuantity, tvDescription;
        Button btnBuyNow, btnAddToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fertilizerImage = itemView.findViewById(R.id.fertilizerImage);
            tvName = itemView.findViewById(R.id.tvFertilizerName);
            tvPrice = itemView.findViewById(R.id.tvFertilizerPrice);
            tvQuantity = itemView.findViewById(R.id.tvFertilizerQuantity);
            tvDescription = itemView.findViewById(R.id.tvFertilizerDescription);
            btnBuyNow = itemView.findViewById(R.id.btnBuyNow);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
