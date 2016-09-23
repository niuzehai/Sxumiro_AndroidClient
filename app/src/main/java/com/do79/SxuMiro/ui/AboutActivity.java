package com.do79.SxuMiro.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.do79.SxuMiro.BuildConfig;
import com.do79.SxuMiro.R;
import com.do79.SxuMiro.base.BaseActivity;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {
	@Bind(R.id.version)
	TextView tv_version;
	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
		initView();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void initView() {
		ButterKnife.bind(this);
		mToolbar.setTitleTextColor(Color.WHITE);
		setSupportActionBar(mToolbar);
		mToolbar.setNavigationIcon(R.drawable.ic_actionbar_back);
		tv_version.setText("版本号:" + BuildConfig.VERSION_NAME);
	}

	@Override
	protected void initData() {

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
