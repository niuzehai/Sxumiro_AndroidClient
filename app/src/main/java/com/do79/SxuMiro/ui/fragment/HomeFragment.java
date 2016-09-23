package com.do79.SxuMiro.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.do79.SxuMiro.R;
import com.do79.SxuMiro.base.BaseFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment {
    private FreshNewsFragment freshNewsFragment;
    private WatchFragment watchFragment;
    Context context;
    Activity mActivity;

    public HomeFragment() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.fragment_home_pager);
        SmartTabLayout smartTabLayout = (SmartTabLayout) view.findViewById(R.id.fragment_home_tab_smart);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getActivity().getSupportFragmentManager(), FragmentPagerItems.with(getContext())
                .add("首页", WatchFragment.class)
                .add("新闻", FreshNewsFragment.class)
                .create());
        ButterKnife.bind(this, view);
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
        return view;
    }

}