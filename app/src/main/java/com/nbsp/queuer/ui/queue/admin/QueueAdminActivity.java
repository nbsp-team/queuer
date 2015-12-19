package com.nbsp.queuer.ui.queue.admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

import com.nbsp.queuer.R;
import com.nbsp.queuer.ui.queue.QueueBaseActivity;

public class QueueAdminActivity extends QueueBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fragment adminFrag = QueueAdminFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, adminFrag)
                .commitAllowingStateLoss();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.queue_admin, menu);
        return true;
    }
}
