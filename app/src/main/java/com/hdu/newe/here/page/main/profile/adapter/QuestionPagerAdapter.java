package com.hdu.newe.here.page.main.profile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hdu.newe.here.page.main.profile.ReadQuestionFragment;
import com.hdu.newe.here.page.main.profile.WriteQuestionFragment;

/**
 * @author Jaylen Hsieh
 * @date 2018/05/01
 */
public class QuestionPagerAdapter extends FragmentPagerAdapter {

    private Fragment mReadQuestionFragment;
    private Fragment mWriteQuestionFragment;

    public QuestionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            if (mReadQuestionFragment == null) {
                mReadQuestionFragment = ReadQuestionFragment.Companion.newInstance();
            }
            return mReadQuestionFragment;
        } else if (position == 1) {
            if (mWriteQuestionFragment == null) {
                mWriteQuestionFragment = WriteQuestionFragment.newInstance();
            }
            return mWriteQuestionFragment;
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
