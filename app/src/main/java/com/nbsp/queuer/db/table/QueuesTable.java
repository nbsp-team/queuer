package com.nbsp.queuer.db.table;

import com.pushtorefresh.storio.sqlite.queries.Query;

/**
 * Created by egor on 20.12.15.
 */

public class QueuesTable {
    
    public static final String TABLE = "queues";

    public static final String COLUMN_ID = "_id";
    
    public static final String COLUMN_TITLE = "title";

    public static final String COLUMN_DESCRIPTION = "description";

    public static final String COLUMN_TIMESTAMP = "timestamp";

    public static final String COLUMN_MAX_MEMBERS = "max_members";

    public static final String COLUMN_CREATOR_ID = "creator_id";

    public static final String COLUMN_CREATOR_NAME = "creator_full_name";

    public static final String COLUMN_CURRENT_MEMBER_ID = "current_member_id";

    public static final String COLUMN_IS_ACTIVE = "is_active";

    public static final Query QUERY_ALL = Query.builder()
            .table(TABLE)
            .build();

    // This is just class with Meta Data, we don't need instances
    private QueuesTable() {
        throw new IllegalStateException("No instances please");
    }

    // Better than static final field -> allows VM to unload useless String
    // Because you need this string only once per application life on the device
    
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE + "("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
                + COLUMN_TITLE + " TEXT NOT NULL, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_TIMESTAMP + " TEXT NOT NULL, "
                + COLUMN_MAX_MEMBERS + " INTEGER, "
                + COLUMN_CREATOR_ID + " INTEGER NOT NULL, "
                + COLUMN_CREATOR_NAME + " TEXT NOT NULL, " // чтобы не хранить таблицу людей
                + COLUMN_CURRENT_MEMBER_ID + " INTEGER NOT NULL, "
                + COLUMN_IS_ACTIVE + " INTEGER NOT NULL"
                // + COLUMN_IS_DELETED + " INTEGER NOT NULL DEFAULT 0"
                + ");";
    }
}
