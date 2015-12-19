package com.nbsp.queuer.ui.queue.member;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.nbsp.queuer.R;
import com.nbsp.queuer.ui.queue.QueueBaseActivity;

public class QueueMemberActivity extends QueueBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fragment memberFrag = QueueMemberFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, memberFrag)
                .commitAllowingStateLoss();
    }
}
