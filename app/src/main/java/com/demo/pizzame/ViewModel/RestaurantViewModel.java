package com.demo.pizzame.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.demo.pizzame.model.RestaurantResponse;
import com.demo.pizzame.network.RestaurantRepository;

/**
 * Created by Manish on 7/13/2018.
 */

public class RestaurantViewModel extends AndroidViewModel {
    private final LiveData<RestaurantResponse> restaurantLiveData;
    private String query;

    public RestaurantViewModel(@NonNull Application application, String query) {
        super(application);
        restaurantLiveData = RestaurantRepository.getInstance().getRestaurantList(query);
    }

    public LiveData<RestaurantResponse> getObservableData() {
        return restaurantLiveData;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        private String query;

        public Factory(@NonNull Application application, String query) {
            this.application = application;
            this.query = query;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new RestaurantViewModel(application, query);
        }
    }
}
