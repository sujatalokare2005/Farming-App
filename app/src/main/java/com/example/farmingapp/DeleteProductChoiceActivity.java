package com.example.farmingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteProductChoiceActivity extends AppCompatActivity {

    Button btnSeeds, btnFertilizer, btnInsecticide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product_choice);

        btnSeeds = findViewById(R.id.btnDeleteSeeds);
        btnFertilizer = findViewById(R.id.btnDeleteFertilizers);
        btnInsecticide = findViewById(R.id.btnDeleteInsecticide);

        btnSeeds.setOnClickListener(v -> {
            // Go to Add Seeds Activity (you can create later)
            Intent intent = new Intent(DeleteProductChoiceActivity.this, DeleteSeedsActivity.class);
            startActivity(intent);
        });

        btnFertilizer.setOnClickListener(v -> {
            // Go to Add Fertilizer Activity (you can create later)
            Intent intent = new Intent(DeleteProductChoiceActivity.this, DeleteFertilizerActivity.class);
            startActivity(intent);
        });

        btnInsecticide.setOnClickListener(v -> {
            // Go to Add Insecticide Activity (you can create later)
            Intent intent = new Intent(DeleteProductChoiceActivity.this, DeletePesticidesActivity.class);
            startActivity(intent);
        });
    }
}
