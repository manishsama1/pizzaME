package com.demo.pizzame.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.demo.pizzame.model.RestaurantResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manish on 7/13/2018.
 */

public class RestaurantRepository {
    private static RestaurantRepository restaurantRepository;
    private RestaurantRepository() {
    }

    public synchronized static RestaurantRepository getInstance() {
        if (restaurantRepository == null) {
            if (restaurantRepository == null) {
                restaurantRepository = new RestaurantRepository();
            }
        }
        return restaurantRepository;
    }

    public LiveData<RestaurantResponse> getRestaurantList(String query) {
        ApiInterface apiService =
                RetrofitClient.getClient().create(ApiInterface.class);
        final MutableLiveData<RestaurantResponse> data = new MutableLiveData<>();
        Call<RestaurantResponse> call = apiService.getRestaurantList(query, "json");
        call.enqueue(new Callback<RestaurantResponse>() {
            @Override
            public void onResponse(Call<RestaurantResponse>call, Response<RestaurantResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RestaurantResponse>call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
