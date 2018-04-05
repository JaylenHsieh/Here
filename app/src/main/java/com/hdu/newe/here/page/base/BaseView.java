package com.hdu.newe.here.page.base;

/**
 *
 * @author Jaylen Hsieh
 * @date 2017/11/19
 */

public interface BaseView<T> {

    void showProgressBar(String text);

    void hideProgressBar();

    void bindPresenter(T presenter);

    void showMessage(String text);
}
