package com.do79.SxuMiro.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.do79.SxuMiro.base.BaseFragment;


import java.util.List;

public class VPFragmentAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mListFragments = null;

    public VPFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        mListFragments = fragments;
    }

    @Override
    public int getCount() {
        return null != mListFragments ? mListFragments.size() : 0;
    }

    @Override
    public Fragment getItem(int index) {
        if (mListFragments != null && index > -1 && index < mListFragments.size()) {
            return mListFragments.get(index);
        } else {
            return null;
        }
    }

}
