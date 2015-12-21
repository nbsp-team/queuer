package com.nbsp.queuer.ui.queues;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

/**
 * Created by nickolay on 21.12.15.
 */

public class AllQueueListFragment extends QueueListFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void updateList() {
        addFakeQueues();
        setRefreshing(false);
    }

    public static AllQueueListFragment newInstance() {
        return new AllQueueListFragment();
    }
}
