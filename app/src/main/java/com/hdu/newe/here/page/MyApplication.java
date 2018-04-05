package com.hdu.newe.here.page;

import android.app.Application;

/**
 *
 * @author pope
 * @date 2018/4/5
 */

public class MyApplication extends Application {

    public static Application INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
        //TODO 各个页面的Model层在此初始化
    }
}
