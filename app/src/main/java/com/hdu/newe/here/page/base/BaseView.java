package com.hdu.newe.here.page.base;

/**
 * Created by Jaylen Hsieh on 2017/11/19.
 */

public interface BaseView<T extends BasePresenter> {
    void bindPresenter(T presenter);

    void showMessage(String text);
}
