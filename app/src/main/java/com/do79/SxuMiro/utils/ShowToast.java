package com.do79.SxuMiro.utils;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.do79.SxuMiro.base.Application;

public class ShowToast {

    public static void Short(@NonNull CharSequence sequence) {
        Toast.makeText(Application.getContext(), sequence, Toast.LENGTH_SHORT).show();
    }

    public static void Long(@NonNull CharSequence sequence) {
        Toast.makeText(Application.getContext(), sequence, Toast.LENGTH_SHORT).show();
    }

}
