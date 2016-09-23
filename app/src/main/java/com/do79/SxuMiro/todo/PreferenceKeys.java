package com.do79.SxuMiro.todo;

import android.content.res.Resources;

import com.do79.SxuMiro.R;

public class PreferenceKeys {
    final String night_mode_pref_key;

    public PreferenceKeys(Resources resources){
        night_mode_pref_key = resources.getString(R.string.night_mode_pref_key);
    }
}
