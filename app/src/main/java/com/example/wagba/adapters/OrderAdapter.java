package com.example.wagba.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.activities.OrderStatus;
import com.example.wagba.models.OrderModel;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    ArrayList<OrderModel> ordersHistory;

    public OrderAdapter(ArrayList<OrderModel> ordersHistory) {
        this.ordersHistory = ordersHistory;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView, dateTextView, timeTextView;
        LinearLayout item;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.order_res_name);
            priceTextView = view.findViewById(R.id.order_price);
            timeTextView = view.findViewById(R.id.order_time);
            dateTextView = view.findViewById(R.id.order_date);
            item = view.findViewById(R.id.item_frame);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View usersView = inflater.inflate(R.layout.order_item, viewGroup, false);
        return new ViewHolder(usersView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.nameTextView.setText(ordersHistory.get(position).getName());
        viewHolder.priceTextView.setText(ordersHistory.get(position).getPrice());
        viewHolder.timeTextView.setText(ordersHistory.get(position).getTime());
        viewHolder.dateTextView.setText(ordersHistory.get(position).getDate());
        viewHolder.item.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.getContext().startActivity(new Intent(v.getContext(), OrderStatus.class));
                    }
                }
        );

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ordersHistory.size();
    }
}
