package com.example.farmingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeletePesticidesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PesticidesAdapter adapter;
    ArrayList<FirebaseHelper> pesticidesList;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_products_list);

        recyclerView = findViewById(R.id.recyclerViewDeleteProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pesticidesList = new ArrayList<>();
        adapter = new PesticidesAdapter();
        recyclerView.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference("Pesticides");
        loadPesticides();
    }

    private void loadPesticides() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                pesticidesList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    FirebaseHelper pest = ds.getValue(FirebaseHelper.class);
                    if (pest != null) {
                        pest.setId(ds.getKey());
                        pesticidesList.add(pest);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(DeletePesticidesActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class PesticidesAdapter extends RecyclerView.Adapter<PesticidesAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(DeletePesticidesActivity.this)
                    .inflate(R.layout.item_delete_product, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            FirebaseHelper pest = pesticidesList.get(position);
            holder.tvName.setText(pest.getStrProductName());

            holder.btnDelete.setOnClickListener(v -> new AlertDialog.Builder(DeletePesticidesActivity.this)
                    .setTitle("Delete Pesticide")
                    .setMessage("Are you sure you want to delete " + pest.getStrProductName() + "?")
                    .setPositiveButton("Yes", (dialog, which) -> reference.child(pest.getId()).removeValue()
                            .addOnSuccessListener(aVoid ->
                                    Toast.makeText(DeletePesticidesActivity.this, "Pesticide deleted", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e ->
                                    Toast.makeText(DeletePesticidesActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()))
                    .setNegativeButton("No", null)
                    .show());
        }

        @Override
        public int getItemCount() {
            return pesticidesList.size();
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
