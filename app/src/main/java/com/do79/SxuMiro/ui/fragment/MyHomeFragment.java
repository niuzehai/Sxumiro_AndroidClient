package com.do79.SxuMiro.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.do79.SxuMiro.R;
import com.do79.SxuMiro.base.BaseFragment;
import com.do79.SxuMiro.ui.ListViewMultiChartFragment;
import com.do79.SxuMiro.view.XViewPager;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyHomeFragment extends BaseFragment {
    @Bind(R.id.fragment_home_pager)
    XViewPager mViewPager;

    @Bind(R.id.fragment_personal_home_tab_smart)
    SmartTabLayout mSmartTabLayout;
    private ScoreFragment scoreFragment;
    private WatchFragment watchFragment;
    List<Fragment> fragments = null;
    List<String> titles = null;
    public MyHomeFragment() {

    }
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
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
        View view = inflater.inflate(R.layout.fragment_personal_home, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.fragment_personal_home_pager);
        SmartTabLayout smartTabLayout = (SmartTabLayout) view.findViewById(R.id.fragment_personal_home_tab_smart);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getActivity().getSupportFragmentManager(), FragmentPagerItems.with(getContext())
                .add("个人成绩", ScoreFragment.class)
                .add("图表分析", ListViewMultiChartFragment.class)
                .create());
        ButterKnife.bind(view);
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
        return view;
    }

}