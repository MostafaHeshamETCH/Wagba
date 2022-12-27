package com.example.wagba.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.wagba.R;
import com.example.wagba.adapters.CartAdapter;
import com.example.wagba.databinding.ActivityDeliveryDetailsBinding;
import com.example.wagba.models.CartModel;
import com.example.wagba.models.OrderDetailsModel;
import com.example.wagba.models.OrderModel;
import com.example.wagba.models.UserModel;
import com.example.wagba.models.room.LocalUser;
import com.example.wagba.view_models.LocalUserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DeliveryDetails extends AppCompatActivity {

    private ActivityDeliveryDetailsBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("users");
    DatabaseReference ordersRef = database.getReference("orders");
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    UserModel currentUser;
    String whichGate = "Gate 4", deliveryTimes = "12:00 PM", uniYear = "No data entered yet";
    float totalPrice = 0;
    private LocalUserViewModel localUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveryDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        localUserViewModel = new ViewModelProvider(this).get(LocalUserViewModel.class);

        localUserViewModel.getAllLocalUsers().observe(this, new Observer<List<LocalUser>>() {
            @Override
            public void onChanged(List<LocalUser> localUsers) {
                if(!localUsers.isEmpty()){
                    uniYear = localUsers.get(0).getUniYear();
                } else {
                    uniYear = "No data entered yet";
                }
            }
        });

        binding.confirmOrderButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Date c = Calendar.getInstance().getTime();
                            String formattedDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(c);
                            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(c);
                            // TODO: Do Order
                            ordersRef.push().setValue(new OrderModel(
                                currentUser.getName(),
                                currentUser.getCart().get(0).getImageUrl(),
                                    String.valueOf(totalPrice),
                                    formattedDate,
                                    currentTime,
                                    currentUser.getCart(),
                                    uid,
                                    currentUser.getName(),
                                    uniYear,
                                    whichGate,
                                    deliveryTimes,
                                    "Just Ordered"
                            ));
                            // TODO: Clear Cart
                            currentUser.setCart(new ArrayList<CartModel>());
                            userRef.child(uid).setValue(currentUser);
                            Intent k = new Intent(DeliveryDetails.this, OrderedSuccessfully.class);
                            startActivity(k);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

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

                for(int i=0;i<currentUser.getCart().size();i++){
                    String tempPrice = currentUser.getCart().get(i).getPrice();
                    try {
                        totalPrice += Float.parseFloat(tempPrice) * currentUser.getCart().get(i).getNumberOfItems();
                    } catch (Exception e){
                        System.out.println("Error " + e.getMessage());
                    }
                }
                binding.totalPrice.setText(String.format("%.2f", totalPrice));
                binding.totalFinalPrice.setText(String.format("%.2f", totalPrice + 25.0));
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

        binding.gate3Item.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.gate3Item.setBackground(ContextCompat.getDrawable(DeliveryDetails.this, R.drawable.selected_item_bg));
                        binding.gate4Item.setBackground(ContextCompat.getDrawable(DeliveryDetails.this, R.drawable.unselected_item_bg));
                        whichGate = "Gate 3";
                    }
                }
        );

        binding.gate4Item.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.gate3Item.setBackground(ContextCompat.getDrawable(DeliveryDetails.this, R.drawable.unselected_item_bg));
                        binding.gate4Item.setBackground(ContextCompat.getDrawable(DeliveryDetails.this, R.drawable.selected_item_bg));
                        whichGate = "Gate 4";
                    }
                }
        );

        binding.item1200.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.item1200.setBackground(ContextCompat.getDrawable(DeliveryDetails.this, R.drawable.selected_item_bg));
                        binding.item300.setBackground(ContextCompat.getDrawable(DeliveryDetails.this, R.drawable.unselected_item_bg));
                        deliveryTimes = "12:00 PM";
                    }
                }
        );

        binding.item300.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.item1200.setBackground(ContextCompat.getDrawable(DeliveryDetails.this, R.drawable.unselected_item_bg));
                        binding.item300.setBackground(ContextCompat.getDrawable(DeliveryDetails.this, R.drawable.selected_item_bg));
                        deliveryTimes = "3:00 PM";
                    }
                }
        );
    }
}