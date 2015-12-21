package com.nbsp.queuer;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.nbsp.queuer.db.DB;
import com.nbsp.queuer.db.entity.DetailQueue;
import com.nbsp.queuer.db.entity.Member;
import com.nbsp.queuer.db.entity.Queue;
import com.nbsp.queuer.db.table.MembersTable;
import com.nbsp.queuer.db.table.QueuesTable;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class DataBaseTest extends ApplicationTestCase<Application> {
    public DataBaseTest() {
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



    public void testPutDetailQueue() {
        Member m1 = Member.newMember(null, 1L, 1L, "test user name", "timestamp");
        Queue q1 = Queue.newQueue(null, "testQueue", 1L, "test creator name", "descr", true,
                7, 10L, "stamp");
        List<Member> list = new ArrayList<>();
        list.add(m1);
        DetailQueue dq1 = new DetailQueue(q1, list);
        DB.getInstance().put().object(dq1).prepare().executeAsBlocking();

        List<DetailQueue> dqlist = DB.getInstance().get().listOfObjects(DetailQueue.class)
                .withQuery(QueuesTable.QUERY_ALL).prepare().executeAsBlocking();
        assertNotNull(dqlist);
        assertEquals(dqlist.size(), 1);
        dq1 = dqlist.get(0);
        assertEquals(dq1.title(), "testQueue");
        assertEquals(dq1.members().get(0).name(), "test user name");
    }



}