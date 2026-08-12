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

public class UpdateSeedsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SeedsAdapter adapter;
    ArrayList<FirebaseHelper> seedsList;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_products_list);

        recyclerView = findViewById(R.id.recyclerViewUpdateSeeds);
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
                Toast.makeText(UpdateSeedsActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class SeedsAdapter extends RecyclerView.Adapter<SeedsAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            android.view.View view = LayoutInflater.from(UpdateSeedsActivity.this)
                    .inflate(R.layout.item_update_seed, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            FirebaseHelper seed = seedsList.get(position);
            holder.tvName.setText(seed.getStrProductName());

            holder.btnUpdate.setOnClickListener(v -> showUpdateDialog(seed));
        }

        @Override
        public int getItemCount() { return seedsList.size(); }

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

    private void showUpdateDialog(FirebaseHelper seed) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        android.view.View view = LayoutInflater.from(this).inflate(R.layout.dialog_update_seed, null);
        builder.setView(view);

        EditText etName = view.findViewById(R.id.etProductName);
        EditText etPrice = view.findViewById(R.id.etProductPrice);
        EditText etQuantity = view.findViewById(R.id.etProductQuantity);
        EditText etDescription = view.findViewById(R.id.etProductDescription);
        Button btnSave = view.findViewById(R.id.btnSave);

        etName.setText(seed.getStrProductName());
        etPrice.setText(seed.getStrProductPrice());
        etQuantity.setText(seed.getStrProductQuantity());
        etDescription.setText(seed.getStrProductDescription());

        AlertDialog dialog = builder.create();
        dialog.show();

        btnSave.setOnClickListener(v -> {
            FirebaseHelper updatedSeed = new FirebaseHelper(
                    etName.getText().toString().trim(),
                    etPrice.getText().toString().trim(),
                    etDescription.getText().toString().trim(),
                    etQuantity.getText().toString().trim()
            );

            reference.child(seed.getId()).setValue(updatedSeed)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(UpdateSeedsActivity.this, "Seed Updated!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(UpdateSeedsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }
}
