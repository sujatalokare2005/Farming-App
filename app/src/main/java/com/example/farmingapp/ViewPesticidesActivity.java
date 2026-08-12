package com.example.farmingapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import java.util.ArrayList;

public class ViewPesticidesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PesticidesAdapter adapter;
    private ArrayList<FirebaseHelper> pesticidesList;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pesticides); // Make sure you create this layout file

        recyclerView = findViewById(R.id.pesticidesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pesticidesList = new ArrayList<>();
        adapter = new PesticidesAdapter(this, pesticidesList);
        recyclerView.setAdapter(adapter);

        loadPesticidesFromFirebase();
    }

    private void loadPesticidesFromFirebase() {
        reference = FirebaseDatabase.getInstance().getReference("Pesticides");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pesticidesList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    FirebaseHelper pesticide = data.getValue(FirebaseHelper.class);
                    if (pesticide != null) {
                        pesticidesList.add(pesticide);
                    }
                }

                adapter.notifyDataSetChanged();

                if (pesticidesList.isEmpty()) {
                    Toast.makeText(ViewPesticidesActivity.this, "डेटाबेसमध्ये कोणतेही कीटकनाशके नाहीत", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewPesticidesActivity.this, "त्रुटी: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
