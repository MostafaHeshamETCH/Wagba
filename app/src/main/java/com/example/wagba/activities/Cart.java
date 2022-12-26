package com.example.wagba.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.wagba.adapters.CartAdapter;
import com.example.wagba.adapters.RestaurantAdapter;
import com.example.wagba.databinding.ActivityCartBinding;
import com.example.wagba.models.CartModel;
import com.example.wagba.models.OrderModel;
import com.example.wagba.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    ArrayList<CartModel> cartItems;
    private ActivityCartBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference restaurantsRef = database.getReference("restaurants");
    DatabaseReference userRef = database.getReference("users");
    CartAdapter cartAdapter;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    UserModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        userRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ArrayList<CartModel> cart = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.child("cart").getChildren()) {
                    CartModel item = ds.getValue(CartModel.class);
                    cart.add(item);
                }
                ArrayList<OrderModel> history = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.child("ordersHistory").getChildren()) {
                    OrderModel item = ds.getValue(OrderModel.class);
                    history.add(item);
                }
                currentUser = new UserModel(
                        dataSnapshot.child("email").getValue(String.class),
                        dataSnapshot.child("name").getValue(String.class),
                        cart,
                        history
                );
                cartAdapter = new CartAdapter(currentUser.getCart());
                binding.cartRecyclerView.setAdapter(cartAdapter);
                binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(Cart.this));
                float totalPrice = 0;
                for(int i=0;i<currentUser.getCart().size();i++){
                    String tempPrice = currentUser.getCart().get(i).getPrice();
                    try {
                        totalPrice += Float.parseFloat(tempPrice) * currentUser.getCart().get(i).getNumberOfItems();
                    } catch (Exception e){
                        System.out.println("Error " + e.getMessage());
                    }
                }
                binding.totalPrice.setText(String.valueOf(totalPrice));
                binding.totalFinalPrice.setText(String.valueOf(totalPrice + 25.0));
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
        cartAdapter = new CartAdapter(cartItems);
        binding.cartRecyclerView.setAdapter(cartAdapter);
        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}