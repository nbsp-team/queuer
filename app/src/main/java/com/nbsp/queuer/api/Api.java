package com.nbsp.queuer.api;

import com.google.gson.GsonBuilder;
import com.nbsp.queuer.db.entity.DetailQueue;
import com.nbsp.queuer.db.entity.Member;
import com.nbsp.queuer.db.entity.Queue;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;
import rx.Observable;

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

    public Observable<List<DetailQueue>> getQueues() {
        List<DetailQueue> list = new ArrayList<>();
        Queue q1 = Queue.newQueue(1L, "title", 1L, "name", "desc", true, 4, 1L, "2015-12-21T20:19:00.419077554+03:00");
        Member m1 = Member.newMember(1L, 1L, 1L, "name", "stamp");
        Member m2 = Member.newMember(2L, 1L, 0L, "name", "stamp");
        List<Member> ml = new ArrayList<>();
        ml.add(m1);
        ml.add(m2);
        list.add(new DetailQueue(q1, ml));
        return Observable.just(list);
    }

}

