package com.example.wagba.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.models.CartModel;
import com.example.wagba.models.OrderModel;
import com.example.wagba.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ArrayList<CartModel> cartItems;

    public CartAdapter(ArrayList<CartModel> cartItems) {
        this.cartItems = cartItems;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView, numberOfItemsTextView;
        ImageView image, addBtn, minusBtn, deleteBtn;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.cart_item_name);
            priceTextView = view.findViewById(R.id.cart_item_price);
            numberOfItemsTextView = view.findViewById(R.id.cart_number_of_items);
            image = view.findViewById(androidx.appcompat.R.id.image);
            addBtn = view.findViewById(R.id.add_btn);
            minusBtn = view.findViewById(R.id.minus_btn);
            deleteBtn = view.findViewById(R.id.delete_btn);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View usersView = inflater.inflate(R.layout.cart_item, viewGroup, false);
        return new ViewHolder(usersView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.nameTextView.setText(cartItems.get(position).getName());
        viewHolder.priceTextView.setText(cartItems.get(position).getPrice());
        viewHolder.numberOfItemsTextView.setText(String.valueOf(cartItems.get(position).getNumberOfItems()));
        Picasso.get().load(cartItems.get(position).getImageUrl()).into(viewHolder.image);
        viewHolder.addBtn.setOnClickListener(view -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userRef = database.getReference("users");
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            userRef.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        ArrayList<CartModel> cart = new ArrayList<>();
                        for (DataSnapshot ds : task.getResult().child("cart").getChildren()) {
                            CartModel item = ds.getValue(CartModel.class);
                            cart.add(item);
                        }
                        ArrayList<OrderModel> history = new ArrayList<>();
                        for (DataSnapshot ds : task.getResult().child("ordersHistory").getChildren()) {
                            OrderModel item = ds.getValue(OrderModel.class);
                            history.add(item);
                        }
                        UserModel currentUser = new UserModel(
                                task.getResult().child("email").getValue(String.class),
                                task.getResult().child("name").getValue(String.class),
                                cart,
                                history
                        );
                        currentUser.getCart().get(viewHolder.getAdapterPosition()).setNumberOfItems(currentUser.getCart().get(viewHolder.getAdapterPosition()).getNumberOfItems() + 1);
                        userRef.child(uid).setValue(currentUser);
                    }
                }
            });
        });
        viewHolder.minusBtn.setOnClickListener(view -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userRef = database.getReference("users");
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            userRef.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        ArrayList<CartModel> cart = new ArrayList<>();
                        for (DataSnapshot ds : task.getResult().child("cart").getChildren()) {
                            CartModel item = ds.getValue(CartModel.class);
                            cart.add(item);
                        }
                        ArrayList<OrderModel> history = new ArrayList<>();
                        for (DataSnapshot ds : task.getResult().child("ordersHistory").getChildren()) {
                            OrderModel item = ds.getValue(OrderModel.class);
                            history.add(item);
                        }
                        UserModel currentUser = new UserModel(
                                task.getResult().child("email").getValue(String.class),
                                task.getResult().child("name").getValue(String.class),
                                cart,
                                history
                        );
                        if(currentUser.getCart().get(viewHolder.getAdapterPosition()).getNumberOfItems() > 1){
                            currentUser.getCart().get(viewHolder.getAdapterPosition()).setNumberOfItems(currentUser.getCart().get(viewHolder.getAdapterPosition()).getNumberOfItems() - 1);
                        }
                        userRef.child(uid).setValue(currentUser);
                    }
                }
            });
        });
        viewHolder.deleteBtn.setOnClickListener(view -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userRef = database.getReference("users");
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            userRef.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        ArrayList<CartModel> cart = new ArrayList<>();
                        for (DataSnapshot ds : task.getResult().child("cart").getChildren()) {
                            CartModel item = ds.getValue(CartModel.class);
                            cart.add(item);
                        }
                        ArrayList<OrderModel> history = new ArrayList<>();
                        for (DataSnapshot ds : task.getResult().child("ordersHistory").getChildren()) {
                            OrderModel item = ds.getValue(OrderModel.class);
                            history.add(item);
                        }
                        UserModel currentUser = new UserModel(
                                task.getResult().child("email").getValue(String.class),
                                task.getResult().child("name").getValue(String.class),
                                cart,
                                history
                        );
                        currentUser.getCart().remove(viewHolder.getAdapterPosition());
                        userRef.child(uid).setValue(currentUser);
                    }
                }
            });
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}
