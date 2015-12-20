package com.nbsp.queuer.db.resolver;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.nbsp.queuer.db.entity.Member;
import com.nbsp.queuer.db.entity.Queue;
import com.nbsp.queuer.db.entity.QueueWithMembers;
import com.nbsp.queuer.db.table.MembersTable;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;
import com.pushtorefresh.storio.sqlite.operations.get.GetResolver;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

/**
 * Created by egor on 20.12.15.
 */
public class QueueWithMembersGetResolver extends DefaultGetResolver<QueueWithMembers> {

    @NonNull
    private final GetResolver<Queue> queueGetResolver;

    // Sorry for this hack :(
    // We will pass you an instance of StorIO
    // into the mapFromCursor() in v2.0.0.
    //
    // At the moment, you can save this instance in performGet() and then null it at the end
    @NonNull
    private final ThreadLocal<StorIOSQLite> storIOSQLiteFromPerformGet = new ThreadLocal<StorIOSQLite>();

    public QueueWithMembersGetResolver(@NonNull GetResolver<Queue> queueGetResolver) {
        this.queueGetResolver = queueGetResolver;
    }

    @NonNull
    @Override
    public QueueWithMembers mapFromCursor(@NonNull Cursor cursor) {
        final StorIOSQLite storIOSQLite = storIOSQLiteFromPerformGet.get();

        // Or you can manually parse cursor (it will be sliiightly faster)
        final Queue queue = queueGetResolver.mapFromCursor(cursor);

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


        return new QueueWithMembers(queue, queueMembers);
    }

}
