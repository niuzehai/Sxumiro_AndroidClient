package com.do79.SxuMiro.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.do79.SxuMiro.base.Application;
import com.do79.SxuMiro.ui.AboutActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.do79.SxuMiro.R;
import com.do79.SxuMiro.utils.AppInfoUtil;
import com.do79.SxuMiro.utils.FileUtil;
import com.do79.SxuMiro.utils.ShowToast;

import java.io.File;
import java.text.DecimalFormat;

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    public static final String CLEAR_CACHE = "clear_cache";
    public static final String ABOUT_APP = "about_us";
    public static final String ABOUT_PERMIT = "about_permit";
    public static final String APP_VERSION = "app_version";
    public static final String ENABLE_FRESH_BIG = "enable_fresh_big";

    private Preference clearCache;
    private Preference aboutus;
    private Preference aboutpermit;
    private Preference appVersion;
    private CheckBoxPreference enableBig;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        clearCache = findPreference(CLEAR_CACHE);
        aboutus = findPreference(ABOUT_APP);
        aboutpermit=findPreference(ABOUT_PERMIT);
        appVersion = findPreference(APP_VERSION);
        enableBig = (CheckBoxPreference) findPreference(ENABLE_FRESH_BIG);

        appVersion.setTitle(AppInfoUtil.getVersionName(getActivity()));

        File cacheFile = ImageLoader.getInstance().getDiskCache().getDirectory();
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        clearCache.setSummary("缓存大小：" + decimalFormat.format(FileUtil.getDirSize(cacheFile)) + "M");

        enableBig.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                ShowToast.Short(((Boolean) newValue) ? "已开启大图模式" : "已关闭大图模式");
                return true;
            }
        });

        clearCache.setOnPreferenceClickListener(this);
        aboutus.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        String key = preference.getKey();

        if (key.equals(CLEAR_CACHE)) {
            ImageLoader.getInstance().clearDiskCache();
            ShowToast.Short("清除缓存成功");
            clearCache.setSummary("缓存大小：0.00M");
        }
        else if (key.equals(ABOUT_APP)) {
            startActivity(new Intent(getActivity(), AboutActivity.class));
        }else if (key.equals(ABOUT_APP)) {

            MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                    .title("开放源代码许可")
                    .backgroundColor(getResources().getColor(Application.COLOR_OF_DIALOG))
                    .contentColor(Application.COLOR_OF_DIALOG_CONTENT)
                    .positiveColor(Application.COLOR_OF_DIALOG_CONTENT)
                    .negativeColor(Application.COLOR_OF_DIALOG_CONTENT)
                    .neutralColor(Application.COLOR_OF_DIALOG_CONTENT)
                    .titleColor(Application.COLOR_OF_DIALOG_CONTENT)
                    .content(R.string.permit)
                    .positiveText("确定")
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(" ")));
                            dialog.dismiss();
                        }
                    })
                    .build();
            dialog.show();
        }
        return true;
    }
}
