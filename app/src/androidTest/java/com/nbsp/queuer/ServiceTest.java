package com.nbsp.queuer;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.nbsp.queuer.db.DB;
import com.nbsp.queuer.db.entity.DetailQueue;
import com.nbsp.queuer.db.entity.Queue;
import com.nbsp.queuer.db.service.BaseProcessor;
import com.nbsp.queuer.db.service.ServiceHelper;
import com.nbsp.queuer.db.table.MembersTable;
import com.nbsp.queuer.db.table.QueuesTable;
import com.pushtorefresh.storio.sqlite.Changes;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by egor on 21.12.15.
 */
public class ServiceTest extends ApplicationTestCase<Application> {

    public ServiceTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DB.createInstance(getContext());
        DB.getInstance().delete().byQuery(
                DeleteQuery.builder()
                        .table(QueuesTable.TABLE)
                        .build())
                .prepare()
                .executeAsBlocking();
        DB.getInstance().delete().byQuery(
                DeleteQuery.builder()
                        .table(MembersTable.TABLE)
                        .build())
                .prepare()
                .executeAsBlocking();
    }

    public void test1() {
        final long reqid = ServiceHelper.getInstance(getContext()).getQueues();

        IntentFilter filter = new IntentFilter(ServiceHelper.ACTION_REQUEST_RESULT);
        BroadcastReceiver requestReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                long resultRequestId = intent
                        .getLongExtra(ServiceHelper.EXTRA_REQUEST_ID, 0);

                if (resultRequestId == reqid) {

                    int resultCode = intent.getIntExtra(ServiceHelper.EXTRA_RESULT_CODE, 0);
                    if (resultCode == BaseProcessor.RESULT_CODE_OK) {
                        List<DetailQueue> queues = DB.getInstance().get()
                                .listOfObjects(DetailQueue.class)
                                .withQuery(QueuesTable.QUERY_ALL)
                                .prepare()
                                .executeAsBlocking();
                        assertEquals(queues.size(), 1);
                        assertEquals(queues.get(0).title(), "title");
                        assertEquals(queues.get(0).members().size(), 2);
                    } else {
                        throw new RuntimeException();
                    }
                }
            }
        };
        getContext().registerReceiver(requestReceiver, filter);


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
