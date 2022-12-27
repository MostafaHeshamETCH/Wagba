package com.example.wagba.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.wagba.adapters.OrderItemAdapter;
import com.example.wagba.databinding.ActivityOrderStatusBinding;
import com.example.wagba.models.OrderDetailsModel;

import java.util.ArrayList;

public class OrderStatus extends AppCompatActivity {

    ArrayList<OrderDetailsModel> orders;
    private ActivityOrderStatusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderStatusBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        orders = new ArrayList<OrderDetailsModel>();

        orders.add(new OrderDetailsModel("McDonald's", "165.0" , 1));

        OrderItemAdapter orderItemAdapter = new OrderItemAdapter(orders);

        binding.orderDetailsRecyclerView.setAdapter(orderItemAdapter);
        binding.orderDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}