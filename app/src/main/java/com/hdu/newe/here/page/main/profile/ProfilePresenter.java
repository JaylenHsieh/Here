package com.hdu.newe.here.page.main.profile;

import com.hdu.newe.here.page.base.BasePresenterImpl;

/**
 * Created by Jaylen Hsieh on 2017/11/19.
 */

public class ProfilePresenter extends BasePresenterImpl implements ProfileContract.Presenter {
    ProfileContract.View mView;

    public ProfilePresenter(ProfileContract.View view) {
        mView = view;
        mView.bindPresenter(this);
    }


}
