package com.do79.SxuMiro.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.volley.Request;
import com.do79.SxuMiro.BuildConfig;
import com.do79.SxuMiro.net.RequestManager;
import com.do79.SxuMiro.utils.logger.LogLevel;
import com.do79.SxuMiro.utils.logger.Logger;
import com.do79.SxuMiro.view.imageloader.ImageLoadProxy;

public class BaseFragment extends Fragment implements ConstantString {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Logger.init(getClass().getSimpleName()).setLogLevel(LogLevel.FULL).hideThreadInfo();
        } else {
            Logger.init(getClass().getSimpleName()).setLogLevel(LogLevel.NONE).hideThreadInfo();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Application.getRefWatcher(getActivity()).watch(this);
        RequestManager.cancelAll(this);
        ImageLoadProxy.getImageLoader().clearMemoryCache();
    }

    protected void executeRequest(Request request) {
        RequestManager.addRequest(request, this);
    }
}
