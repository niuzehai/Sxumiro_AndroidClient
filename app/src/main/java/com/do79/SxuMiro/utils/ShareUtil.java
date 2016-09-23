package com.do79.SxuMiro.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.do79.SxuMiro.R;
import com.do79.SxuMiro.base.ConstantString;

import java.io.File;

public class ShareUtil {

    public static void shareText(Activity activity, String shareText) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,
                shareText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(intent, activity.getResources().getString(R
                .string.app_name)));
    }

}
