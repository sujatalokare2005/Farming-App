package com.example.farmingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import java.util.ArrayList;

public class DeleteSeedsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SeedsAdapter adapter;
    ArrayList<FirebaseHelper> seedsList;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_products_list); // reuse same layout

        recyclerView = findViewById(R.id.recyclerViewDeleteProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        seedsList = new ArrayList<>();
        adapter = new SeedsAdapter();
        recyclerView.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference("Seeds");
        loadSeeds();
    }

    private void loadSeeds() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                seedsList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    FirebaseHelper seed = ds.getValue(FirebaseHelper.class);
                    if (seed != null) {
                        seed.setId(ds.getKey());
                        seedsList.add(seed);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(DeleteSeedsActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class SeedsAdapter extends RecyclerView.Adapter<SeedsAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(DeleteSeedsActivity.this)
                    .inflate(R.layout.item_delete_product, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            FirebaseHelper seed = seedsList.get(position);
            holder.tvName.setText(seed.getStrProductName());

            holder.btnDelete.setOnClickListener(v -> {
                // Confirmation dialog
                new AlertDialog.Builder(DeleteSeedsActivity.this)
                        .setTitle("Delete Seed")
                        .setMessage("Are you sure you want to delete " + seed.getStrProductName() + "?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            reference.child(seed.getId()).removeValue()
                                    .addOnSuccessListener(aVoid ->
                                            Toast.makeText(DeleteSeedsActivity.this, "Seed deleted", Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e ->
                                            Toast.makeText(DeleteSeedsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        })
                        .setNegativeButton("No", null)
                        .show();
            });
        }

        @Override
        public int getItemCount() {
            return seedsList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;
            Button btnDelete;

            ViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvProductName);
                btnDelete = itemView.findViewById(R.id.btnDeleteProduct);
            }
        }
    }
}
