package com.do79.SxuMiro.callback;

public interface LoadItemResultCallBack {

    int SUCCESS_OK = 1001;
    int SUCCESS_NONE = 1002;
    int ERROR_NET = 1003;
    int ON_DOING=1004;

    void ItemOnSuccess(int result, Object object);
    void ItemOnClear();
}
