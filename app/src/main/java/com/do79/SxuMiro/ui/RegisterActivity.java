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
import com.do79.SxuMiro.utils.logger.Logger;
import com.mikepenz.materialize.MaterializeBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegisterActivity extends BaseActivity {
    @Bind(R.id.register_account)
    EditText register_account;

    @Bind(R.id.register_account_InputLayout)
    TextInputLayout register_account_InputLayout;

    @Bind(R.id.register_account_pwd)
    EditText register_account_pwd;

    @Bind(R.id.register_account_pwd_InputLayout)
    TextInputLayout register_account_pwd_InputLayout;

    @Bind(R.id.bt_register)
    Button bt_register;

    @Bind(R.id.register_account_pwd_again)
    EditText register_account_pwd_again;

    @Bind(R.id.register_account_pwd_again_InputLayout)
    TextInputLayout register_account_pwd_again_InputLayout;

    private static Boolean accountState = false;
    private static Boolean pwdAgainState = false;
    private static Boolean pwdState = false;
    public static RegisterActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_by_phone);
        new MaterializeBuilder().withActivity(this).build();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.register_by_phone);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        instance = this;
        initView();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        register_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    register_account_InputLayout.setError("");
                    accountState = true;
                    bt_register.setEnabled(accountState && pwdState && pwdAgainState);
                } else {
                    register_account_InputLayout.setError("手机号不正确");
                    accountState = false;
                    bt_register.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        register_account_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6 && s.length() < 20) {
                    register_account_pwd_InputLayout.setError("");
                    pwdState = true;
                    bt_register.setEnabled(accountState && pwdState && pwdAgainState);
                } else {
                    register_account_pwd_InputLayout.setError("密码格式不正确");
                    pwdState = false;
                    bt_register.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        register_account_pwd_again.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (register_account_pwd.getText().toString().equals(register_account_pwd_again.getText().toString())) {
                    pwdAgainState = true;
                    register_account_pwd_again_InputLayout.setError("");
                    bt_register.setEnabled(accountState && pwdState && pwdAgainState);
                } else {
                    register_account_pwd_again_InputLayout.setError("两次密码输入不一致");
                    pwdAgainState = false;
                    bt_register.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_register.setEnabled(false);
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                sweetAlertDialog.setTitleText("正在注册中...");
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.show();

                sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                sweetAlertDialog.setTitleText("注册成功");

                sweetAlertDialog.dismissWithAnimation();
                bt_register.setEnabled(true);

                startActivity(new Intent(RegisterActivity.this, BingingStudentIdActivity.class));
            }
        });
    }

    @Override
    protected void initData() {

    }

    public void SkipLogin(View v) {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
