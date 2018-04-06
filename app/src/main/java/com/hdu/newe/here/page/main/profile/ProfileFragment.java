package com.hdu.newe.here.page.main.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.profile.Bean.FunctionBean;
import com.hdu.newe.here.page.base.BaseFragment;
import com.hdu.newe.here.page.main.profile.adapter.FunctionAdapter;
import com.hdu.newe.here.utils.MultiItemDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaylen Hsieh on 2017/11/19.
 */

public class ProfileFragment extends BaseFragment<ProfileContract.Presenter>
        implements ProfileContract.View{

    private List<FunctionBean> mFunctionBeanList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initList();
        Context mContext = getContext();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        FunctionAdapter adapter = new FunctionAdapter(mFunctionBeanList,mContext);
        MultiItemDivider itemDivider = new MultiItemDivider(mContext, MultiItemDivider.Companion.getVERTICAL_LIST());

        //最后一个item下没有分割线
        itemDivider.setDividerMode(MultiItemDivider.Companion.getINSIDE());
        //收进padding
        itemDivider.clipToChildPadding(true);
        recyclerView.addItemDecoration(itemDivider);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initList(){
        FunctionBean information = new FunctionBean(R.drawable.ic_people,"个人信息");
        mFunctionBeanList.add(information);
        FunctionBean changePhone = new FunctionBean(R.drawable.ic_phone,"更换手机");
        mFunctionBeanList.add(changePhone);
        FunctionBean checkVersion = new FunctionBean(R.drawable.ic_check,"版本检测");
        mFunctionBeanList.add(checkVersion);
        FunctionBean feedBack = new FunctionBean(R.drawable.ic_feedback,"意见反馈");
        mFunctionBeanList.add(feedBack);
        FunctionBean aboutUs = new FunctionBean(R.drawable.ic_about,"关于我们");
        mFunctionBeanList.add(aboutUs);
        FunctionBean logout = new FunctionBean(R.drawable.ic_logout,"退出账号");
        mFunctionBeanList.add(logout);
    }
}
