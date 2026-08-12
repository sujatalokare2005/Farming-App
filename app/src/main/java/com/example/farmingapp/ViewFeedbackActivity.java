package com.example.farmingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewFeedbackActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FeedbackAdapter feedbackAdapter;
    private List<FeedbackModel> feedbackList;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_admin);

        recyclerView = findViewById(R.id.recyclerViewFeedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        feedbackList = new ArrayList<>();
        feedbackAdapter = new FeedbackAdapter(feedbackList);
        recyclerView.setAdapter(feedbackAdapter);

        // Reference to "Feedback" node in your Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Feedback");

        fetchFeedbackFromFirebase();
    }

    private void fetchFeedbackFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                feedbackList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FeedbackModel feedback = dataSnapshot.getValue(FeedbackModel.class);
                    if (feedback != null) {
                        feedbackList.add(feedback);
                    }
                }
                feedbackAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // You can log or show a Toast if data fetch fails
            }
        });
    }
}
