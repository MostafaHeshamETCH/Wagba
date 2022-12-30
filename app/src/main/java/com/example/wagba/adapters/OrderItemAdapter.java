package com.example.wagba.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.models.OrderDetailsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {
    ArrayList<OrderDetailsModel> ordersHistory;

    public OrderItemAdapter(ArrayList<OrderDetailsModel> ordersHistory) {
        this.ordersHistory = ordersHistory;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView;
        ImageView image;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.order_item_name);
            priceTextView = view.findViewById(R.id.order_item_price);
            image = view.findViewById(R.id.image);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View usersView = inflater.inflate(R.layout.order_details_item, viewGroup, false);
        return new ViewHolder(usersView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.nameTextView.setText(ordersHistory.get(position).getName());
        viewHolder.priceTextView.setText(ordersHistory.get(position).getPrice());
        Picasso.get().load(ordersHistory.get(position).getImageUrl()).into(viewHolder.image);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ordersHistory.size();
    }
}
