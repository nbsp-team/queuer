package com.nbsp.queuer.ui.queues;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nbsp.queuer.R;
import com.nbsp.queuer.db.entity.DetailQueue;
import com.nbsp.queuer.db.entity.Member;
import com.nbsp.queuer.db.entity.Queue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nickolay on 21.12.15.
 */

public class QueueListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    protected QueueAdapter mQueueAdapter;

    public QueueListFragment() {
    }

    public static QueueListFragment newInstance() {
        return new QueueListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_queue_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(llm);
        mQueueAdapter = new QueueAdapter();
        mRecyclerView.setAdapter(mQueueAdapter);


        return view;
    }



    protected void addFakeQueues() {
        Queue q1 = Queue.newQueue(1L, "Очередь1", 0L, "Вася", "заходим", true, 15, 0L, "2015-12-20T04:58:09.419077554+03:00");
        Queue q2 = Queue.newQueue(2L, "Очередь2", 1L, "Волоколам", "заходим1", true, 15, 0L, "2015-12-20T04:58:09.419077554+03:00");
        Member m1 = Member.newMember(0L, 1L, 0L, "Viktor Popov", "213213");
        Member m2 = Member.newMember(12L, 1L, 12L, "Viktor Antonov", "213213");
        Member m3 = Member.newMember(0L, 2L, 0L, "Viktor Popov", "213213");
        Member m4 = Member.newMember(12L, 2L, 12L, "Viktor Antonov", "213213");
        List<Member> members = new ArrayList<>();
        members.add(m1);
        members.add(m2);
        DetailQueue qm1 = new DetailQueue(q1, members);
        List<Member> members2 = new ArrayList<>();
        members2.add(m3);
        members2.add(m4);
        DetailQueue qm2 = new DetailQueue(q2, members2);
        mQueueAdapter.clear();
        mQueueAdapter.add(qm1);
        mQueueAdapter.add(qm2);
        mQueueAdapter.notifyDataSetChanged();
    }
}
