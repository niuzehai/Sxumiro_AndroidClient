package com.do79.SxuMiro.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;

import com.do79.greendao.DaoMaster;
import com.do79.greendao.DaoSession;
import com.do79.SxuMiro.BuildConfig;
import com.do79.SxuMiro.R;
import com.do79.SxuMiro.cache.BaseCache;
import com.do79.SxuMiro.utils.StrictModeUtil;
import com.do79.SxuMiro.utils.logger.LogLevel;
import com.do79.SxuMiro.utils.logger.Logger;
import com.do79.SxuMiro.view.imageloader.ImageLoadProxy;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.Map;

public class Application extends android.app.Application {

    public static int COLOR_OF_DIALOG = R.color.primary;
    public static int COLOR_OF_DIALOG_CONTENT = Color.WHITE;

    private static Context mContext;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    private RefWatcher refWatcher;
///////////////////////

    private static final boolean IS_ENABLED = true;


    private String getClassName(Object o) {
        Class c = o.getClass();
        while(c.isAnonymousClass()) {
            c = c.getEnclosingClass();
        }
        return c.getSimpleName();

    }


    ////////////////////////
    @Override
    public void onCreate() {
        StrictModeUtil.init();
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        mContext = this;
        ImageLoadProxy.initImageLoader(this);

        if (BuildConfig.DEBUG) {
            Logger.init().hideThreadInfo().setMethodCount(1).setLogLevel(LogLevel.FULL);
        }
    }

    public static Context getContext() {
        return mContext;
    }

    public static RefWatcher getRefWatcher(Context context) {
        Application application = (Application) context.getApplicationContext();
        return application.refWatcher;
    }


    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, BaseCache.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

}