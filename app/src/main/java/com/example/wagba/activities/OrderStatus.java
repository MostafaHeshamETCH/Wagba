package com.example.wagba.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.wagba.adapters.MenuAdapter;
import com.example.wagba.adapters.OrderItemAdapter;
import com.example.wagba.databinding.ActivityOrderStatusBinding;
import com.example.wagba.models.CartModel;
import com.example.wagba.models.MenuModel;
import com.example.wagba.models.OrderDetailsModel;
import com.squareup.picasso.Picasso;

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

        Bundle extra = getIntent().getBundleExtra("extra");
        ArrayList<CartModel> orderDetails = (ArrayList<CartModel>) extra.getSerializable("orderDetails");
        orders = new ArrayList<OrderDetailsModel>();

        for (int i = 0; i < orderDetails.size(); i++) {
            orders.add(new OrderDetailsModel(String.valueOf(orderDetails.get(i).getNumberOfItems()) + "x " + orderDetails.get(i).getName(), orderDetails.get(i).getPrice(), orderDetails.get(i).getNumberOfItems(), orderDetails.get(i).getImageUrl()));
        }

        OrderItemAdapter orderItemAdapter = new OrderItemAdapter(orders);
        binding.orderDetailsRecyclerView.setAdapter(orderItemAdapter);
        binding.orderDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.date.setText(getIntent().getStringExtra("date"));
        binding.price.setText(getIntent().getStringExtra("price"));
        binding.status.setText(getIntent().getStringExtra("status"));

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