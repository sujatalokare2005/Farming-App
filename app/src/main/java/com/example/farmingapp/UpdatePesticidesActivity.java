package com.example.farmingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import java.util.ArrayList;

public class UpdatePesticidesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PesticidesAdapter adapter;
    ArrayList<FirebaseHelper> pesticidesList;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_products_list); // reuse same layout

        recyclerView = findViewById(R.id.recyclerViewUpdateSeeds);
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
                Toast.makeText(UpdatePesticidesActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class PesticidesAdapter extends RecyclerView.Adapter<PesticidesAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            android.view.View view = LayoutInflater.from(UpdatePesticidesActivity.this)
                    .inflate(R.layout.item_update_seed, parent, false); // reuse item layout
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            FirebaseHelper pest = pesticidesList.get(position);
            holder.tvName.setText(pest.getStrProductName());

            holder.btnUpdate.setOnClickListener(v -> showUpdateDialog(pest));
        }

        @Override
        public int getItemCount() { return pesticidesList.size(); }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;
            Button btnUpdate;
            ViewHolder(android.view.View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvSeedName);
                btnUpdate = itemView.findViewById(R.id.btnUpdateSeed);
            }
        }
    }

    private void showUpdateDialog(FirebaseHelper pest) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        android.view.View view = LayoutInflater.from(this).inflate(R.layout.dialog_update_seed, null);
        builder.setView(view);

        EditText etName = view.findViewById(R.id.etProductName);
        EditText etPrice = view.findViewById(R.id.etProductPrice);
        EditText etQuantity = view.findViewById(R.id.etProductQuantity);
        EditText etDescription = view.findViewById(R.id.etProductDescription);
        Button btnSave = view.findViewById(R.id.btnSave);

        etName.setText(pest.getStrProductName());
        etPrice.setText(pest.getStrProductPrice());
        etQuantity.setText(pest.getStrProductQuantity());
        etDescription.setText(pest.getStrProductDescription());

        AlertDialog dialog = builder.create();
        dialog.show();

        btnSave.setOnClickListener(v -> {
            FirebaseHelper updatedPest = new FirebaseHelper(
                    etName.getText().toString().trim(),
                    etPrice.getText().toString().trim(),
                    etDescription.getText().toString().trim(),
                    etQuantity.getText().toString().trim()
            );

            reference.child(pest.getId()).setValue(updatedPest)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(UpdatePesticidesActivity.this, "Pesticide Updated!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(UpdatePesticidesActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }
}
