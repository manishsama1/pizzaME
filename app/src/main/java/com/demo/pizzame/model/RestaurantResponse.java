package com.demo.pizzame.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Manish on 7/12/2018.
 */

public class RestaurantResponse {
    @SerializedName("query")
    private Query query;

    public Query getQuery() {
        return query;
    }

    public class Query {
        @SerializedName("results")
        private Results results;

        public Results getResults() {
            return results;
        }

        public class Results {
            @SerializedName("Result")
            private List<Restaurant> restaurantList;

            public List<Restaurant> getRestaurantList() {
                return restaurantList;
            }
        }
    }
}
