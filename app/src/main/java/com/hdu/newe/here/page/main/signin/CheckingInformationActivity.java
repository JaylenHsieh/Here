package com.hdu.newe.here.page.main.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.user.entity.UserBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author pope
 */
public class CheckingInformationActivity extends AppCompatActivity {

    @BindView(R.id.toolbar2)
    android.support.v7.widget.Toolbar toolbar2;
    private CheckMsgListFragment checkMsgListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking_information);
        ButterKnife.bind(this);

        //获取上一个活动传来的viewCode数据用作判别加载哪个布局,同时获取SignInDataBean的objectId用来查询数据
        Intent intent = getIntent();
        int viewCode = intent.getIntExtra("viewCode", 0);
        //初始化Toolbar
        initToolbar(viewCode);
        String objectId = intent.getStringExtra("objectId");
        if (checkMsgListFragment == null) {
            checkMsgListFragment = CheckMsgListFragment.newInstance(viewCode, objectId);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container_msg_list, checkMsgListFragment);
        transaction.commit();

    }

    public void hideOrShowMsg(UserBean userBean, boolean isAppear) {
        checkMsgListFragment.loadMsg(userBean, isAppear);
    }

    /**
     * 初始化Toolbar方法
     *
     * @param viewCode 加载的视图Code
     */
    private void initToolbar(int viewCode) {
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        switch (viewCode) {
            case 1:
                toolbar2.setTitle("缺勤名单");
                break;
            case 2:
                toolbar2.setTitle("请假名单");
                break;
            case 3:
                toolbar2.setTitle("更改出勤设置");
                break;
            case 4:
                toolbar2.setTitle("出勤抽查");
                break;
            default:
                break;
        }
    }

}
