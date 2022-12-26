package com.example.wagba.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.wagba.adapters.MenuAdapter;
import com.example.wagba.adapters.RestaurantAdapter;
import com.example.wagba.databinding.ActivityRestaurantMenuBinding;
import com.example.wagba.models.CartModel;
import com.example.wagba.models.MenuModel;
import com.example.wagba.models.OrderModel;
import com.example.wagba.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantMenu extends AppCompatActivity {

    private ActivityRestaurantMenuBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("users");
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    UserModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantMenuBinding.inflate(getLayoutInflater());
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
                for (DataSnapshot ds : dataSnapshot.child("cart").getChildren()) {
                    OrderModel item = ds.getValue(OrderModel.class);
                    history.add(item);
                }
                currentUser = new UserModel(
                        dataSnapshot.child("email").getValue(String.class),
                        dataSnapshot.child("name").getValue(String.class),
                        cart,
                        history
                );
                assert currentUser != null;
                if(currentUser.getCart() != null){
                    binding.cartShortcutNumber.setText(currentUser.getCart().size() + " items");
                } else {
                    binding.cartShortcutNumber.setText("0 items");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("RT DB", "Failed to read value.", error.toException());
            }
        });

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
        Picasso.get().load(getIntent().getStringExtra("image")).into(binding.resImageView);


        binding.menuRecyclerView.setAdapter(menuAdapter);
        binding.menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // adds bottom padding to recycler view to clear space fot cart
        float offsetPx = 300;
        Homepage.BottomOffsetDecoration bottomOffsetDecoration = new Homepage.BottomOffsetDecoration((int) offsetPx);
        binding.menuRecyclerView.addItemDecoration(bottomOffsetDecoration);
    }
}