package com.hdu.newe.here.utils;

import android.app.Application;
import android.content.Context;

/**
 * 自定义 Application
 * 用于获取全局 {@link Context}
 */

public class MyApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
