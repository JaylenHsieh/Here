package com.hdu.newe.here.page.main.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.hdu.newe.here.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginNoticeFragment extends Fragment {


    @BindView(R.id.checkIsRead1)
    CheckBox checkIsRead1;
    @BindView(R.id.checkIsRead2)
    CheckBox checkIsRead2;
    @BindView(R.id.checkIsRead3)
    CheckBox checkIsRead3;
    Unbinder unbinder;

    public LoginNoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_notice, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 检查是否阅读
     *
     * @return 返回是否阅读
     */
    public boolean checkIsRead(){
        if (checkIsRead1.isChecked()&&checkIsRead2.isChecked()&&checkIsRead3.isChecked()){
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
