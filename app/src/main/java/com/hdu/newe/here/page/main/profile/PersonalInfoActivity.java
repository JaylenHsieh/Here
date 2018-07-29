package com.hdu.newe.here.page.main.profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.variousdata.student.bean.LeaveRequestBean;
import com.hdu.newe.here.biz.user.entity.UserBean;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author Jaylen Hsieh
 * @date 2018/07/23
 */
public class PersonalInfoActivity extends AppCompatActivity {

    @BindView(R.id.tv_user_name)
    EditText mTvUserName;
    @BindView(R.id.tv_user_number)
    EditText mTvUserNumber;
    @BindView(R.id.tv_stu_faculty)
    EditText mTvStuFaculty;
    @BindView(R.id.tv_stu_speciality)
    EditText mTvStuSpeciality;
    @BindView(R.id.tvEdit)
    TextView mTvEdit;
    @BindView(R.id.tvSubmit)
    TextView mTvSubmit;
    @BindView(R.id.tv_stu_class)
    EditText mTvStuClass;
    @BindView(R.id.tv_stu_class_num)
    EditText mTvStuClassNum;
    @BindView(R.id.tv_instructor)
    EditText mTvInstructor;

    String objectId;
    String leaveRequestObjId;
    Boolean mIsTeacher;
    Boolean mIsInstructor;

    public static final String LEAVE_REQUEST_OBJ_ID = "LeaveRequestObjId";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        objectId = pref.getString("objId", "");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (!mTvUserNumber.getText().toString().equals("暂未提交")){

        BmobQuery<UserBean> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(objectId, new QueryListener<UserBean>() {
            @Override
            public void done(UserBean user, BmobException e) {
                if (e == null) {
                    mTvUserName.setText(user.getUserName());
                    mTvUserNumber.setText(user.getUserNumber());
                    mTvStuFaculty.setText(user.getUserCollege());
                    mTvStuSpeciality.setText(user.getUserMajor());
                    mTvStuClass.setText(user.getUserClass());
                    mTvStuClassNum.setText(user.getUserClassNum());
                    mTvInstructor.setText(user.getUserInstructor());

                    // TODO 虽然没有改动，但是后台会把这两个值变成 false
                    mIsTeacher = user.isTeacher();
                    mIsInstructor = user.isInstructor();
                    Toast.makeText(PersonalInfoActivity.this, "从后台获取数据成功", Toast.LENGTH_SHORT).show();

                    createLeaveRequestData(user);
                } else {
                    Toast.makeText(PersonalInfoActivity.this, "查询失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("error", e.getMessage());
                }
            }
        });
//        }

    }

    @OnClick({R.id.tvEdit, R.id.tvSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvEdit:
                Toast.makeText(this, "现在可以编辑信息了", Toast.LENGTH_SHORT).show();
                mTvUserName.setFocusable(true);
                mTvUserName.setFocusableInTouchMode(true);
                mTvUserName.requestFocus();
//                mTvUserNumber.setFocusable(true);
//                mTvUserNumber.setFocusableInTouchMode(true);
//                mTvUserNumber.requestFocus();
                mTvStuFaculty.setFocusable(true);
                mTvStuFaculty.setFocusableInTouchMode(true);
                mTvStuFaculty.requestFocus();
                mTvStuSpeciality.setFocusable(true);
                mTvStuSpeciality.setFocusableInTouchMode(true);
                mTvStuSpeciality.requestFocus();
                mTvStuClass.setFocusable(true);
                mTvStuClass.setFocusableInTouchMode(true);
                mTvStuClass.requestFocus();
                mTvStuClassNum.setFocusable(true);
                mTvStuClassNum.setFocusableInTouchMode(true);
                mTvStuClassNum.requestFocus();
                mTvInstructor.setFocusable(true);
                mTvInstructor.setFocusableInTouchMode(true);
                mTvInstructor.requestFocus();
                mTvEdit.setVisibility(View.GONE);
                mTvSubmit.setVisibility(View.VISIBLE);
                break;
            case R.id.tvSubmit:
                UserBean user = new UserBean();
                //会出现莫有上传的值变成 false，所以改成传一下所有数据
                user.setUserName(mTvUserName.getText().toString());
                user.setUserMajor(mTvStuSpeciality.getText().toString());
                user.setUserCollege(mTvStuFaculty.getText().toString());
                user.setUserClass(mTvStuClass.getText().toString());
                user.setUserClassNum(mTvStuClassNum.getText().toString());
                user.setUserInstructor(mTvInstructor.getText().toString());
                // TODO 虽然没有改动，但是后台会把这两个值变成 false
                user.setIsInstructor(mIsInstructor);
                user.setIsTeacher(mIsTeacher);
                user.setLeaveRequestObjId(leaveRequestObjId);
                user.update(objectId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(PersonalInfoActivity.this, "保存成功，下次启动生效", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PersonalInfoActivity.this, "失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 获取到用户的 @param leaveRequestObjId，如果为空，则再LeaveRequestBean创建一行新的数据
     * 如果不为空，则获取到leaveRequestObjId，
     *
     * @param user
     */
    private void createLeaveRequestData(UserBean user) {
        if ("".equals(user.getLeaveRequestObjId()) || user.getLeaveRequestObjId() == null) {
            LeaveRequestBean leaveRequestBean = new LeaveRequestBean();
            leaveRequestBean.save(new SaveListener<String>() {
                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        leaveRequestObjId = objectId;
                        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                        editor.putString(LEAVE_REQUEST_OBJ_ID, leaveRequestObjId);
                        editor.apply();
                        uploadleaveObjIdInfo();
                        Toast.makeText(PersonalInfoActivity.this, "请假表创建成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PersonalInfoActivity.this, "创建失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            leaveRequestObjId = user.getLeaveRequestObjId();
        }
    }

    /**
     * 如果为空，就直接先上传一下，避免出现多次建表的情况
     */
    private void uploadleaveObjIdInfo(){
        UserBean user = new UserBean();
        user.setLeaveRequestObjId(leaveRequestObjId);
        user.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    Log.d("TAG","success");
                } else {
                    Log.d("TAG","error"+e.getMessage());
                }
            }
        });

        LeaveRequestBean leaveRequest = new LeaveRequestBean();
        // 新建的一行数据的列表赋值一个空的 List
        leaveRequest.setLeaveRequestReason(Collections.<String>emptyList());
        leaveRequest.setLeaveRequestState(Collections.<String>emptyList());
        leaveRequest.setLeaveRequestTime(Collections.<String>emptyList());
        leaveRequest.setUserObjectId(objectId);
        leaveRequest.setIsTeacher(mIsTeacher);
        leaveRequest.update(leaveRequestObjId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){

                }else{
                    Log.d("TAG",e.getMessage());
                }
            }
        });

    }
}
