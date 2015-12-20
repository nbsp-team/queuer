package com.nbsp.queuer.db.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nbsp.queuer.db.table.MembersTable;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by egor on 20.12.15.
 */

// This annotation will trigger annotation processor
// Which will generate type mapping code in compile time,
// You just need to link it in your code.
@StorIOSQLiteType(table = MembersTable.TABLE)
public class Member {
    @Nullable
    @StorIOSQLiteColumn(name = MembersTable.COLUMN_ID, key = true)
    Long id;

    @NonNull
    @StorIOSQLiteColumn(name = MembersTable.COLUMN_QUEUE_ID)
    Long queueId;

    @NonNull
    @StorIOSQLiteColumn(name = MembersTable.COLUMN_USER_NAME)
    String userName;

    @NonNull
    @StorIOSQLiteColumn(name = MembersTable.COLUMN_USER_ID)
    Long userId;

    @NonNull
    @StorIOSQLiteColumn(name = MembersTable.COLUMN_TIMESTAMP)
    String timestamp;

    Member() {}

    private Member(@Nullable Long id, @NonNull Long queueId, @NonNull Long userId,
                   @NonNull String userName, @NonNull String timestamp) {
        this.id = id;
        this.queueId = queueId;
        this.userName = userName;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    public static Member newMember(@Nullable Long id, @NonNull Long queueId, @NonNull Long userId,
                            @NonNull String userName, @NonNull String timestamp) {
        return new Member(id, queueId, userId, userName, timestamp);
    }

    public Long id() {
        return id;
    }

    public String name() {
        return userName;
    }
}
