package com.do79.SxuMiro.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.do79.SxuMiro.R;
import com.do79.SxuMiro.adapter.VPFragmentAdapter;
import com.do79.SxuMiro.base.Application;
import com.do79.SxuMiro.base.BaseActivity;
import com.do79.SxuMiro.base.BaseFragment;
import com.do79.SxuMiro.model.NetWorkEvent;
import com.do79.SxuMiro.ui.fragment.FreshNewsFragment;
import com.do79.SxuMiro.ui.fragment.HomeFragment;
import com.do79.SxuMiro.ui.fragment.MyHomeFragment;
import com.do79.SxuMiro.ui.fragment.SchoolActivitiesFragment;
import com.do79.SxuMiro.ui.fragment.TodoFragment;
import com.do79.SxuMiro.utils.NetWorkUtil;
import com.do79.SxuMiro.utils.ShareUtil;
import com.do79.SxuMiro.utils.ShowToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

import com.do79.SxuMiro.utils.logger.Logger;
import com.do79.SxuMiro.view.XViewPager;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @Bind(R.id.MainToolbar)
    Toolbar mToolbar;
    @Bind(R.id.home_container)
    XViewPager mViewPager;
    @Bind(R.id.fab)
    FloatingActionMenu fab;
    @Bind(R.id.fab1)
    FloatingActionButton fab1;
    @Bind(R.id.fab2)
    FloatingActionButton fab2;
    @Bind(R.id.fab3)
    FloatingActionButton fab3;
    private BroadcastReceiver netStateReceiver;
    private MaterialDialog noNetWorkDialog;
    private long exitTime;
    private Fragment currentFragment;
    private Drawer appDrawer;
    MyHomeFragment myHomeFragment;
    HomeFragment homeFragment;
    private AccountHeader headerResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initViewPager();

    }

    private void initViewPager() {
        mViewPager.setEnableScroll(false);
        List<BaseFragment> fragments = getPagerFragments();
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setAdapter(new VPFragmentAdapter(getSupportFragmentManager(), fragments));
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        if (Build.VERSION.SDK_INT >= 21) {
            getSupportActionBar().setElevation(0);
            mViewPager.setElevation(0);
        }
        initDrawer();
        initFab();

    }

    private void initDrawer() {
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.nav_header_bg)

                .withSelectionListEnabled(false)
                .withOnAccountHeaderSelectionViewClickListener(new AccountHeader.OnAccountHeaderSelectionViewClickListener() {
                    @Override
                    public boolean onClick(View view, IProfile profile) {
//                        mainViewPresenter.toggleUserNameClick();
                        return false;
                    }
                })
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        startActivity(new Intent(MainActivity.this, LoginOptionsActivity.class));
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        startActivity(new Intent(MainActivity.this, LoginOptionsActivity.class));
                        return false;
                    }
                })
                .build();


        appDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withFullscreen(true)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIdentifier(R.string.home).withName(R.string.home).withIcon(FontAwesome.Icon.faw_home),
                        new PrimaryDrawerItem().withIdentifier(R.string.news).withName(R.string.news).withIcon(FontAwesome.Icon.faw_newspaper_o),
                        new PrimaryDrawerItem().withIdentifier(R.string.todo).withName(R.string.todo).withIcon(FontAwesome.Icon.faw_calendar_check_o),
                        new PrimaryDrawerItem().withIdentifier(R.string.my_collection).withName(R.string.my_collection).withIcon(FontAwesome.Icon.faw_child),
                        new PrimaryDrawerItem().withIdentifier(R.string.timetable).withName(R.string.timetable).withIcon(FontAwesome.Icon.faw_table).withSelectable(false),
                        new PrimaryDrawerItem().withIdentifier(R.string.school_activities).withName(R.string.school_activities).withIcon(FontAwesome.Icon.faw_tags),
                        new PrimaryDrawerItem().withIdentifier(R.string.chat).withName(R.string.chat).withIcon(FontAwesome.Icon.faw_comment_o),
                        new DividerDrawerItem(),

                        new SecondaryDrawerItem().withIdentifier(R.string.settings).withName(R.string.settings).withSelectable(false),
                        new SecondaryDrawerItem().withIdentifier(R.string.feedback).withName(R.string.feedback).withSelectable(false),
                        new SecondaryDrawerItem().withIdentifier(R.string.share).withName(R.string.share).withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // position start with 1

                        switch ((int) drawerItem.getIdentifier()) {
                            case R.string.home:
                                return switchFragment(position);
                            case R.string.news:
                                return switchFragment(position);
                            case R.string.todo:
                                return switchFragment(position);
                            case R.string.my_collection:
                                return switchFragment(position);
                            case R.string.timetable:
                                return switchFragment(position);
                            case R.string.school_activities:
                                return switchFragment(position);
                            case R.string.chat:
                                return switchFragment(position);
                            //////////////////////////////////////////////////////////////////////////////////
                            case R.string.settings:
                                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                                return true;
                            case R.string.feedback:

                                return true;
                            case R.string.share:
                                ShareUtil.shareText(MainActivity.this,  getResources().getString(R.string.share_sxumiro));
                                return true;
                            default:
                                //0 header 4 divider
                                return true;
                        }
                    }
                })
                .withShowDrawerOnFirstLaunch(true)
                .build();

        fillAccountHeader();
        Logger.d("主页onCreate结束");
    }

    public void fillAccountHeader() {
//        if (userDetail != null) {
//            ArrayList pro = headerResult.getProfiles();
//            if (pro != null && pro.size() > 0)
//                for (int i = 0; i < pro.size(); i++) {
//                    headerResult.removeProfile(0);
//                }
//            ProfileDrawerItem profileDrawerItem = new ProfileDrawerItem();
//            profileDrawerItem.withEmail(userDetail.getUsername());
//            if (userDetail.getAvatar_large() == null) {
//                profileDrawerItem.withIcon(R.mipmap.empty_avatar_user);
//            } else {
//                profileDrawerItem.withIcon(userDetail.getAvatar_large());
//            }
//            headerResult.addProfiles(profileDrawerItem);
//
//            appDrawer.addItem(new SecondaryDrawerItem().withName(R.string.logout).withIdentifier(R.string.logout));
//        } else {
//            ArrayList pro = headerResult.getProfiles();
//            if (pro != null && pro.size() > 0)
//                for (int i = 0; i < pro.size(); i++) {
//                    headerResult.removeProfile(0);
//                }
            headerResult.addProfiles(
                    new ProfileDrawerItem()
                            .withEmail("登陆.注册")
                            .withIcon(R.mipmap.empty_avatar_user)
            );
//        }
    }

    @Override
    protected void initData() {

        netStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(
                        ConnectivityManager.CONNECTIVITY_ACTION)) {
                    if (NetWorkUtil.isNetWorkConnected(MainActivity.this)) {
                        EventBus.getDefault().post(new NetWorkEvent(NetWorkEvent.AVAILABLE));
                    } else {
                        EventBus.getDefault().post(new NetWorkEvent(NetWorkEvent.UNAVAILABLE));
                    }
                }
            }
        };

        registerReceiver(netStateReceiver, new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(NetWorkEvent event) {

        if (event.getType() == NetWorkEvent.UNAVAILABLE) {

            if (noNetWorkDialog == null) {
                noNetWorkDialog = new MaterialDialog.Builder(MainActivity.this)
                        .title("无网络连接")
                        .content("去开启网络?")
                        .positiveText("是")
                        .backgroundColor(getResources().getColor(Application.COLOR_OF_DIALOG))
                        .contentColor(Application.COLOR_OF_DIALOG_CONTENT)
                        .positiveColor(Application.COLOR_OF_DIALOG_CONTENT)
                        .negativeColor(Application.COLOR_OF_DIALOG_CONTENT)
                        .titleColor(Application.COLOR_OF_DIALOG_CONTENT)
                        .negativeText("否")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                Intent intent = new Intent(
                                        Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(intent);
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                            }
                        })
                        .cancelable(false)
                        .build();
            }
            if (!noNetWorkDialog.isShowing()) {
                noNetWorkDialog.show();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netStateReceiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (appDrawer != null && appDrawer.isDrawerOpen()) {
                appDrawer.closeDrawer();
            }
            else if ((System.currentTimeMillis() - exitTime) > 2000 && !(appDrawer != null && appDrawer.isDrawerOpen())) {
                ShowToast.Short("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Drawer Method
    ///////////////////////////////////////////////////////////////////////////


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.aboutMeMenuItem:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void initFab() {
        fab.hideMenu(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fab.showMenu(true);
                fab.setMenuButtonShowAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.show_from_bottom));
                fab.setMenuButtonHideAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.hide_to_bottom));
            }
        }, 300);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        final FloatingActionMenu menu1 = (FloatingActionMenu) findViewById(R.id.fab);
        menu1.setClosedOnTouchOutside(true);

        fab1.setOnClickListener(clickListener);
        fab2.setOnClickListener(clickListener);
        fab3.setOnClickListener(clickListener);


    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            String text = "";
            switch (v.getId()) {
                case R.id.fab1:
                    text = fab1.getLabelText();
                    break;
                case R.id.fab2:
                    text = fab2.getLabelText();
//                    startActivity(new Intent(MainActivity.this, ListViewMultiChartActivity.class));
                    break;
                case R.id.fab3:
                    text = "课程表";
                    intent.setClass(MainActivity.this, TimetableActivity.class);
                    startActivity(intent);
                    break;
            }
            Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
        }
    };


    private boolean switchFragment(int id) {

        switch (id) {
            case 1:
                getSupportActionBar().setTitle(R.string.app_name);
                mViewPager.setCurrentItem(0, false);
                break;
            case 2:
                getSupportActionBar().setTitle(R.string.news);
                mViewPager.setCurrentItem(1, false);
                break;
            case 3:
                getSupportActionBar().setTitle(R.string.todo);
                mViewPager.setCurrentItem(3, false);
                break;
            case 4:
                getSupportActionBar().setTitle(R.string.my_collection);
                mViewPager.setCurrentItem(2, false);
                break;
            case 5:
                getSupportActionBar().setTitle(R.string.timetable);
                mViewPager.setCurrentItem(3, false);
                break;
            case 6:
                getSupportActionBar().setTitle(R.string.school_activities);
                mViewPager.setCurrentItem(4, false);
                break;
            case 7:
                getSupportActionBar().setTitle(R.string.chat);
                mViewPager.setCurrentItem(3, false);
                break;

            default:
                break;

        }
//        mViewPager.setCurrentItem();
        return false;

    }

    public List<BaseFragment> getPagerFragments() {
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new FreshNewsFragment());
        fragments.add(new MyHomeFragment());
        fragments.add(new TodoFragment());
        fragments.add(new SchoolActivitiesFragment());
        return fragments;
    }
}
