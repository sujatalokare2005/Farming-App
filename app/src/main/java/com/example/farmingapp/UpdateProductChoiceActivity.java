package com.example.farmingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateProductChoiceActivity extends AppCompatActivity {

    Button btnSeeds, btnFertilizer, btnInsecticide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product_choice);

        btnSeeds = findViewById(R.id.btnUpdateSeeds);
        btnFertilizer = findViewById(R.id.btnUpdateFertilizer);
        btnInsecticide = findViewById(R.id.btnUpdateInsecticide);

        btnSeeds.setOnClickListener(v -> {
            // Go to Add Seeds Activity (you can create later)
            Intent intent = new Intent(UpdateProductChoiceActivity.this, UpdateSeedsActivity.class);
            startActivity(intent);
        });

        btnFertilizer.setOnClickListener(v -> {
            // Go to Add Fertilizer Activity (you can create later)
      Intent intent = new Intent(UpdateProductChoiceActivity.this, UpdateFertilizerActivity.class);
          startActivity(intent);
        });

        btnInsecticide.setOnClickListener(v -> {
            // Go to Add Insecticide Activity (you can create later)
         Intent intent = new Intent(UpdateProductChoiceActivity.this, UpdatePesticidesActivity.class);
          startActivity(intent);
        });
    }
}
