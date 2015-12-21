package com.nbsp.queuer.db.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * Created by egor on 20.12.15.
 */
public class QueuerService extends IntentService {

    public static final String METHOD_EXTRA = "METHOD_EXTRA";

    public static final String METHOD_GET = "GET";

    public static final String RESOURCE_TYPE_EXTRA = "RESOURCE_TYPE_EXTRA";

    public static final int RESOURCE_TYPE_MY_QUEUES = 1;

    public static final String SERVICE_CALLBACK = "SERVICE_CALLBACK";

    public static final String ORIGINAL_INTENT_EXTRA = "ORIGINAL_INTENT_EXTRA";

    private static final int REQUEST_INVALID = -1;

    private ResultReceiver mCallback;

    private Intent mOriginalRequestIntent;

    public QueuerService() {
        super("super service");
    }

    @Override
    protected void onHandleIntent(Intent requestIntent) {
        mOriginalRequestIntent = requestIntent;
        // Get request data from Intent
        String method = requestIntent.getStringExtra(METHOD_EXTRA);
        int resourceType = requestIntent.getIntExtra(RESOURCE_TYPE_EXTRA, -1);
        mCallback = requestIntent.getParcelableExtra(SERVICE_CALLBACK);

        switch (resourceType) {
            case RESOURCE_TYPE_MY_QUEUES:
                if (method.equalsIgnoreCase(METHOD_GET)) {
                    MyQueuesProcessor processor = new MyQueuesProcessor();
                    processor.getQueues(makeMyQueuesProcessorCallback());
                } else {
                    mCallback.send(REQUEST_INVALID, getOriginalIntentBundle());
                }
                break;
            default:
                mCallback.send(REQUEST_INVALID, getOriginalIntentBundle());
                break;
        }
    }

    private BaseProcessor.ProcessorCallback makeMyQueuesProcessorCallback() {
        return new BaseProcessor.ProcessorCallback() {
            @Override
            public void send(int resultCode) {
                if (mCallback != null) {
                    mCallback.send(resultCode, getOriginalIntentBundle());
                }
            }
        };
    }

    protected Bundle getOriginalIntentBundle() {
        Bundle originalRequest = new Bundle();
        originalRequest.putParcelable(ORIGINAL_INTENT_EXTRA, mOriginalRequestIntent);
        return originalRequest;
    }
}
