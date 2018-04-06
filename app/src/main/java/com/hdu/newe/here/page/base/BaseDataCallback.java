package com.hdu.newe.here.page.base;

/**
 *
 * @author pope
 * @date 2018/4/6
 */

public interface BaseDataCallback {

    void onStartGetData();

    void onGetSuccess();

    void onGetFailed(String errorMsg);

}
