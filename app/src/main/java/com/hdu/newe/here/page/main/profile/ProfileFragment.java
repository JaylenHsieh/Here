package com.hdu.newe.here.page.main.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdu.newe.here.R;
import com.hdu.newe.here.page.base.BaseFragment;
import com.jonnyhsia.uilib.widget.BottomNavigation;

/**
 * Created by Jaylen Hsieh on 2017/11/19.
 */

public class ProfileFragment extends BaseFragment implements ProfileContract.View{

    ProfileContract.Presenter mPresenter;

    @Override
    public void bindPresenter(ProfileContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
