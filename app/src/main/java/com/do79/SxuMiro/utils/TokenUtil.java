package com.do79.SxuMiro.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.do79.SxuMiro.base.BaseActivity;

public class TokenUtil {
    public static void saveToken(String token, Activity mActivity) {
        SharedPreferences sp = mActivity.getSharedPreferences("UserState", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Token", token);
        editor.commit();
    }

    public static String readToken(Activity mActivity) {
        SharedPreferences preferences = mActivity.getSharedPreferences("UserState", Context.MODE_PRIVATE);
        String Token = preferences.getString("Token", "0");
        return Token;
    }

    public static boolean IsLogin(Activity mActivity){
        SharedPreferences preferences = mActivity.getSharedPreferences("UserState", Context.MODE_PRIVATE);
        String Token = preferences.getString("Token","0");
        if(Token=="0"){
            return false;
        }else {
            return true;
        }

    }

}