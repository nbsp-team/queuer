package com.nbsp.queuer.ui.queues;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nbsp.queuer.R;
import com.nbsp.queuer.db.entity.DetailQueue;

import java.util.ArrayList;
import java.util.List;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.MemberViewHolder>{

    private List<DetailQueue> mQueues;

    public QueueAdapter(){
        this.mQueues = new ArrayList<>();
    }

    public void add(DetailQueue queue) {
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
        DetailQueue queue = mQueues.get(position);
        holder.mTitle.setText(queue.title());
        Long currentUserId = 0L; // todo get it
        boolean isAdmin = queue.creatorId().equals(currentUserId);
        holder.mInfo.setText(makeInfoTextFor(queue));
        if(isAdmin) {
            holder.mIsAdmin.setText("Я админ!");
        } else {
            holder.mIsAdmin.setText("Я не админ");
        }
    }

    private String makeInfoTextFor(DetailQueue queue) {
        Long currentUserId = 0L; // todo get it
        boolean isAdmin = queue.creatorId().equals(currentUserId);
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

    public void clear() {
        mQueues.clear();
    }

    public void addAll(List<DetailQueue> queues) {
        mQueues.addAll(queues);
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