package com.hdu.newe.here.page.main.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hdu.newe.here.R;
import com.hdu.newe.here.page.main.profile.adapter.QuestionPagerAdapter;
import com.hdu.newe.here.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jaylen Hsieh on 2018/04/26.
 */
public class QuestionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tv_switcher)
    TextView mSwitcher;

    String[] mSwitcherText;
    QuestionPagerAdapter mPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        UIUtils.transparentStatusBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSwitcherText = new String[]{
                getString(R.string.write_question),
                getString(R.string.read_question)
        };

        setupViewPager();

    }

    private void setupViewPager() {
        mPagerAdapter = new QuestionPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mSwitcher.setText(mSwitcherText[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.toolbar, R.id.tv_switcher})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                break;
            case R.id.tv_switcher:
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(
                            (mViewPager.getCurrentItem() + 1) % 2, true
                    );
                }
                break;
            default:
                break;
        }
    }
}
