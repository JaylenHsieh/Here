package com.hdu.newe.here.page.main.leaverequest.teacher;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.variousdata.student.bean.LeaveRequestBean;
import com.hdu.newe.here.page.main.profile.PersonalInfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * @author pope
 */
public class LeaveRequestActivityT2 extends Activity {

    @BindView(R.id.leaveRequestList2)
    RecyclerView leaveRequestList2;
    @BindView(R.id.fabRefresh2)
    FloatingActionButton fabRefresh2;

    String userObjId;
    String userLeaveObjId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_request_t2);
        ButterKnife.bind(this);

        //获取用户Id和用户请假表的Id
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        userObjId = sharedPreferences.getString("objId", "");
        userLeaveObjId = sharedPreferences.getString("userLeaveObjId", "");

        //判断获取到的两个Id是否有效
        if (userObjId.equals("") || userLeaveObjId.equals("")) {
            Toast.makeText(LeaveRequestActivityT2.this, "请先完善您的个人信息", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LeaveRequestActivityT2.this, PersonalInfoActivity.class);
            startActivity(intent);
            finish();
        } else {
            //有效则开始获取请假表数据
            getLeaveRequestData(userLeaveObjId);
        }
    }

    /**
     * 获取请假表数据的方法
     *
     * @param userLeaveObjId 用户请假表的Id
     */
    private void getLeaveRequestData(String userLeaveObjId) {

        //获取用户请假表数据
        BmobQuery<LeaveRequestBean> query = new BmobQuery<>();
        query.getObject(userLeaveObjId, new QueryListener<LeaveRequestBean>() {
            @Override
            public void done(LeaveRequestBean leaveRequestBean, BmobException e) {
                if (e != null) {
                    Toast.makeText(LeaveRequestActivityT2.this, "error5465465", Toast.LENGTH_LONG).show();
                    Log.i("报错5465465",e.getMessage());
                } else {
                    //加载列表
                    loadList(leaveRequestBean);
                }
            }
        });

    }

    /**
     * 加载列表
     * @param leaveRequestBean 请假数据表
     */
    private void loadList(final LeaveRequestBean leaveRequestBean) {
        //配置列表适配器并加载显示
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LeaveRequestActivityT2.this);
        leaveRequestList2.setLayoutManager(layoutManager);
        LeaveRequestAdapterT adapterT = new LeaveRequestAdapterT(leaveRequestBean,LeaveRequestActivityT2.this);
        leaveRequestList2.setAdapter(adapterT);
    }
}
