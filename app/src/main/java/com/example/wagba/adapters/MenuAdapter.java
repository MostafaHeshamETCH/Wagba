package com.example.wagba.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.models.CartModel;
import com.example.wagba.models.MenuModel;
import com.example.wagba.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>  {
    ArrayList<MenuModel> menu;

    public MenuAdapter(ArrayList<MenuModel> menu){
        this.menu = menu;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, descTextView, priceTextView;
        ImageView image, addToCart;
        LinearLayout item;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.menu_item_name);
            descTextView = view.findViewById(R.id.menu_item_desc);
            priceTextView = view.findViewById(R.id.menu_item_price);
            image = view.findViewById(R.id.menu_item_image);
            item = view.findViewById(R.id.meal_item);
            addToCart = view.findViewById(R.id.menu_item_add_btn);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View usersView = inflater.inflate(R.layout.meal_item, viewGroup, false);
        return new ViewHolder(usersView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.nameTextView.setText(menu.get(position).getName());
        viewHolder.descTextView.setText(menu.get(position).getDesc());
        viewHolder.priceTextView.setText(menu.get(position).getPrice());
        Picasso.get().load(menu.get(position).getImageUrl()).into(viewHolder.image);
        viewHolder.addToCart.setOnClickListener(view -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userRef = database.getReference("users");
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            UserModel currentUser;

            DatabaseReference cartRef = userRef.child(uid).child("cart").push();
            cartRef.setValue(new CartModel(menu.get(position).getName(), menu.get(position).getPrice(), 1, menu.get(position).getImageUrl()));
            Toast.makeText(view.getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return menu.size();
    }
}
