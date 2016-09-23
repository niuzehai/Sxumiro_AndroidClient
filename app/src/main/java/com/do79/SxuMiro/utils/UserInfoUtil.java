package com.do79.SxuMiro.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class UserInfoUtil {
    public static void saveInfo(String id, String name ,Activity mActivity) {
        SharedPreferences sp = mActivity.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("id", id);
        editor.putString("name", name);
        editor.commit();
    }

    public static Bundle readToken(Activity mActivity) {
        SharedPreferences preferences = mActivity.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String id = preferences.getString("id", "0");
        String name = preferences.getString("name", "0");
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("name",name);
        return bundle;
    }

}