package com.nbsp.queuer.ui.queue;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egor on 19.12.15.
 */
public class QueueBaseActivity extends AppCompatActivity {
    MembersAdapter mMembersAdapter;
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
        mMembersAdapter = new MembersAdapter();
        mRecyclerView.setAdapter(mMembersAdapter);
        addFakeMembers();
        setTitle("Очередь 1");

    }

    private void addFakeMembers() {
        Member m1 = Member.newMember(12L, 12L, 12L, "Viktor Popov", "213213");
        Member m2 = Member.newMember(12L, 12L, 12L, "Viktor Antonov", "213213");
        mMembersAdapter.add(m1);
        mMembersAdapter.add(m2);
        mMembersAdapter.notifyDataSetChanged();
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

    public static class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MemberViewHolder>{

        List<Member> mMembers;

        public MembersAdapter(){
            this.mMembers = new ArrayList<>();
        }

        public void add(Member member) {
            mMembers.add(member);
        }

        @Override
        public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_card, parent, false);
            MemberViewHolder vh = new MemberViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MemberViewHolder holder, int position) {
            holder.mFullName.setText(mMembers.get(position).name());
            holder.mPosition.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return mMembers.size();
        }

        public static class MemberViewHolder extends RecyclerView.ViewHolder {
            TextView mFullName;
            TextView mPosition;

            public MemberViewHolder(View itemView) {
                super(itemView);
                mFullName = (TextView) itemView.findViewById(R.id.full_name);
                mPosition = (TextView) itemView.findViewById(R.id.position);
            }

        }
    }
}
