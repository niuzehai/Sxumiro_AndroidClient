package com.do79.SxuMiro.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.do79.SxuMiro.R;
import com.do79.SxuMiro.base.ConstantString;
import com.do79.SxuMiro.cache.FreshNewsCache;
import com.do79.SxuMiro.callback.LoadFinishCallBack;
import com.do79.SxuMiro.callback.LoadResultCallBack;
import com.do79.SxuMiro.model.school_activities.SchoolActivities;
import com.do79.SxuMiro.net.JSONParser;
import com.do79.SxuMiro.net.Request4FreshNews;
import com.do79.SxuMiro.net.Request4SchoolActivities;
import com.do79.SxuMiro.net.RequestManager;
import com.do79.SxuMiro.ui.SchoolActivitiesDetailActivity;
import com.do79.SxuMiro.ui.fragment.SchoolActivitiesFragment;
import com.do79.SxuMiro.utils.NetWorkUtil;
import com.do79.SxuMiro.utils.ShareUtil;
import com.do79.SxuMiro.utils.ShowToast;
import com.do79.SxuMiro.view.imageloader.ImageLoadProxy;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SchoolActivitiesAdapter extends RecyclerView.Adapter<SchoolActivitiesAdapter.ViewHolder> {

    private int page;
    private int lastPosition = -1;
    private Activity mActivity;
    private DisplayImageOptions options;
    private ArrayList<SchoolActivities> mSchoolActivities;
    private LoadFinishCallBack mLoadFinisCallBack;
    private LoadResultCallBack mLoadResultCallBack;

    public SchoolActivitiesAdapter(Activity activity, LoadFinishCallBack loadFinisCallBack, LoadResultCallBack loadResultCallBack) {
        this.mActivity = activity;
        this.mLoadFinisCallBack = loadFinisCallBack;
        this.mLoadResultCallBack = loadResultCallBack;
        mSchoolActivities = new ArrayList<>();

        int loadingResource = R.drawable.ic_loading_large;
        options = ImageLoadProxy.getOptions4PictureList(loadingResource);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R
                    .anim.item_bottom_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {

            holder.card.clearAnimation();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_school_activities;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final SchoolActivities freshNews = mSchoolActivities.get(position);

        ImageLoadProxy.displayImage(freshNews.getCustomFields().getThumb_m(), holder.img, options);
        holder.tv_name_of_activities.setText(freshNews.getTitle());
        holder.tv_number_of_participants.setText(freshNews.getAuthor().getName() + "人参加");
        holder.tv_number_of_see.setText( freshNews.getCustomFields().getViews() + "人浏览");


            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toDetailActivity(position);
                }
            });

            setAnimation(holder.card, position);
    }

    private void toDetailActivity(int position) {
        Intent intent = new Intent(mActivity, SchoolActivitiesDetailActivity.class);
        intent.putExtra(SchoolActivitiesDetailActivity.DATA_FRESH_NEWS, mSchoolActivities);
        intent.putExtra(SchoolActivitiesDetailActivity.DATA_POSITION, position);
        mActivity.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mSchoolActivities.size();
    }

    public void loadFirst() {
        page = 1;
        loadDataByNetworkType();
    }

    public void loadNextPage() {
        page++;
        loadDataByNetworkType();
    }

    private void loadDataByNetworkType() {

        if (NetWorkUtil.isNetWorkConnected(mActivity)) {
            RequestManager.addRequest(new Request4SchoolActivities(SchoolActivities.getUrlFreshNews(page),
                    new Response.Listener<ArrayList<SchoolActivities>>() {
                        @Override
                        public void onResponse(ArrayList<SchoolActivities> response) {

                            mLoadResultCallBack.onSuccess(LoadResultCallBack.SUCCESS_OK, null);
                            mLoadFinisCallBack.loadFinish(null);

                            if (page == 1) {
                                mSchoolActivities.clear();
                                FreshNewsCache.getInstance(mActivity).clearAllCache();
                            }

                            mSchoolActivities.addAll(response);
                            notifyDataSetChanged();

                            FreshNewsCache.getInstance(mActivity).addResultCache(JSONParser.toString(response),
                                    page);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mLoadResultCallBack.onError(LoadResultCallBack.ERROR_NET, error.getMessage());
                    mLoadFinisCallBack.loadFinish(null);
                }
            }), mActivity);
        } else {
            mLoadResultCallBack.onSuccess(LoadResultCallBack.SUCCESS_OK, null);
            mLoadFinisCallBack.loadFinish(null);

            if (page == 1) {
                mSchoolActivities.clear();
                ShowToast.Short(ConstantString.LOAD_NO_NETWORK);
            }
//
//            mSchoolActivities.addAll(FreshNewsCache.getInstance(mActivity).getCacheByPage(page));
            notifyDataSetChanged();
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_number_of_participants)
        TextView tv_number_of_participants;
        @Bind(R.id.tv_number_of_see)
        TextView tv_number_of_see;
        @Bind(R.id.tv_name_of_activities)
        TextView tv_name_of_activities;
        @Bind(R.id.img)
        ImageView img;
        @Bind(R.id.card)
        CardView card;

        public ViewHolder(View contentView) {
            super(contentView);
            ButterKnife.bind(this,contentView);
        }
    }

}