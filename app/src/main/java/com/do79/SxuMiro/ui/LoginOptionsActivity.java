package com.do79.SxuMiro.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.do79.SxuMiro.R;
import com.do79.SxuMiro.base.BaseActivity;

import butterknife.ButterKnife;

public class LoginOptionsActivity extends BaseActivity{
    public static LoginOptionsActivity instance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);
        instance = this;
        initView();
    }

    @Override
    protected void initView() {
//        ButterKnife.bind(this);
//        mToolbar.setTitleTextColor(Color.WHITE);
//        setSupportActionBar(mToolbar);
//        mToolbar.setTitle(R.string.title_activity_setting);
//        mToolbar.setNavigationIcon(R.drawable.ic_actionbar_back);
    }

    @Override
    protected void initData() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    public void SkipLogin(View v){
        finish();
    }
    public void Login(View v){
        startActivity(new Intent(this, LoginActivity.class));
    }
    public void NowLogin(View v){
        startActivity(new Intent(this,LoginActivity.class));
    }
}
