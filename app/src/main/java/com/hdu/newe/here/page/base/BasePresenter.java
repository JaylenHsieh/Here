package com.hdu.newe.here.page.base;

/**
 *
 * @author Jaylen Hsieh
 * @date 2017/11/19
 */

public interface BasePresenter {

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
