package com.demo.pizzame.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Manish on 7/13/2018.
 */

public class Restaurant implements Parcelable {
    public static final String KEY = "key";
    @SerializedName("id")
    private String id;

    @SerializedName("Title")
    private String title;

    @SerializedName("Address")
    private String address;

    @SerializedName("City")
    private String city;

    @SerializedName("State")
    private String state;

    @SerializedName("Phone")
    private String phone;

    @SerializedName("Latitude")
    private String latitude;

    @SerializedName("Longitude")
    private String longitude;

    @SerializedName("Distance")
    private String distance;

    protected Restaurant(Parcel in) {
        id = in.readString();
        title = in.readString();
        address = in.readString();
        city = in.readString();
        state = in.readString();
        phone = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        distance = in.readString();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPhone() {
        return phone;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getDistance() {
        return distance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(address);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(phone);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(distance);
    }
}
