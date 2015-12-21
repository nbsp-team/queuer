package com.nbsp.queuer.ui.queues;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nbsp.queuer.db.DB;
import com.nbsp.queuer.db.entity.DetailQueue;
import com.nbsp.queuer.db.service.BaseProcessor;
import com.nbsp.queuer.db.service.ServiceHelper;
import com.nbsp.queuer.db.table.QueuesTable;

import java.util.List;

/**
 * Created by nickolay on 21.12.15.
 */

public class MyQueueListFragment extends QueueListFragment {
    private long mServiceHelperRequestId;
    private BroadcastReceiver mReceiver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(ServiceHelper.ACTION_REQUEST_RESULT);
        mReceiver = new ServiceHelperReceiver();
        getContext().registerReceiver(mReceiver, filter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateList();
    }

    private void updateList() {
        mServiceHelperRequestId = ServiceHelper.getInstance(getContext()).getQueues();
    }

    public static MyQueueListFragment newInstance() {
        return new MyQueueListFragment();
    }

    class ServiceHelperReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            long resultRequestId = intent
                    .getLongExtra(ServiceHelper.EXTRA_REQUEST_ID, 0);

            if (resultRequestId == mServiceHelperRequestId) {

                int resultCode = intent.getIntExtra(ServiceHelper.EXTRA_RESULT_CODE, 0);
                if (resultCode == BaseProcessor.RESULT_CODE_OK) {
                    List<DetailQueue> queues = DB.getInstance().get()
                            .listOfObjects(DetailQueue.class)
                            .withQuery(QueuesTable.QUERY_ALL)
                            .prepare()
                            .executeAsBlocking();
                    mQueueAdapter.addAll(queues);
                    mQueueAdapter.notifyDataSetChanged();
                } else {
                    throw new RuntimeException("processor status not ok");
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mReceiver);
    }
}
