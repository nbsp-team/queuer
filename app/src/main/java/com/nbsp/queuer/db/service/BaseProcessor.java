package com.nbsp.queuer.db.service;

/**
 * Created by egor on 20.12.15.
 */
public class BaseProcessor {

    public static final int RESULT_CODE_OK = 1;
    public static final int RESULT_CODE_ERROR = 2;
    interface ProcessorCallback {
        void send(int resultCode);
    }
}
