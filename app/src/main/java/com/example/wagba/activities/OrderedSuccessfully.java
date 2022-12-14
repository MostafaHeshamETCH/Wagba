package com.example.wagba.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wagba.databinding.ActivityOrderedSuccessfullyBinding;

public class OrderedSuccessfully extends AppCompatActivity {

    private ActivityOrderedSuccessfullyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderedSuccessfullyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.backToHomepageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      Intent i = new Intent(OrderedSuccessfully.this, Homepage.class);
                      i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                      startActivity(i);
                    }
                }
        );
    }
}