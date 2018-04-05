package com.hdu.newe.here.page.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 *
 * @author Jaylen Hsieh
 * @date 2017/11/19
 */

public class BaseFragment<T extends BasePresenter> extends Fragment {

    protected ProgressBar mProgressBar;

    public T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.onCreate();
    }

    public void showMessage(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    public void showProgressBar(String text) {

    }

    public void hideProgressBar() {

    }

    public void bindPresenter(T presenter) {
        mPresenter = presenter;
    }

}
