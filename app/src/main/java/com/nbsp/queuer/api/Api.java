package com.nbsp.queuer.api;

import com.google.gson.GsonBuilder;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;

public class Api {
    private static Api instance;

    private static final String BASE_URL = "api.com";

    private Api() {
        initRestAdapter();
        initRequests();
    }

    public static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }
        return instance;
    }

    private RestAdapter mRestAdapter;

    private ApiInterface mApiInterface;

    private void initRestAdapter() {
        GsonBuilder builder = new GsonBuilder();

        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(builder.create()))
                .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog("RETROFIT"))
                .build();
    }

    private void initRequests() {
        mApiInterface = mRestAdapter.create(ApiInterface.class);
    }

    /*
    public Observable<DirectionsResponse> getDirections(String origin, String dest, TravelMode travelMode) {
        return mApiInterface.getWalkingDirections(origin, dest, mode);
    }
    */

}

