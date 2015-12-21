package com.nbsp.queuer.api;

/**
 * Created by Dimorinny on 21.12.15.
 */

public class ApiError extends RuntimeException {
    private int mErrorCode;
    private String mDescription;

    public ApiError(int errorCode, String description) {
        mErrorCode = errorCode;
        mDescription = description;
    }

    public int getErrorCode() {
        return mErrorCode;
    }
    public String getDescription() {
        return mDescription;
    }
}
