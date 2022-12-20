package com.example.wagba.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.activities.RestaurantMenu;
import com.example.wagba.models.RestaurantModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    ArrayList<RestaurantModel> restaurants;

    public RestaurantAdapter(ArrayList<RestaurantModel> users) {
        this.restaurants = users;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, ratingTextView, timeTextView;
        ImageView image;
        FrameLayout item;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.res_name);
            ratingTextView = view.findViewById(R.id.res_rating);
            timeTextView = view.findViewById(R.id.res_time);
            item = view.findViewById(R.id.restaurant_item);
            image = view.findViewById(R.id.res_image_view);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View usersView = inflater.inflate(R.layout.restaurant_item, viewGroup, false);
        return new ViewHolder(usersView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.nameTextView.setText(restaurants.get(position).getName());
        viewHolder.ratingTextView.setText(restaurants.get(position).getRating());
        viewHolder.timeTextView.setText(restaurants.get(position).getTime());
        viewHolder.item.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), RestaurantMenu.class);
                        Bundle extra = new Bundle();
                        extra.putSerializable("menu", restaurants.get(viewHolder.getAdapterPosition()).getMenu());
                        intent.putExtra("extra", extra);
                        intent.putExtra("name", restaurants.get(viewHolder.getAdapterPosition()).getName());
                        intent.putExtra("rating", restaurants.get(viewHolder.getAdapterPosition()).getRating());
                        intent.putExtra("time", restaurants.get(viewHolder.getAdapterPosition()).getTime());
                        intent.putExtra("image", restaurants.get(viewHolder.getAdapterPosition()).getImageUrl());
                        v.getContext().startActivity(intent);
                    }
                }
        );
        Picasso.get().load(restaurants.get(position).getImageUrl()).into(viewHolder.image);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return restaurants.size();
    }
}
