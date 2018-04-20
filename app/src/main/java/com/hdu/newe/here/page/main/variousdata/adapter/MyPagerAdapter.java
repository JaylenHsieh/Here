package com.hdu.newe.here.page.main.variousdata.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 *
 * @author pope
 * @date 2018/4/5
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> titleList;

    public MyPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList,List<String> titleList) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return titleList.get(position);
    }
}
