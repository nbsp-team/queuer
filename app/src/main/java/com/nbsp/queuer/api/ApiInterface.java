package com.nbsp.queuer.api;

import com.nbsp.queuer.api.response.LoginResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by Dimorinny on 10.09.15.
 */

public interface ApiInterface {

    @POST("/login")
    @FormUrlEncoded
    Observable<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password);

    @POST("/register")
    @FormUrlEncoded
    Observable<LoginResponse> register(
            @Field("email") String email,
            @Field("password") String password,
            @Field("firstName") String firstName,
            @Field("lastName") String lastName);
}
