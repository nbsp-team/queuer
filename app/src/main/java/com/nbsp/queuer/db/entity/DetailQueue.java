package com.nbsp.queuer.db.entity;

import android.support.annotation.NonNull;


import java.util.Date;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * Created by egor on 20.12.15.
 */
public class DetailQueue {
    @NonNull
    private final Queue queue;

    @NonNull
    private final List<Member> members;

    public DetailQueue(@NonNull Queue queue, @NonNull List<Member> members) {
        this.queue = queue;
        this.members = unmodifiableList(members); // We prefer immutable entities
    }

    public Member getCurrentMember() {
        for(Member m : members) {
            if (m.userId().equals(queue.currentMemberId)) {
                return m;
            }
        }
        throw new RuntimeException("current member not found in queue");
    }

    public int getPositionOfUserWithId(long userId) {
        int i = 0;
        for(Member m : members) {
            if (m.userId().equals(userId)) {
                return i;
            }
            i++;
        }
        throw new RuntimeException("user not found in queue");
    }

    public List<Member> members() {
        return members;
    }

    public Queue queue() {
        return queue;
    }

    public String title() {
        return queue.title();
    }

    public Long creatorId() {
        return queue.creatorId();
    }

    public String timestamp() {
        return queue.timestamp();
    }
}
