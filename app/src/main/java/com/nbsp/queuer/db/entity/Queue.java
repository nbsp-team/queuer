package com.nbsp.queuer.db.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nbsp.queuer.db.table.QueuesTable;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by egor on 20.12.15.
 */

// This annotation will trigger annotation processor
// Which will generate type mapping code in compile time,
// You just need to link it in your code.
@StorIOSQLiteType(table = QueuesTable.TABLE)
public class Queue {

    @Nullable
    @StorIOSQLiteColumn(name = QueuesTable.COLUMN_ID, key = true)
    Long id;

    @NonNull
    @StorIOSQLiteColumn(name = QueuesTable.COLUMN_CREATOR_ID)
    Long creatorId;

    @NonNull
    @StorIOSQLiteColumn(name = QueuesTable.COLUMN_CREATOR_NAME)
    String creatorName;

    @NonNull
    @StorIOSQLiteColumn(name = QueuesTable.COLUMN_CURRENT_MEMBER_ID)
    Long currentMemberId;

    @Nullable
    @StorIOSQLiteColumn(name = QueuesTable.COLUMN_DESCRIPTION)
    String description;

    @NonNull
    @StorIOSQLiteColumn(name = QueuesTable.COLUMN_IS_ACTIVE)
    Boolean isActive;

    @Nullable
    @StorIOSQLiteColumn(name = QueuesTable.COLUMN_MAX_MEMBERS)
    Integer maxMembers;

    @NonNull
    @StorIOSQLiteColumn(name = QueuesTable.COLUMN_TITLE)
    String title;

    Queue() {}

    private Queue(@Nullable Long id, @NonNull String title, @NonNull Long creatorId,
                  @NonNull String creatorName, @Nullable String description, @NonNull Boolean isActive,
                  @Nullable Integer maxMembers, @NonNull Long currentMemberId) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.description = description;
        this.isActive = isActive;
        this.maxMembers = maxMembers;
        this.currentMemberId = currentMemberId;
    }

    public static Queue newQueue(@Nullable Long id, @NonNull String title, @NonNull Long creatorId,
                  @NonNull String creatorName, @Nullable String description, @NonNull Boolean isActive,
                  @Nullable Integer maxMembers, @NonNull Long currentMemberId) {
        return new Queue(id, title, creatorId, creatorName, description, isActive, maxMembers,
                currentMemberId);
    }

    @Nullable
    public Long id() {
        return id;
    }

    @NonNull
    public Long currentMemberId() {
        return currentMemberId;
    }

    public String title() {
        return title;
    }

    public Long creatorId() {
        return creatorId;
    }
}
