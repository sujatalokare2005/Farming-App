package com.example.farmingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserFeedback extends AppCompatActivity {

    private EditText etCustomerName, etFeedback;
    private Button btnSubmit;

    private DatabaseReference feedbackRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_user); // Your feedback form XML

        etCustomerName = findViewById(R.id.etCustomerName);
        etFeedback = findViewById(R.id.etFeedback);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Firebase reference
        feedbackRef = FirebaseDatabase.getInstance().getReference("Feedback");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etCustomerName.getText().toString().trim();
                String feedbackMessage = etFeedback.getText().toString().trim();

                if (name.isEmpty() || feedbackMessage.isEmpty()) {
                    Toast.makeText(UserFeedback.this, "कृपया नाव आणि फीडबॅक भरा", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Push feedback to Firebase
                String feedbackId = feedbackRef.push().getKey(); // unique id
                FeedbackModel feedback = new FeedbackModel(name, feedbackMessage);
                feedbackRef.child(feedbackId).setValue(feedback)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(UserFeedback.this, "फीडबॅक सबमिट यशस्वी", Toast.LENGTH_SHORT).show();
                            etCustomerName.setText("");
                            etFeedback.setText("");
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(UserFeedback.this, "फीडबॅक सबमिट करण्यात त्रुटी", Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}
