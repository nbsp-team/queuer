package com.nbsp.queuer.ui.queue.member;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nbsp.queuer.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class QueueMemberFragment extends Fragment {


    public QueueMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_queue_member, container, false);
    }

    public static QueueMemberFragment newInstance() {
        Bundle args = new Bundle();

        QueueMemberFragment fragment = new QueueMemberFragment();
        fragment.setArguments(args);

        return fragment;
    }


}
