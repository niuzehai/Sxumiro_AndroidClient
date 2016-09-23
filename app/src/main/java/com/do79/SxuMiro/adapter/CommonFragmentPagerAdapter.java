package com.do79.SxuMiro.adapter;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class CommonFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;

    public CommonFragmentPagerAdapter(FragmentManager fm, @NonNull List<Fragment> fragments1, @NonNull List<String> titles) {
        super(fm);
        this.fragments = fragments1;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getCount() {
            return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
