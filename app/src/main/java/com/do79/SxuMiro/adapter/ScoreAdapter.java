package com.do79.SxuMiro.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.do79.SxuMiro.R;
import com.do79.SxuMiro.callback.LoadItemResultCallBack;
import com.do79.SxuMiro.callback.LoadResultCallBack;
import com.do79.SxuMiro.model.score.ScoreDetail;
import com.do79.SxuMiro.model.score.StudentScore;
import com.do79.SxuMiro.model.score.SubjectDetail;
import com.do79.SxuMiro.net.Request4Score;
import com.do79.SxuMiro.net.RequestManager;
import com.do79.SxuMiro.utils.NetWorkUtil;
import com.do79.SxuMiro.utils.UserInfoUtil;
import com.do79.SxuMiro.utils.logger.Logger;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Bind;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {

    private Activity mActivity;
    private ArrayList<SubjectDetail> mSubjectDetail;
    public StudentScore studentScore = null;
    private LoadResultCallBack mLoadResultCallBack;
    private LoadItemResultCallBack mLoadItemResultCallBack;
    private ArrayList<String> mItem;

    public ScoreAdapter(Activity activity, LoadResultCallBack loadResultCallBack, LoadItemResultCallBack loadItemResultCallBack) {
        this.mLoadItemResultCallBack = loadItemResultCallBack;
        this.mLoadResultCallBack = loadResultCallBack;
        this.mActivity = activity;
        mSubjectDetail = new ArrayList<>();
        mItem = new ArrayList<>();
    }

    private void setAnimation(View viewToAnimate) {

        Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R
                .anim.item_bottom_in);
        viewToAnimate.startAnimation(animation);

    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {

        holder.ll_score_item.clearAnimation();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.score_item;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final SubjectDetail subjectDetail = mSubjectDetail.get(position);
        if (subjectDetail.getProperty().equals("0")) {
            holder.tv_score_property.setText("选修");
            holder.tv_score_property.setBackgroundResource(R.drawable.bg_blue_property_btn_selector);
        } else if (subjectDetail.getProperty().equals("1")) {
            holder.tv_score_property.setText("必修");
            holder.tv_score_property.setBackgroundResource(R.drawable.bg_green_property_btn_selector);
        }
        holder.tv_score_course_name.setText(subjectDetail.getCourse_name());
        holder.tv_score.setText(subjectDetail.getScore() + "/" + subjectDetail.getScore_next());
        holder.tv_score_gpa.setText(subjectDetail.getGpa() + "/" + subjectDetail.getCredit_hour());
        setAnimation(holder.ll_score_item);

    }


    @Override
    public int getItemCount() {
        return mSubjectDetail.size();
    }

    public void loadDataByNetworkType(final String item) {
        if (NetWorkUtil.isNetWorkConnected(mActivity)) {
            mLoadResultCallBack.onSuccess(LoadResultCallBack.ON_DOING, null, LoadResultCallBack.ON_DOING);
            RequestManager.addRequest(new Request4Score(StudentScore.URL_STUDENT_SCORE,
                    new Response.Listener<StudentScore>() {
                        @Override
                        public void onResponse(StudentScore response) {
                            mLoadResultCallBack.onSuccess(LoadResultCallBack.SUCCESS_OK, null);
                            studentScore = response;
                            UserInfoUtil.saveInfo(studentScore.getId(), studentScore.getName(),mActivity);
                            mLoadItemResultCallBack.ItemOnClear();
                            mSubjectDetail.clear();
                            mSubjectDetail.clear();
                            mItem.clear();
                            AddItem();
                            if (item.equals("所有学期")) {
                                SelectAllData();
                            } else if (item.equals("firstLoad")) {
                                SelectAllData();
                            } else {
                                SelectDataByItem(item);
                            }
                            mLoadItemResultCallBack.ItemOnSuccess(LoadResultCallBack.SUCCESS_OK, null);
                            notifyDataSetChanged();
                            Logger.d(mSubjectDetail.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mLoadResultCallBack.onError(LoadResultCallBack.ERROR_NET, error.getMessage());
                }
            }), mActivity);
        } else {
            mLoadResultCallBack.onSuccess(LoadResultCallBack.SUCCESS_OK, null);
        }

    }

    public void loadDataBySpinnarType(String item) {
        if (studentScore != null) {
            mSubjectDetail.clear();
            if (item.equals("所有学期")) {
                SelectAllData();
            } else {
                SelectDataByItem(item);
            }
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_score_item)
        LinearLayout ll_score_item;
        @Bind(R.id.tv_score_property)
        TextView tv_score_property;
        @Bind(R.id.tv_score_course_name)
        TextView tv_score_course_name;
        @Bind(R.id.tv_score)
        TextView tv_score;
        @Bind(R.id.tv_score_gpa)
        TextView tv_score_gpa;

        public ViewHolder(View contentView) {
            super(contentView);
            ButterKnife.bind(this, contentView);
        }
    }

    public ArrayList<String> getmItem() {
        return mItem;
    }

    public StudentScore getStudentScore() {
        return studentScore;
    }

    private void SelectAllData() {
        ArrayList<ScoreDetail> mScoreDetail = studentScore.getScore_detail();
        for (ScoreDetail subjectDetail : mScoreDetail) {
            ArrayList<SubjectDetail> mySubjectDetail = subjectDetail.getScore_list();
            for (SubjectDetail subject_detail : mySubjectDetail) {
                mSubjectDetail.add(subject_detail);
            }
        }
    }

    private void SelectDataByItem(String item) {
        ArrayList<ScoreDetail> mScoreDetail = studentScore.getScore_detail();
        for (ScoreDetail subjectDetail : mScoreDetail) {
            if (item == subjectDetail.getItem()) {
                ArrayList<SubjectDetail> mySubjectDetail = subjectDetail.getScore_list();
                for (SubjectDetail subject_detail : mySubjectDetail) {
                    mSubjectDetail.add(subject_detail);
                }
            }
        }
    }

    private void AddItem() {
        ArrayList<ScoreDetail> mScoreDetail = studentScore.getScore_detail();
        for (ScoreDetail subjectDetail : mScoreDetail) {
            mItem.add(subjectDetail.getItem());
        }
    }
}