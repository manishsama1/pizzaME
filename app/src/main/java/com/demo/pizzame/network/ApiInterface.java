package com.demo.pizzame.network;

import com.demo.pizzame.model.RestaurantResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Manish on 7/12/2018.
 */

public interface ApiInterface {
    @GET("https://query.yahooapis.com/v1/public/yql")
    Call<RestaurantResponse> getRestaurantList(@Query("q") String query, @Query("format") String json);
}
