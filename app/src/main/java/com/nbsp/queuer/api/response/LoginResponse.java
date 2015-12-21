package com.nbsp.queuer.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dimorinny on 21.12.15.
 */
public class LoginResponse {
    @SerializedName("AccessToken")
    private String mAccessToken;

    @SerializedName("RefreshToken")
    private String mRefreshToken;

    public String getAccessToken() {
        return mAccessToken;
    }

    public String getRefreshToken() {
        return mRefreshToken;
    }
}
