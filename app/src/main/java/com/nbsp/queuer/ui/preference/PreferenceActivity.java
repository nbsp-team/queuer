package com.nbsp.queuer.ui.preference;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.nbsp.queuer.R;
import com.nbsp.queuer.ui.BaseActivity;

public class PreferenceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefrences);

        getFragmentManager().beginTransaction()
                .replace(R.id.content, new SettingsFragment())
                .commit();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
