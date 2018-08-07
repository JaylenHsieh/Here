package com.hdu.newe.here.page.main.signin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hdu.newe.here.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author pope
 */
public class NewSubjectActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private CreateSubjectFragment createSubjectFragment;
    private JoinSubjectFragment joinSubjectFragment;
    private SharedPreferences sharedPreferences;

    private String subjectName;
    private String subjectCode;

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_subject);
        ButterKnife.bind(this);

        //初始化Toolbar
        initToolbar();

        //加载Fragment
        loadFragment();

    }

    /**
     * 初始化Toolbar方法
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 加载Fragment方法 目前暂定教师用户加载“创建教学班”，学生用户加载“加入教学班”。
     */
    private void loadFragment() {
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (sharedPreferences.getBoolean("isTeacher", false)) {
            if (createSubjectFragment == null) {
                createSubjectFragment = CreateSubjectFragment.newInstance();
            }
            transaction.replace(R.id.container_new_subject, createSubjectFragment);
            toolbar.setTitle("创建教学班");
        } else {
            if (joinSubjectFragment == null) {
                joinSubjectFragment = JoinSubjectFragment.newInstance();
            }
            transaction.replace(R.id.container_new_subject, new JoinSubjectFragment());
            toolbar.setTitle("加入教学班");
        }
        transaction.commit();
    }

    /**
     * 启动ResultFragment方法 用以展示结果
     * 由于不同用户在当前活动展示的Fragment不同，所以在这里进行判断后隐藏对应的Fragment
     */
    public void lunchResultFragment() {

        FragmentManager fragmentManager1 = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager1.beginTransaction();
        if (sharedPreferences.getBoolean("isTeacher", false)) {
            transaction.hide(createSubjectFragment)
                    .replace(R.id.container_new_subject,new ResultFragment());
            transaction.commit();
        } else {
            transaction.hide(joinSubjectFragment)
                    .replace(R.id.container_new_subject,new ResultFragment());
            transaction.commit();
        }
    }

}
