package com.hdu.newe.here.page;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

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
        //初始化百度地图相关SDK
        SDKInitializer.initialize(this);
        //TODO 各个页面的Model层在此初始化
    }

}
