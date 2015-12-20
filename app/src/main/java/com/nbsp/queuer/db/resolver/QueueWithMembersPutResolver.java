package com.nbsp.queuer.db.resolver;

import android.support.annotation.NonNull;

import com.nbsp.queuer.db.entity.DetailQueue;
import com.nbsp.queuer.db.table.MembersTable;
import com.nbsp.queuer.db.table.QueuesTable;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResolver;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by egor on 20.12.15.
 */
public class QueueWithMembersPutResolver extends PutResolver<DetailQueue> {

    @NonNull
    @Override
    public PutResult performPut(@NonNull StorIOSQLite storIOSQLite, @NonNull DetailQueue detailQueue) {
        // 1 for user and other for his/her tweets
        final List<Object> objectsToPut = new ArrayList<Object>(1 + detailQueue.members().size());

        objectsToPut.add(detailQueue.queue());
        objectsToPut.addAll(detailQueue.members());

        storIOSQLite
                .put()
                .objects(objectsToPut)
                .prepare()
                .executeAsBlocking();

        // BTW, you can save it as static final
        final Set<String> affectedTables = new HashSet<String>(2);

        affectedTables.add(QueuesTable.TABLE);
        affectedTables.add(MembersTable.TABLE);

        // Actually, we don't know what to return for such complex operation, so let's return update result
        return PutResult.newUpdateResult(objectsToPut.size(), affectedTables);
    }
}