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
import com.nbsp.queuer.db.entity.Member;
import com.nbsp.queuer.db.entity.Queue;
import com.nbsp.queuer.db.service.BaseProcessor;
import com.nbsp.queuer.db.service.ServiceHelper;
import com.nbsp.queuer.db.table.MembersTable;
import com.nbsp.queuer.db.table.QueuesTable;
import com.nbsp.queuer.utils.QueueTimeUtils;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by egor on 21.12.15.
 */
public class TimeEstimateTest extends ApplicationTestCase<Application> {

    public TimeEstimateTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

    }

    public void test1() {
        Queue q1 = Queue.newQueue(1L, "", 1L, "", "", true, 4, 3L, "2015-12-20T04:00:00.419077554+03:00");
        Member m1 = Member.newMember(1L, 1L, 1L, "", "");
        Member m2 = Member.newMember(1L, 1L, 2L, "", "");
        Member m3 = Member.newMember(1L, 1L, 3L, "", "");
        Member m4 = Member.newMember(1L, 1L, 4L, "", "");
        List<Member> ml = new ArrayList<>();
        ml.add(m1);
        ml.add(m2);
        ml.add(m3);
        ml.add(m4);
        DetailQueue dq = new DetailQueue(q1, ml);


        try {
            Log.d("tag", QueueTimeUtils.timeUntilNLeavesQueue(4, dq));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
