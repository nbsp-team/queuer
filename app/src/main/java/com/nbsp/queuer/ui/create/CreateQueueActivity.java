package com.nbsp.queuer.ui.create;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.nbsp.queuer.R;
import com.nbsp.queuer.ui.BaseActivity;
import com.nbsp.queuer.utils.ErrorUtils;

public class CreateQueueActivity extends BaseActivity {
    private ProgressDialog mCreateProgressDialog;
    private EditText mNameEditText;
    private EditText mDescriptionEditText;
    private EditText mUserLimitEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_queue);

        mCreateProgressDialog = new ProgressDialog(this);
        mCreateProgressDialog.setTitle(getString(R.string.creating_queue));
        mCreateProgressDialog.setMessage(getString(R.string.pleas_wait));

        initViews();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        }
    }

    private void initViews() {
        mNameEditText = (EditText) findViewById(R.id.name);
        mDescriptionEditText = (EditText) findViewById(R.id.description);
        mUserLimitEditText = (EditText) findViewById(R.id.user_limit);
    }

    /**
     * Создание меню
     *
     * @param menu the menu
     * @return true, if successful
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create_queue, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_create:
                createQueue();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createQueue() {
        if (mNameEditText.getText().toString().length() == 0 ||
                mDescriptionEditText.getText().toString().length() == 0 ||
                mUserLimitEditText.getText().toString().length() == 0) {
            ErrorUtils.showError(this, getString(R.string.fill_required_fields));
            return;
        }

        mCreateProgressDialog.show();
    }
}
