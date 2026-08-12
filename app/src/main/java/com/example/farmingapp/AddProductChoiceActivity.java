package com.example.farmingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AddProductChoiceActivity extends AppCompatActivity {

    Button btnSeeds, btnFertilizer, btnInsecticide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_choice);

        btnSeeds = findViewById(R.id.btnSeeds);
        btnFertilizer = findViewById(R.id.btnFertilizer);
        btnInsecticide = findViewById(R.id.btnInsecticide);

        btnSeeds.setOnClickListener(v -> {
            // Go to Add Seeds Activity (you can create later)
            Intent intent = new Intent(AddProductChoiceActivity.this, AddSeedsActivity.class);
           startActivity(intent);
        });

        btnFertilizer.setOnClickListener(v -> {
            // Go to Add Fertilizer Activity (you can create later)
           Intent intent = new Intent(AddProductChoiceActivity.this, AddFertilizerActivity.class);
          startActivity(intent);
        });

        btnInsecticide.setOnClickListener(v -> {
            // Go to Add Insecticide Activity (you can create later)
          Intent intent = new Intent(AddProductChoiceActivity.this, AddPesticidesActivity.class);
           startActivity(intent);
        });
    }
}
