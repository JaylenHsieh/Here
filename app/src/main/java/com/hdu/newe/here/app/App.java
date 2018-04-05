package com.hdu.newe.here.app;

import android.app.Application;

/**
 * Created by Jaylen Hsieh on 2018/04/05.
 */
public class App extends Application{
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstance(){
        return instance;
    }
}
