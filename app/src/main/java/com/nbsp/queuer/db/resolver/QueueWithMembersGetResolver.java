package com.nbsp.queuer.db.resolver;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.nbsp.queuer.db.entity.Member;
import com.nbsp.queuer.db.entity.Queue;
import com.nbsp.queuer.db.entity.DetailQueue;
import com.nbsp.queuer.db.table.MembersTable;
import com.nbsp.queuer.db.table.QueuesTable;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

/**
 * Created by egor on 20.12.15.
 */
public class QueueWithMembersGetResolver extends DefaultGetResolver<DetailQueue> {

    // Sorry for this hack :(
    // We will pass you an instance of StorIO
    // into the mapFromCursor() in v2.0.0.
    //
    // At the moment, you can save this instance in performGet() and then null it at the end
    @NonNull
    private final ThreadLocal<StorIOSQLite> storIOSQLiteFromPerformGet = new ThreadLocal<StorIOSQLite>();

    public QueueWithMembersGetResolver() {
    }

    @NonNull
    @Override
    public DetailQueue mapFromCursor(@NonNull Cursor cursor) {
        final StorIOSQLite storIOSQLite = storIOSQLiteFromPerformGet.get();

        Queue queue = Queue.newQueue(
                cursor.getLong(cursor.getColumnIndexOrThrow(QueuesTable.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(QueuesTable.COLUMN_TITLE)),
                cursor.getLong(cursor.getColumnIndexOrThrow(QueuesTable.COLUMN_CREATOR_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(QueuesTable.COLUMN_CREATOR_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(QueuesTable.COLUMN_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndexOrThrow(QueuesTable.COLUMN_IS_ACTIVE)) == 1,
                cursor.getInt(cursor.getColumnIndexOrThrow(QueuesTable.COLUMN_MAX_MEMBERS)),
                cursor.getLong(cursor.getColumnIndexOrThrow(QueuesTable.COLUMN_CURRENT_MEMBER_ID))
        );

        // Yep, you can reuse StorIO here!
        // Or, you can do manual low level requests here
        // BTW, if you profiled your app and found that such queries are not very fast
        // You can always add some optimized version for particular queries to improve the performance
        final List<Member> queueMembers = storIOSQLite
                .get()
                .listOfObjects(Member.class)
                .withQuery(Query.builder()
                        .table(MembersTable.TABLE)
                        .where(MembersTable.COLUMN_QUEUE_ID + "=?")
                        .whereArgs(queue.id())
                        .build())
                .prepare()
                .executeAsBlocking();


        return new DetailQueue(queue, queueMembers);
    }

}
