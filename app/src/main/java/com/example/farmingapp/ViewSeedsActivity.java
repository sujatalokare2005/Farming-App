package com.example.farmingapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import java.util.ArrayList;

public class ViewSeedsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SeedsAdapter adapter;
    private ArrayList<FirebaseHelper> seedsList;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_seeds);

        recyclerView = findViewById(R.id.seedsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        seedsList = new ArrayList<>();
        adapter = new SeedsAdapter(this, seedsList);
        recyclerView.setAdapter(adapter);

        loadSeedsFromFirebase();
    }

    private void loadSeedsFromFirebase() {
        reference = FirebaseDatabase.getInstance().getReference("Seeds");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                seedsList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    FirebaseHelper seed = data.getValue(FirebaseHelper.class);
                    if (seed != null) {
                        seedsList.add(seed);
                    }
                }

                adapter.notifyDataSetChanged();

                if (seedsList.isEmpty()) {
                    Toast.makeText(ViewSeedsActivity.this, "डेटाबेसमध्ये कोणतीही बियाणे नाहीत", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewSeedsActivity.this, "त्रुटी: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
