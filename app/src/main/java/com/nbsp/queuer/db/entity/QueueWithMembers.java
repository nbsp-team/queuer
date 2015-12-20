package com.nbsp.queuer.db.entity;

import android.support.annotation.NonNull;

import com.nbsp.queuer.model.QueueMember;
import com.nbsp.queuer.model.User;

import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * Created by egor on 20.12.15.
 */
public class QueueWithMembers {
    @NonNull
    private final Queue queue;

    @NonNull
    private final List<Member> members;

    public QueueWithMembers(@NonNull Queue queue, @NonNull List<Member> members) {
        this.queue = queue;
        this.members = unmodifiableList(members); // We prefer immutable entities
    }

    public Member getCurrentMember() {
        for(Member m : members) {
            if (m.id().equals(queue.currentMemberId)) {
                return m;
            }
        }
        throw new RuntimeException("current member not found in queue");
    }

    public List<Member> members() {
        return members;
    }

    public Queue queue() {
        return queue;
    }
}
