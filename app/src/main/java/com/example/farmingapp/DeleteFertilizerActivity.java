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

public class DeleteFertilizerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FertilizerAdapter adapter;
    ArrayList<FirebaseHelper> fertilizerList;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_products_list);

        recyclerView = findViewById(R.id.recyclerViewDeleteProducts);
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
                Toast.makeText(DeleteFertilizerActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class FertilizerAdapter extends RecyclerView.Adapter<FertilizerAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(DeleteFertilizerActivity.this)
                    .inflate(R.layout.item_delete_product, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            FirebaseHelper fert = fertilizerList.get(position);
            holder.tvName.setText(fert.getStrProductName());

            holder.btnDelete.setOnClickListener(v -> new AlertDialog.Builder(DeleteFertilizerActivity.this)
                    .setTitle("Delete Fertilizer")
                    .setMessage("Are you sure you want to delete " + fert.getStrProductName() + "?")
                    .setPositiveButton("Yes", (dialog, which) -> reference.child(fert.getId()).removeValue()
                            .addOnSuccessListener(aVoid ->
                                    Toast.makeText(DeleteFertilizerActivity.this, "Fertilizer deleted", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e ->
                                    Toast.makeText(DeleteFertilizerActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()))
                    .setNegativeButton("No", null)
                    .show());
        }

        @Override
        public int getItemCount() {
            return fertilizerList.size();
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
