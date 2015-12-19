package com.nbsp.queuer.ui.queue.admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nbsp.queuer.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class QueueAdminFragment extends Fragment {


    public QueueAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_queue_admin, container, false);
    }

    public static QueueAdminFragment newInstance() {
        Bundle args = new Bundle();

        QueueAdminFragment fragment = new QueueAdminFragment();
        fragment.setArguments(args);

        return fragment;
    }


}
