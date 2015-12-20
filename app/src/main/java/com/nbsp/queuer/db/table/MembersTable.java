package com.nbsp.queuer.db.table;

import com.pushtorefresh.storio.sqlite.queries.Query;

/**
 * Created by egor on 20.12.15.
 */

public class MembersTable {

    public static final String TABLE = "members";

    public static final String COLUMN_ID = "_id";

    public static final String COLUMN_QUEUE_ID = "queue_id";

    public static final String COLUMN_USER_ID = "user_id";

    public static final String COLUMN_USER_NAME = "user_full_name";

    public static final String COLUMN_TIMESTAMP = "timestamp";

    public static final Query QUERY_ALL = Query.builder()
            .table(TABLE)
            .build();

    // This is just class with Meta Data, we don't need instances
    private MembersTable() {
        throw new IllegalStateException("No instances please");
    }

    // Better than static final field -> allows VM to unload useless String
    // Because you need this string only once per application life on the device
    
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE + "("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
                + COLUMN_QUEUE_ID + " INTEGER NOT NULL, "
                + COLUMN_USER_ID + " INTEGER NOT NULL, "
                + COLUMN_TIMESTAMP + " TEXT NOT NULL, "
                + COLUMN_USER_NAME + " TEXT NOT NULL"
                + ");";
    }
}
