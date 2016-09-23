package com.do79.SxuMiro.callback;

public interface LoadResultCallBack {

    int SUCCESS_OK = 1001;
    int SUCCESS_NONE = 1002;
    int ERROR_NET = 1003;
    int ON_DOING=1004;

    void onSuccess(int result, Object object);

    void onError(int code, String msg);

    void onSuccess(int result, Object object,int status);
}
