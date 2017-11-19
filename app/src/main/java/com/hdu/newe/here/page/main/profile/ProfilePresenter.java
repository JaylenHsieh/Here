package com.hdu.newe.here.page.main.profile;

/**
 * Created by Jaylen Hsieh on 2017/11/19.
 */

public class ProfilePresenter implements ProfileContract.Presenter {
    ProfileContract.View mView;

    public ProfilePresenter(ProfileContract.View view) {
        mView = view;
        mView.bindPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }
}
