package com.demo.pizzame;

import android.arch.lifecycle.ViewModelProviders;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;


import com.demo.pizzame.ViewModel.RestaurantViewModel;
import com.demo.pizzame.ui.MainActivity;
import com.demo.pizzame.ui.RestaurantAdapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Created by Manish on 7/15/2018.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Mock
    private RestaurantViewModel restaurantViewModel;

    @Mock
    private RestaurantAdapter restaurantAdapter;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureViewsArePresnt() throws Exception {
        MainActivity activity = rule.getActivity();
        RecyclerView recyclerView = activity.findViewById(R.id.restaurant_list);
        assertThat(recyclerView, instanceOf(RecyclerView.class));

        recyclerView = (RecyclerView) recyclerView;
        String query = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20local.search%20where%20zip%3D%2778759%27%20and%20query%3D%27pizza%27&format=json&diagnostics=true&callback=";
        restaurantViewModel = ViewModelProviders.of(activity, new RestaurantViewModel.Factory(activity.getApplication(), query))
                .get(RestaurantViewModel.class);
        restaurantAdapter = (RestaurantAdapter) ((RecyclerView) recyclerView).getAdapter();
        int count = restaurantAdapter.getItemCount();
        assertThat(count, equalTo(0));
    }
}

