package com.hdu.newe.here.page.main;

import android.app.Application;

/**
 *
 * @author pope
 * @date 2018/4/5
 */

public class MvpApplication extends Application {

    public static Application INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
        
    }

}
