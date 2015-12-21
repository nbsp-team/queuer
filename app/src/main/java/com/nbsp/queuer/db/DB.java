package com.nbsp.queuer.db;

import android.content.Context;

import com.nbsp.queuer.db.entity.Member;
import com.nbsp.queuer.db.entity.MemberStorIOSQLiteDeleteResolver;
import com.nbsp.queuer.db.entity.MemberStorIOSQLiteGetResolver;
import com.nbsp.queuer.db.entity.MemberStorIOSQLitePutResolver;
import com.nbsp.queuer.db.entity.Queue;
import com.nbsp.queuer.db.entity.QueueStorIOSQLiteDeleteResolver;
import com.nbsp.queuer.db.entity.QueueStorIOSQLiteGetResolver;
import com.nbsp.queuer.db.entity.QueueStorIOSQLitePutResolver;
import com.nbsp.queuer.db.entity.DetailQueue;
import com.nbsp.queuer.db.resolver.QueueWithMembersDeleteResolver;
import com.nbsp.queuer.db.resolver.QueueWithMembersGetResolver;
import com.nbsp.queuer.db.resolver.QueueWithMembersPutResolver;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

/**
 * Created by egor on 20.12.15.
 */
public class DB {

    static StorIOSQLite instance = null;

    private DB() {

    }

    public static StorIOSQLite getInstance() {
        return instance;
    }

    public static void createInstance(Context context) {
        instance = DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(new DbOpenHelper(context))
                .addTypeMapping(Queue.class, SQLiteTypeMapping.<Queue>builder()
                        .putResolver(new QueueStorIOSQLitePutResolver())
                        .getResolver(new QueueStorIOSQLiteGetResolver())
                        .deleteResolver(new QueueStorIOSQLiteDeleteResolver())
                        .build())
                .addTypeMapping(Member.class, SQLiteTypeMapping.<Member>builder()
                        .putResolver(new MemberStorIOSQLitePutResolver())
                        .getResolver(new MemberStorIOSQLiteGetResolver())
                        .deleteResolver(new MemberStorIOSQLiteDeleteResolver())
                        .build())
                .addTypeMapping(DetailQueue.class, SQLiteTypeMapping.<DetailQueue>builder()
                        .putResolver(new QueueWithMembersPutResolver())
                        .getResolver(new QueueWithMembersGetResolver())
                        .deleteResolver(new QueueWithMembersDeleteResolver())
                        .build())
                .build();
    }
}
