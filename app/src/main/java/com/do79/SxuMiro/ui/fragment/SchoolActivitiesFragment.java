package com.do79.SxuMiro.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.do79.SxuMiro.R;
import com.do79.SxuMiro.adapter.FreshNewsAdapter;
import com.do79.SxuMiro.adapter.SchoolActivitiesAdapter;
import com.do79.SxuMiro.base.BaseFragment;
import com.do79.SxuMiro.base.ConstantString;
import com.do79.SxuMiro.callback.LoadMoreListener;
import com.do79.SxuMiro.callback.LoadResultCallBack;
import com.do79.SxuMiro.model.school_activities.SchoolActivities;
import com.do79.SxuMiro.utils.ShowToast;
import com.do79.SxuMiro.view.AutoLoadRecyclerView;
import com.victor.loading.rotate.RotateLoading;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SchoolActivitiesFragment extends BaseFragment implements LoadResultCallBack {

    @Bind(R.id.recycler_view)
    AutoLoadRecyclerView mRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.loading)
    RotateLoading loading;


    private SchoolActivitiesAdapter mAdapter;

    public SchoolActivitiesFragment() {

    }
    public static SchoolActivitiesFragment getInstance(SchoolActivities schoolActivities) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATA_FRESH_NEWS, schoolActivities);
        SchoolActivitiesFragment fragment = new SchoolActivitiesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public AutoLoadRecyclerView getRecyclerView(){
        return mRecyclerView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auto_load, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore() {
                mAdapter.loadNextPage();
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.loadFirst();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setOnPauseListenerParams(false, true);

        mAdapter = new SchoolActivitiesAdapter(getActivity(), mRecyclerView, this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.loadFirst();
        loading.start();
    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_refresh, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_refresh) {
//            mSwipeRefreshLayout.setRefreshing(true);
//            mAdapter.loadFirst();
//            return true;
//        }
//        return false;
//    }

    @Override
    public void onSuccess(int result, Object object) {
        loading.stop();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError(int code, String msg) {
        loading.stop();
//        ShowToast.Short(ConstantString.LOAD_FAILED);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onSuccess(int result, Object object, int status) {

    }

}