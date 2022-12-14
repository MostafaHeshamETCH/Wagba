package com.example.wagba.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wagba.adapters.MenuAdapter;
import com.example.wagba.databinding.ActivityRestaurantMenuBinding;
import com.example.wagba.models.MenuModel;

import java.util.ArrayList;

public class RestaurantMenu extends AppCompatActivity {

    private ActivityRestaurantMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.cartShortcut.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent k = new Intent(RestaurantMenu.this, Cart.class);
                            startActivity(k);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        Bundle extra = getIntent().getBundleExtra("extra");
        ArrayList<MenuModel> menu = (ArrayList<MenuModel>) extra.getSerializable("menu");
        MenuAdapter menuAdapter = new MenuAdapter(menu);

        binding.name.setText(getIntent().getStringExtra("name"));
        binding.resRating.setText(getIntent().getStringExtra("rating"));
        binding.resTime.setText(getIntent().getStringExtra("time"));

        binding.menuRecyclerView.setAdapter(menuAdapter);
        binding.menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // adds bottom padding to recycler view to clear space fot cart
        float offsetPx = 300;
        Homepage.BottomOffsetDecoration bottomOffsetDecoration = new Homepage.BottomOffsetDecoration((int) offsetPx);
        binding.menuRecyclerView.addItemDecoration(bottomOffsetDecoration);
    }
}