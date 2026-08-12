package com.example.farmingapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import java.util.ArrayList;

public class ViewFertilizerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FertilizerAdapter adapter;
    private ArrayList<FirebaseHelper> fertilizerList;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fertilizer);

        recyclerView = findViewById(R.id.fertilizerRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fertilizerList = new ArrayList<>();
        adapter = new FertilizerAdapter(this, fertilizerList);
        recyclerView.setAdapter(adapter);

        loadFertilizersFromFirebase();
    }

    private void loadFertilizersFromFirebase() {
        reference = FirebaseDatabase.getInstance().getReference("Fertilizers");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fertilizerList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    FirebaseHelper fertilizer = data.getValue(FirebaseHelper.class);
                    if (fertilizer != null) {
                        fertilizerList.add(fertilizer);
                    }
                }

                adapter.notifyDataSetChanged();

                if (fertilizerList.isEmpty()) {
                    Toast.makeText(ViewFertilizerActivity.this, "डेटाबेसमध्ये कोणतेही खत नाही", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewFertilizerActivity.this, "त्रुटी: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
