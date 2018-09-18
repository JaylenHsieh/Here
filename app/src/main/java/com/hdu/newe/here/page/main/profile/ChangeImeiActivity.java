package com.hdu.newe.here.page.main.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.user.entity.UserBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class ChangeImeiActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tvNumber)
    TextView mTvNumber;
    @BindView(R.id.tvIMEI)
    TextView mTvIMEI;
    @BindView(R.id.editImei)
    EditText mEditImei;
    @BindView(R.id.checkBox)
    CheckBox mCheckBox;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.tvIMEILast)
    TextView mTvIMEILast;
    @BindView(R.id.tvIMEINext)
    TextView mTvIMEINext;
    String objectId;
    Long mImeiTimeLimit;
    UserBean user;
    Calendar mCalendar = Calendar.getInstance();
    /**
     * 设置日期格式
     */
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void setUser(UserBean user) {
        this.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_imei);
        ButterKnife.bind(this);
        objectId = getSharedPreferences("user", Context.MODE_PRIVATE).getString("objId", "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        BmobQuery<UserBean> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(objectId, new QueryListener<UserBean>() {
            @Override
            public void done(UserBean user, BmobException e) {
                if (e == null) {
                    mTvNumber.setText(user.getUserNumber());
                    mTvIMEI.setText(user.getImei());
                    mTvIMEILast.setText(df.format(user.getImeiTimeLimit()));
                    mImeiTimeLimit = user.getImeiTimeLimit() + 60 * 60 * 24 * 180 * 1000L;
                    mTvIMEINext.setText(df.format(mImeiTimeLimit));
                } else {
                    Toast.makeText(ChangeImeiActivity.this, "查询失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("error", e.getMessage());
                }
            }
        });
    }

    @OnClick({R.id.checkBox, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.checkBox:
                if (mCheckBox.isChecked()) {
                    mTvSubmit.setVisibility(View.VISIBLE);
                } else {
                    mTvSubmit.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_submit:
                // TODO 点击提交会闪退
                if (mCalendar.getTimeInMillis() > mImeiTimeLimit) {
                    user.setImei(mEditImei.getText().toString());
                    user.setImeiTimeLimit(mCalendar.getTimeInMillis());
                    user.update(objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ChangeImeiActivity.this, "更改成功，下次更改时间为180天后", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(ChangeImeiActivity.this, "失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    long timeGap = (mImeiTimeLimit - mCalendar.getTimeInMillis()) / 1000 / 60 / 60 / 24;
                    Toast.makeText(this, "你距离下次更换IMEI还有" + timeGap + "天", Toast.LENGTH_SHORT).show();
                }

            default:
                break;
        }
    }


}
