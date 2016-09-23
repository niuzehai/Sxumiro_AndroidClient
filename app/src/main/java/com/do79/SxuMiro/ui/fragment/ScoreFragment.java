package com.do79.SxuMiro.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.do79.SxuMiro.R;
import com.do79.SxuMiro.adapter.ScoreAdapter;
import com.do79.SxuMiro.base.BaseFragment;
import com.do79.SxuMiro.base.ConstantString;
import com.do79.SxuMiro.callback.LoadItemResultCallBack;
import com.do79.SxuMiro.callback.LoadResultCallBack;
import com.do79.SxuMiro.model.score.StudentScore;
import com.do79.SxuMiro.utils.ShowToast;
import com.do79.SxuMiro.utils.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;


public class ScoreFragment extends BaseFragment implements LoadResultCallBack, LoadItemResultCallBack {
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.tv_score_date)
    TextView tv_score_date;
    @Bind(R.id.tv_score_gpa)
    TextView tv_score_gpa;
    @Bind(R.id.tv_score_name_id)
    TextView tv_score_name_id;
    @Bind(R.id.score_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.spinner_item)
    Spinner spinner_item;
    private List<String> itemList = new ArrayList<String>();
    private ScoreAdapter mScoreAdapter;
    private ArrayAdapter<String> mArrayAdapter;
    private Activity mActivity;
    protected Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_score, container, false);
        mActivity = getActivity();
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setHasFixedSize(false);
        initView();
        initData();
    }

    protected void initView() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mScoreAdapter.loadDataByNetworkType(spinner_item.getSelectedItem().toString());
//                mScoreAdapter.loadDataBySpinnarType(spinner_item.getSelectedItem().toString());
                Logger.d(spinner_item.getSelectedItem().toString());
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mScoreAdapter = new ScoreAdapter(mActivity, this, this);
        mRecyclerView.setAdapter(mScoreAdapter);
        mSwipeRefreshLayout.setRefreshing(true);
        mScoreAdapter.loadDataByNetworkType("firstLoad");

    }


    protected void initData() {
        mArrayAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, itemList);
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_item.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mScoreAdapter.loadDataBySpinnarType(mArrayAdapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);//解绑
    }


    private void UpdateUi(StudentScore studentScore) {
        tv_score_date.setText("最后一次更新时间" + studentScore.getDate());
        tv_score_gpa.setText(studentScore.getStatus());
        tv_score_name_id.setText(studentScore.getName() + " " + studentScore.getId());
    }

    @Override
    public void onSuccess(int result, Object object) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError(int code, String msg) {
        ShowToast.Short(ConstantString.LOAD_FAILED);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onSuccess(int result, Object object, int status) {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void ItemOnSuccess(int result, Object object) {
        for (int i = mScoreAdapter.getmItem().size() - 1; i >= 0; i--) {
            itemList.add(mScoreAdapter.getmItem().get(i));
        }
//        for (String  item : mScoreAdapter.getmItem()) {
//            itemList.add(item);
//        }
        spinner_item.setAdapter(mArrayAdapter);
        Logger.e(itemList.toString());
        UpdateUi(mScoreAdapter.getStudentScore());
    }

    @Override
    public void ItemOnClear() {
        itemList.clear();
//        mArrayAdapter.clear();
    }

}
