package com.nbsp.queuer.ui.register;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nbsp.queuer.R;
import com.nbsp.queuer.api.Api;
import com.nbsp.queuer.api.response.LoginResponse;
import com.nbsp.queuer.ui.BaseActivity;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Dimorinny on 21.12.15.
 */
public class RegisterActivity extends BaseActivity {
    private EditText mLoginInput;
    private EditText mFirstNameInput;
    private EditText mLastNameInput;
    private EditText mPasswordInput;
    private EditText mReplyPasswordInput;
    private Button mRegisterButton;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initProgressDialog();
        initListeners();
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Регестрация");
        mProgressDialog.setMessage("Подождите, идет регестрация");
    }

    private void initViews() {
        mLoginInput = (EditText) findViewById(R.id.email_input);
        mFirstNameInput = (EditText) findViewById(R.id.first_name_input);
        mLastNameInput = (EditText) findViewById(R.id.last_name_input);
        mPasswordInput = (EditText) findViewById(R.id.password_input);
        mReplyPasswordInput = (EditText) findViewById(R.id.reply_password_input);
        mRegisterButton = (Button) findViewById(R.id.register_button);
    }

    private void initListeners() {
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateAllFields()) {
                    showToast("Заполните все поля");
                    return;
                }

                if (!validatePasswords()) {
                    showToast("Заполните все поля");
                    return;
                }

                register(
                        mLoginInput.getText().toString(),
                        mFirstNameInput.getText().toString(),
                        mLastNameInput.getText().toString(),
                        mPasswordInput.getText().toString()
                );
            }
        });
    }

    private boolean validatePasswords() {
        return mPasswordInput.getText().toString().equals(mReplyPasswordInput.getText().toString());
    }

    private boolean validateAllFields() {
        return !(mLoginInput.getText().toString().isEmpty()
                || mFirstNameInput.getText().toString().isEmpty()
                || mLastNameInput.getText().toString().isEmpty()
                || mPasswordInput.getText().toString().isEmpty()
                || mReplyPasswordInput.getText().toString().isEmpty());
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void showProgressDialog(boolean show) {
        if (show) {
            mProgressDialog.show();
        } else {
            mProgressDialog.hide();
        }
    }

    private void register(String login, String firstName, String lastName, String password) {
        showProgressDialog(true);
        Api.getInstance().register(login, password, firstName, lastName)
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        showProgressDialog(false);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        showProgressDialog(false);
                        Log.v("LoginActivity", loginResponse.getAccessToken());
                        Log.v("LoginActivity", loginResponse.getRefreshToken());
                    }
                });
    }
}
