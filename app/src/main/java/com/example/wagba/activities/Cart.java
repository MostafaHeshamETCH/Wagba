package com.example.wagba.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wagba.adapters.CartAdapter;
import com.example.wagba.databinding.ActivityCartBinding;
import com.example.wagba.models.CartModel;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    ArrayList<CartModel> cartItems;
    private ActivityCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        binding.checkoutButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent k = new Intent(Cart.this, DeliveryDetails.class);
                            startActivity(k);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        cartItems = new ArrayList<CartModel>();

        cartItems.add(new CartModel("Share Box", "165.0", 2));
        cartItems.add(new CartModel("Share Box", "165.0", 2));
        cartItems.add(new CartModel("Share Box", "165.0", 2));

        CartAdapter restaurantAdapter = new CartAdapter(cartItems);

        binding.cartRecyclerView.setAdapter(restaurantAdapter);
        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}