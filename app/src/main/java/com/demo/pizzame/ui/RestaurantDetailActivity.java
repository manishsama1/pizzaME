package com.demo.pizzame.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.pizzame.R;
import com.demo.pizzame.model.Restaurant;

import java.util.Locale;

/**
 * Created by Manish on 7/12/2018.
 */

public class RestaurantDetailActivity extends AppCompatActivity{
    private static final int CALL_PHONE = 1;
    private TextView restaurantName;
    private TextView address;
    private TextView phone;
    private TextView distance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        final Restaurant restaurant = bundle.getParcelable(Restaurant.KEY);
        restaurantName = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        distance = findViewById(R.id.distance);
        address = findViewById(R.id.address);

        restaurantName.setText(restaurant.getTitle());
        address.setText(restaurant.getAddress() + RestaurantAdapter.COMMA + RestaurantAdapter.EMPTY_STRING + restaurant.getCity() + RestaurantAdapter.COMMA + RestaurantAdapter.EMPTY_STRING + restaurant.getState());
        phone.setText(restaurant.getPhone());
        distance.setText(restaurant.getDistance() + RestaurantAdapter.EMPTY_STRING + RestaurantAdapter.MILES);

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4192?q=" + Uri.encode(address.getText().toString()));
//                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(restaurant.getLatitude()), Double.parseDouble(restaurant.getLongitude()));
                Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                startActivity(intent);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });
    }

    public void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Check permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PHONE);
        } else {
            makeCall();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CALL_PHONE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();
            } else {
                Toast.makeText(this, R.string.call_permission_required, Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void makeCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone.getText().toString()));
        startActivity(callIntent);
    }
}
