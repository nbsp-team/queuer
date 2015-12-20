package com.nbsp.queuer.db.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by egor on 20.12.15.
 */
public class ServiceHelper {
    public static String ACTION_REQUEST_RESULT = "REQUEST_RESULT";
    public static String EXTRA_REQUEST_ID = "EXTRA_REQUEST_ID";
    public static String EXTRA_RESULT_CODE = "EXTRA_RESULT_CODE";

    private static final String REQUEST_ID = "REQUEST_ID";
    private static ServiceHelper instance = null;
    private Context ctx;
    private ServiceHelper(Context ctx){
        this.ctx = ctx.getApplicationContext();
    }
    private static Object lock = new Object();
    public static ServiceHelper getInstance(Context ctx){
        synchronized (lock) {
            if(instance == null){
                instance = new ServiceHelper(ctx);
            }
        }

        return instance;
    }

    private static final String QUEUES_HASHKEY = "queues";

    private Map<String,Long> pendingRequests = new HashMap<String,Long>();

    public long getQueues() {
        if (pendingRequests.containsKey(QUEUES_HASHKEY)){
            return pendingRequests.get(QUEUES_HASHKEY);
        }

        long requestId = generateRequestID();

        pendingRequests.put(QUEUES_HASHKEY, requestId);

        ResultReceiver serviceCallback = new ResultReceiver(null){
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                handleGetQueuesResponse(resultCode, resultData);
            }
        };

        Intent intent = new Intent(this.ctx, QueuerService.class);
        intent.putExtra(QueuerService.METHOD_EXTRA, QueuerService.METHOD_GET);
        intent.putExtra(QueuerService.RESOURCE_TYPE_EXTRA, QueuerService.RESOURCE_TYPE_MY_QUEUES);
        intent.putExtra(QueuerService.SERVICE_CALLBACK, serviceCallback);
        intent.putExtra(REQUEST_ID, requestId);

        this.ctx.startService(intent);

        return requestId;
    }

    private void handleGetQueuesResponse(int resultCode, Bundle resultData){
        Intent origIntent = (Intent) resultData.getParcelable(QueuerService.ORIGINAL_INTENT_EXTRA);

        if(origIntent != null){
            long requestId = origIntent.getLongExtra(REQUEST_ID, 0);

            pendingRequests.remove(QUEUES_HASHKEY);

            Intent resultBroadcast = new Intent(ACTION_REQUEST_RESULT);
            resultBroadcast.putExtra(EXTRA_REQUEST_ID, requestId);
            resultBroadcast.putExtra(EXTRA_RESULT_CODE, resultCode);

            ctx.sendBroadcast(resultBroadcast);
        }
    }

    private long generateRequestID() {
        long requestId = UUID.randomUUID().getLeastSignificantBits();
        return requestId;
    }
}
