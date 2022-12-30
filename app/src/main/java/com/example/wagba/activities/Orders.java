package com.example.wagba.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.wagba.R;
import com.example.wagba.adapters.OrderAdapter;
import com.example.wagba.adapters.RestaurantAdapter;
import com.example.wagba.databinding.ActivityHomepageBinding;
import com.example.wagba.databinding.ActivityOrdersBinding;
import com.example.wagba.models.OrderModel;
import com.example.wagba.models.RestaurantModel;
import com.example.wagba.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Orders extends AppCompatActivity {

    ArrayList<OrderModel> orders;
    private ActivityOrdersBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference restaurantsRef = database.getReference("orders");
    DatabaseReference userRef = database.getReference("users");
    RestaurantAdapter restaurantAdapter;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    UserModel currentUser;
    OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrdersBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        orders = new ArrayList<OrderModel>();
        orderAdapter = new OrderAdapter(orders);
        binding.ordersRecyclerView.setAdapter(restaurantAdapter);
        binding.ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        restaurantsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // restaurants = (ArrayList<RestaurantModel>) dataSnapshot.getValue();
                orders.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    orders.add(postSnapshot.getValue(OrderModel.class));
                }
                for(int i=0;i<orders.size();i++){
                    if(!Objects.equals(uid, orders.get(i).getClientUid())){
                        orders.remove(i);
                        i--;
                    }
                }
                orderAdapter = new OrderAdapter(orders);
                binding.ordersRecyclerView.setAdapter(orderAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("RT DB", "Failed to read value.", error.toException());
            }
        });

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