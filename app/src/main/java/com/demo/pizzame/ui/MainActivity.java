package com.demo.pizzame.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.demo.pizzame.R;
import com.demo.pizzame.ViewModel.RestaurantViewModel;
import com.demo.pizzame.model.Restaurant;
import com.demo.pizzame.model.RestaurantResponse;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 1;
    public static final String TAG = "RestaurantListFragment";
    private ProgressBar progressBar;
    private RecyclerView restaurantListView;
    private RestaurantAdapter restaurantAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        restaurantAdapter = new RestaurantAdapter();
        restaurantListView = (RecyclerView) findViewById(R.id.restaurant_list);
        progressBar = findViewById(R.id.main_progress_bar);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        linearLayoutManager = new LinearLayoutManager(this);
        restaurantListView.addItemDecoration(itemDecoration);
        restaurantListView.setLayoutManager(linearLayoutManager);
        restaurantListView.setAdapter(restaurantAdapter);
        checkPermission(this);
    }

    private void findCurrentLocation() {
        Geocoder geocoder;
        String bestProvider;
        List<Address> addressList = null;
        String zipCode = "";

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        bestProvider = lm.getBestProvider(criteria, false);
        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(bestProvider);

        if (location == null) {
            Toast.makeText(this, "Location Not found", Toast.LENGTH_LONG).show();
        } else {
            geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                zipCode = addressList.get(0).getPostalCode();
                String query = "select * from local.search where zip='" + Integer.parseInt(zipCode) + "' and query='pizza'";
//                String finalQuery = URLEncoder.encode(query);
                //TODO This query might require encoding in lower android versions. I didnt spent much time fixing/testing on lower android versions
                RestaurantViewModel.Factory factory = new RestaurantViewModel.Factory(getApplication(), query);
                final RestaurantViewModel viewModel = ViewModelProviders.of(this, factory).get(RestaurantViewModel.class);
                observeViewModel(viewModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void observeViewModel(RestaurantViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getObservableData().observe(this, new Observer<RestaurantResponse>() {
            @Override
            public void onChanged(@Nullable RestaurantResponse restaurantResponse) {
                if (restaurantResponse == null) {
                    Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                }
                toggleProgressBar(false);
                List<Restaurant> restaurantList = new ArrayList<>();
                restaurantList.addAll(restaurantResponse.getQuery().getResults().getRestaurantList());
                if (!restaurantList.isEmpty()) {
                    restaurantAdapter.updateList(restaurantList);
                }
            }
        });
    }

    private void toggleProgressBar(boolean visible) {
        progressBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void checkPermission(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check permission
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        } else {
            findCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                findCurrentLocation();
            } else {
                Toast.makeText(this, R.string.permission_required, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
