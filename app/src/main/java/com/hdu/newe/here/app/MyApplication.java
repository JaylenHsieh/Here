package com.hdu.newe.here.app;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.hdu.newe.here.biz.ModelFactory;

import cn.bmob.v3.Bmob;

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

        // Bmob
        Bmob.initialize(this, "5ba25b0b532dddb1661a151f38b84349");

        //初始化百度地图相关SDK
        SDKInitializer.initialize(this);

        ModelFactory.init(this);
    }

}
