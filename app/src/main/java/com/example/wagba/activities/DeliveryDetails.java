package com.example.wagba.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wagba.R;
import com.example.wagba.databinding.ActivityDeliveryDetailsBinding;

public class DeliveryDetails extends AppCompatActivity {

    private ActivityDeliveryDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveryDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.confirmOrderButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent k = new Intent(DeliveryDetails.this, OrderedSuccessfully.class);
                            startActivity(k);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        binding.backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        binding.gate3Item.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.gate3Item.setBackground(ContextCompat.getDrawable(DeliveryDetails.this, R.drawable.selected_item_bg));
                        binding.gate4Item.setBackground(ContextCompat.getDrawable(DeliveryDetails.this, R.drawable.unselected_item_bg));
                    }
                }
        );

        binding.gate4Item.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.gate3Item.setBackground(ContextCompat.getDrawable(DeliveryDetails.this, R.drawable.unselected_item_bg));
                        binding.gate4Item.setBackground(ContextCompat.getDrawable(DeliveryDetails.this, R.drawable.selected_item_bg));
                    }
                }
        );

        binding.item1200.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.item1200.setBackground(ContextCompat.getDrawable(DeliveryDetails.this, R.drawable.selected_item_bg));
                        binding.item300.setBackground(ContextCompat.getDrawable(DeliveryDetails.this, R.drawable.unselected_item_bg));
                    }
                }
        );

        binding.item300.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.item1200.setBackground(ContextCompat.getDrawable(DeliveryDetails.this, R.drawable.unselected_item_bg));
                        binding.item300.setBackground(ContextCompat.getDrawable(DeliveryDetails.this, R.drawable.selected_item_bg));
                    }
                }
        );
    }
}