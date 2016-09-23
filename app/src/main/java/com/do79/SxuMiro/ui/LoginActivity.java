package com.do79.SxuMiro.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.do79.SxuMiro.R;
import com.do79.SxuMiro.base.BaseActivity;
import com.mikepenz.materialize.MaterializeBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends BaseActivity{
    @Bind(R.id.login)
    Button loginButton;

    @Bind(R.id.account_InputLayout)
    TextInputLayout accountInputLayout;

    @Bind(R.id.account)
    EditText account;

    @Bind(R.id.account_pwd_InputLayout)
    TextInputLayout accountPwdInputLayout;

    @Bind(R.id.account_pwd)
    EditText account_pwd;
    public static LoginActivity instance = null;
    private static Boolean accountState = false;
    private static Boolean pwdState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_phone);
        new MaterializeBuilder().withActivity(this).build();

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.login_by_account);
        instance = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }
    @Override
    protected void initView() {
        ButterKnife.bind(this);

        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    accountInputLayout.setError("");
                    accountState = true;
                    loginButton.setEnabled(accountState && pwdState);
                } else {
                    accountInputLayout.setError("手机号不正确");
                    accountState = false;
                    loginButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        account_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6 && s.length() < 20) {
                    accountPwdInputLayout.setError("");
                    pwdState = true;
                    loginButton.setEnabled(accountState && pwdState);
                } else {
                    accountPwdInputLayout.setError("密码不正确");
                    pwdState = false;
                    loginButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setEnabled(false);
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                sweetAlertDialog.setTitleText("正在登陆中...");
                sweetAlertDialog.setCancelable(true);
                sweetAlertDialog.show();
                loginButton.setEnabled(true);
            }
        });
    }

    @Override
    protected void initData() {

    }

    public void Register(View v){
        startActivity(new Intent(this,RegisterActivity.class));
    }
    public void SkipLogin(View v){
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
