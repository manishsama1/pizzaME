package com.demo.pizzame.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.pizzame.R;
import com.demo.pizzame.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manish on 7/13/2018.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    public static final String EMPTY_STRING = " ";
    public static final String COMMA = ",";
    public static final String MILES = "miles";
    private List<Restaurant> restaurantList;

    public RestaurantAdapter() {
        restaurantList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_row_view, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        RestaurantViewHolder viewHolder = holder;
        Restaurant restaurant = restaurantList.get(position);

        viewHolder.name.setText(restaurant.getTitle());
        viewHolder.address.setText(restaurant.getAddress() + COMMA + EMPTY_STRING + restaurant.getCity() + COMMA + EMPTY_STRING + restaurant.getState());
        viewHolder.phone.setText(restaurant.getPhone());
        viewHolder.distance.setText(restaurant.getDistance() + EMPTY_STRING + MILES);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView address;
        private TextView phone;
        private TextView distance;

        public RestaurantViewHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), RestaurantDetailActivity.class);
                    intent.putExtra(Restaurant.KEY, restaurantList.get(getAdapterPosition()));
                    itemView.getContext().startActivity(intent);
                }
            });
            name = (TextView) itemView.findViewById(R.id.name);
            address = (TextView) itemView.findViewById(R.id.address);
            phone = (TextView) itemView.findViewById(R.id.phone);
            distance = (TextView) itemView.findViewById(R.id.distance);
        }
    }

    public void updateList(final List<Restaurant> restaurantList) {
        if (this.restaurantList == null || this.restaurantList.isEmpty()) {
            this.restaurantList = restaurantList;
            notifyItemRangeInserted(0, restaurantList.size());
        } else {
//       todo add logic for pagination
        }
    }
}
