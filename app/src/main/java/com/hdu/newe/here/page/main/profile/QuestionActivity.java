package com.hdu.newe.here.page.main.profile;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.profile.Bean.QuestionBean;
import com.hdu.newe.here.page.main.profile.adapter.QuestionAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Jaylen Hsieh on 2018/04/26.
 */
public class QuestionActivity extends AppCompatActivity {

    private List<QuestionBean> mQuestionList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        RecyclerView recyclerView = findViewById(R.id.questionRecList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        QuestionAdapter questionAdapter = new QuestionAdapter(mQuestionList);
        recyclerView.setAdapter(questionAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BmobQuery<QuestionBean> questionQuery = new BmobQuery<QuestionBean>();
        questionQuery.findObjects(new FindListener<QuestionBean>() {
            @Override
            public void done(List<QuestionBean> list, BmobException e) {
                if (e == null){
                    int n = list.size();
                    for (int i = 0; i<n;i++){
                        mQuestionList.add(list.get(i));
                    }
                }
            }
        });

    }
}
