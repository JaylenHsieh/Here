package com.hdu.newe.here.page.main.variousdata.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 *
 * @author pope
 * @date 2018/4/4
 */

public class VariousDataPagerAdapter extends PagerAdapter {

    private ArrayList<View> viewList;
    private ArrayList<String> titleList;

    public VariousDataPagerAdapter(ArrayList<View> viewList, ArrayList<String> titleList) {
        this.viewList = viewList;
        this.titleList = titleList;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
