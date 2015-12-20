package com.nbsp.queuer.ui.queues;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nbsp.queuer.R;
import com.nbsp.queuer.db.entity.Member;
import com.nbsp.queuer.db.entity.Queue;
import com.nbsp.queuer.db.entity.QueueWithMembers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egor on 19.12.15.
 */
public class MyQueuesActivity extends AppCompatActivity {
    QueuesAdapter mQueuesAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);
        mQueuesAdapter = new QueuesAdapter();
        mRecyclerView.setAdapter(mQueuesAdapter);
        addFakeQueues();
    }

    private void addFakeQueues() {
        Queue q1 = Queue.newQueue(1L, "Очередь1", 0L, "Вася", "заходим", true, 15, 1L);
        Queue q2 = Queue.newQueue(2L, "Очередь2", 1L, "Волоколам", "заходим1", true, 15, 0L);
        Member m1 = Member.newMember(0L, 12L, 12L, "Viktor Popov", "213213");
        Member m2 = Member.newMember(12L, 12L, 12L, "Viktor Antonov", "213213");
        List<Member> members = new ArrayList<>();
        members.add(m1);
        members.add(m2);
        QueueWithMembers qm1 = new QueueWithMembers(q1, members);
        QueueWithMembers qm2 = new QueueWithMembers(q2, members);
        mQueuesAdapter.add(qm1);
        mQueuesAdapter.add(qm2);
        mQueuesAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class QueuesAdapter extends RecyclerView.Adapter<QueuesAdapter.MemberViewHolder>{

        List<QueueWithMembers> mQueues;

        public QueuesAdapter(){
            this.mQueues = new ArrayList<>();
        }

        public void add(QueueWithMembers queue) {
            mQueues.add(queue);
        }

        @Override
        public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.queue_card, parent, false);
            MemberViewHolder vh = new MemberViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MemberViewHolder holder, int position) {
            QueueWithMembers queue = mQueues.get(position);
            holder.mTitle.setText(queue.title());
            Long currentUserId = 0L; // todo get it
            boolean isAdmin = queue.creatorId().equals(currentUserId);
            holder.mInfo.setText(makeInfoTextFor(queue, isAdmin));
            if(isAdmin) {
                holder.mIsAdmin.setText("Я админ!");
            } else {
                holder.mIsAdmin.setText("Я не админ");
            }
        }

        private String makeInfoTextFor(QueueWithMembers queue, boolean isAdmin) {
            Long currentUserId = 0L; // todo get it
            if (isAdmin) {
                return "14 чел. ~ 1 час 41 мин.";
            } else {
                if (queue.getCurrentMember().id().equals(currentUserId)) {
                    return "Ваша очередь!";
                } else {
                    return "перед вами 8 чел. ~ 1 час 41 мин.";
                }
            }
        }

        @Override
        public int getItemCount() {
            return mQueues.size();
        }

        public static class MemberViewHolder extends RecyclerView.ViewHolder {
            TextView mTitle;
            TextView mInfo;
            TextView mIsAdmin;

            public MemberViewHolder(View itemView) {
                super(itemView);
                mTitle = (TextView) itemView.findViewById(R.id.queue_title);
                mInfo = (TextView) itemView.findViewById(R.id.info_text);
                mIsAdmin = (TextView) itemView.findViewById(R.id.is_admin);
            }

        }
    }
}
