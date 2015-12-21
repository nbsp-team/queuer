package com.nbsp.queuer.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nbsp.queuer.R;
import com.nbsp.queuer.api.Api;
import com.nbsp.queuer.api.response.LoginResponse;
import com.nbsp.queuer.ui.BaseActivity;
import com.nbsp.queuer.ui.register.RegisterActivity;
import com.nbsp.queuer.utils.ErrorUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Dimorinny on 21.12.15.
 */
public class LoginActivity extends BaseActivity{
    private EditText mLoginInput;
    private EditText mPasswordInput;
    private Button mLoginButton;
    private Button mRegisterButton;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initProgressDialog();
        initListeners();
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Вход");
        mProgressDialog.setMessage("Подождите, идет вход");
    }

    private void initViews() {
        mLoginInput = (EditText) findViewById(R.id.email_input);
        mPasswordInput = (EditText) findViewById(R.id.password_input);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mRegisterButton = (Button) findViewById(R.id.login_register_button);
    }

    private void initListeners() {
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateAllFields()) {
                    ErrorUtils.showError(LoginActivity.this, "Заполните все поля");
                    return;
                }

                login(mLoginInput.getText().toString(), mPasswordInput.getText().toString());
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });
    }

    private boolean validateAllFields() {
        return !(mLoginInput.getText().toString().isEmpty()
                || mPasswordInput.getText().toString().isEmpty());
    }

    private void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void showProgressDialog(boolean show) {
        if (show) {
            mProgressDialog.show();
        } else {
            mProgressDialog.hide();
        }
    }

    public void login(final String login, final String password) {
        showProgressDialog(true);
        Api.getInstance().login(login, password)
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
