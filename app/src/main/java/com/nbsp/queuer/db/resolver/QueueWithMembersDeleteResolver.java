package com.nbsp.queuer.db.resolver;

import android.support.annotation.NonNull;

import com.nbsp.queuer.db.entity.DetailQueue;
import com.nbsp.queuer.db.table.MembersTable;
import com.nbsp.queuer.db.table.QueuesTable;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResolver;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by egor on 20.12.15.
 */
public class QueueWithMembersDeleteResolver extends DeleteResolver<DetailQueue> {

    @NonNull
    @Override
    public DeleteResult performDelete(@NonNull StorIOSQLite storIOSQLite,
                                      @NonNull DetailQueue detailQueue) {

        final List<Object> objectsToDelete = new ArrayList<>(1 + detailQueue.members().size());

        objectsToDelete.add(detailQueue.queue());
        objectsToDelete.addAll(detailQueue.members());

        storIOSQLite
        .delete()
        .objects(objectsToDelete)
        .prepare()
        .executeAsBlocking();

        // BTW, you can save it as static final
        final Set<String> affectedTables = new HashSet<String>(2);

        affectedTables.add(QueuesTable.TABLE);
        affectedTables.add(MembersTable.TABLE);

        return DeleteResult.newInstance(objectsToDelete.size(), affectedTables);
    }
}
