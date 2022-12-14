package com.example.wagba.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.wagba.R;
import com.example.wagba.adapters.OrderAdapter;
import com.example.wagba.adapters.RestaurantAdapter;
import com.example.wagba.databinding.ActivityHomepageBinding;
import com.example.wagba.databinding.ActivityOrdersBinding;
import com.example.wagba.models.OrderModel;
import com.example.wagba.models.RestaurantModel;

import java.util.ArrayList;

public class Orders extends AppCompatActivity {

    ArrayList<OrderModel> orders;
    private ActivityOrdersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrdersBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        orders = new ArrayList<OrderModel>();

        orders.add(new OrderModel("McDonald's", "", "165.0", "9/12/2022", "2:30 PM"));
        orders.add(new OrderModel("McDonald's", "", "215.0", "8/12/2022", "1:30 PM"));
        orders.add(new OrderModel("McDonald's", "", "305.0", "7/12/2022", "12:00 AM"));

        OrderAdapter restaurantAdapter = new OrderAdapter(orders);

        binding.ordersRecyclerView.setAdapter(restaurantAdapter);
        binding.ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }
}