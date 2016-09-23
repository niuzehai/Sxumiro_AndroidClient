package com.do79.SxuMiro.ui;

import android.app.ActivityManager;
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
import com.do79.SxuMiro.utils.TokenUtil;
import com.do79.SxuMiro.utils.logger.Logger;
import com.mikepenz.materialize.MaterializeBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class BingingStudentIdActivity extends BaseActivity{

    @Bind(R.id.sxu_id)
    EditText sxu_id;

    @Bind(R.id.sxu_id_InputLayout)
    TextInputLayout sxu_id_InputLayout;

    @Bind(R.id.sxu_pwd)
    EditText sxu_pwd;

    @Bind(R.id.sxu_pwd_InputLayout)
    TextInputLayout sxu_pwd_InputLayout;

    @Bind(R.id.id_binding_button)
    Button id_binding_button;

    private static Boolean idState = false;
    private static Boolean pwdState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_student_id);
        new MaterializeBuilder().withActivity(this).build();

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.binding_sxu_id);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }
    @Override
    protected void initView() {
        ButterKnife.bind(this);

        sxu_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    sxu_id_InputLayout.setError("");
                    idState = true;
                    id_binding_button.setEnabled(idState && pwdState);
                } else {
                    sxu_id_InputLayout.setError("学号不正确");
                    idState = false;
                    id_binding_button.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sxu_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6 && s.length() < 20) {
                    sxu_pwd_InputLayout.setError("");
                    pwdState = true;
                    id_binding_button.setEnabled(idState && pwdState);
                } else {
                    sxu_pwd_InputLayout.setError("密码不正确");
                    pwdState = false;
                    id_binding_button.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        id_binding_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_binding_button.setEnabled(false);
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(BingingStudentIdActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                sweetAlertDialog.setTitleText("正在绑定中...");
                sweetAlertDialog.setCancelable(true);
                sweetAlertDialog.show();
                id_binding_button.setEnabled(true);
                String Token = "g9e7avs6g79a46v";
                TokenUtil.saveToken(Token, BingingStudentIdActivity.this);
                Logger.e(TokenUtil.readToken(BingingStudentIdActivity.this));
                finish();
                LoginActivity.instance.finish();
                LoginOptionsActivity.instance.finish();
                RegisterActivity.instance.finish();
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
