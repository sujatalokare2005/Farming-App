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

public class UpdateFertilizerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FertilizerAdapter adapter;
    ArrayList<FirebaseHelper> fertilizerList;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_products_list); // reuse the same layout

        recyclerView = findViewById(R.id.recyclerViewUpdateSeeds);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fertilizerList = new ArrayList<>();
        adapter = new FertilizerAdapter();
        recyclerView.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference("Fertilizers");
        loadFertilizers();
    }

    private void loadFertilizers() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                fertilizerList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    FirebaseHelper fert = ds.getValue(FirebaseHelper.class);
                    if (fert != null) {
                        fert.setId(ds.getKey());
                        fertilizerList.add(fert);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(UpdateFertilizerActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class FertilizerAdapter extends RecyclerView.Adapter<FertilizerAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            android.view.View view = LayoutInflater.from(UpdateFertilizerActivity.this)
                    .inflate(R.layout.item_update_seed, parent, false); // reuse same item layout
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            FirebaseHelper fert = fertilizerList.get(position);
            holder.tvName.setText(fert.getStrProductName());

            holder.btnUpdate.setOnClickListener(v -> showUpdateDialog(fert));
        }

        @Override
        public int getItemCount() { return fertilizerList.size(); }

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

    private void showUpdateDialog(FirebaseHelper fert) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        android.view.View view = LayoutInflater.from(this).inflate(R.layout.dialog_update_seed, null);
        builder.setView(view);

        EditText etName = view.findViewById(R.id.etProductName);
        EditText etPrice = view.findViewById(R.id.etProductPrice);
        EditText etQuantity = view.findViewById(R.id.etProductQuantity);
        EditText etDescription = view.findViewById(R.id.etProductDescription);
        Button btnSave = view.findViewById(R.id.btnSave);

        etName.setText(fert.getStrProductName());
        etPrice.setText(fert.getStrProductPrice());
        etQuantity.setText(fert.getStrProductQuantity());
        etDescription.setText(fert.getStrProductDescription());

        AlertDialog dialog = builder.create();
        dialog.show();

        btnSave.setOnClickListener(v -> {
            FirebaseHelper updatedFert = new FirebaseHelper(
                    etName.getText().toString().trim(),
                    etPrice.getText().toString().trim(),
                    etDescription.getText().toString().trim(),
                    etQuantity.getText().toString().trim()
            );

            reference.child(fert.getId()).setValue(updatedFert)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(UpdateFertilizerActivity.this, "Fertilizer Updated!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(UpdateFertilizerActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }
}
