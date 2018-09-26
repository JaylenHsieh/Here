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
import com.hdu.newe.here.biz.user.entity.UserBean;
import com.hdu.newe.here.biz.variousdata.student.bean.LeaveRequestBean;
import com.hdu.newe.here.biz.variousdata.student.bean.VariousDataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
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
    UserBean user;
    Boolean isTeacher;

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static final String LEAVE_REQUEST_OBJ_ID = "LeaveRequestObjId";

    private List<String> mList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        objectId = pref.getString("objId", "");
        isTeacher = pref.getBoolean("isTeacher", false);
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
                    setUser(user);
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
//                    Toast.makeText(PersonalInfoActivity.this, "从后台获取数据成功", Toast.LENGTH_SHORT).show();

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
                user.setUserName(mTvUserName.getText().toString());
                user.setUserMajor(mTvStuSpeciality.getText().toString());
                user.setUserCollege(mTvStuFaculty.getText().toString());
                user.setUserClass(mTvStuClass.getText().toString());
                user.setUserClassNum(mTvStuClassNum.getText().toString());
                user.setUserInstructor(mTvInstructor.getText().toString());
                user.setLeaveRequestObjId(leaveRequestObjId);
                user.update(objectId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                            editor.putString("userName", mTvUserName.getText().toString());
                            editor.putString("userMajor", mTvStuSpeciality.getText().toString());
                            editor.putString("userCollege", mTvStuFaculty.getText().toString());
                            editor.putString("userClass", mTvStuClass.getText().toString());
                            editor.putString("userClassNum", mTvStuClassNum.getText().toString());
                            editor.putString("userInstructor", mTvInstructor.getText().toString());
                            editor.putString("userLeaveObjId", leaveRequestObjId);
                            editor.apply();
                            createVariousDataBean(user.getObjectId());
                            Toast.makeText(PersonalInfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            finish();
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
     * 初始化用户的多种数据表
     * @param objectId 用户Id
     */
    private void createVariousDataBean(final String objectId) {

        BmobQuery<VariousDataBean> query = new BmobQuery<>();
        query.addWhereEqualTo("userObjectId", objectId);
        query.findObjects(new FindListener<VariousDataBean>() {
            @Override
            public void done(List<VariousDataBean> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.isEmpty()) {
                        //说明未创建数据表
                        VariousDataBean variousDataBean = new VariousDataBean();
                        variousDataBean.setUserObjectId(objectId);
                        variousDataBean.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    return;
                                } else {
                                    Toast.makeText(PersonalInfoActivity.this, "error1112", Toast.LENGTH_LONG).show();
                                    Log.i("报错1112", e.getMessage());
                                }
                            }
                        });
                    } else {
                        //说明已经创建
                        return;
                    }
                } else {
                    Toast.makeText(PersonalInfoActivity.this, "error1111", Toast.LENGTH_LONG).show();
                    Log.i("报错11111", e.getMessage());
                }
            }
        });

    }

    /**
     * 获取到用户的 @param leaveRequestObjId，如果为空，则在 LeaveRequestBean创建一行新的数据
     * 如果不为空，则获取到leaveRequestObjId，
     *
     * @param user
     */
    private void createLeaveRequestData(UserBean user) {
        if ("".equals(user.getLeaveRequestObjId()) || user.getLeaveRequestObjId() == null) {
            LeaveRequestBean leaveRequestBean = new LeaveRequestBean();
            // 新建的一行数据的列表赋值一个空的 List
            mList.add("");
            leaveRequestBean.setLeaveRequestReason(mList);
            leaveRequestBean.setLeaveRequestState(mList);
            mList.add("");
            leaveRequestBean.setLeaveRequestTime(mList);
            leaveRequestBean.setUserObjectId(objectId);
            leaveRequestBean.save(new SaveListener<String>() {
                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        leaveRequestObjId = objectId;
                        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                        editor.putString(LEAVE_REQUEST_OBJ_ID, leaveRequestObjId);
                        editor.apply();
                        uploadLeaveObjIdInfo();
//                        Toast.makeText(PersonalInfoActivity.this, "请假表创建成功", Toast.LENGTH_SHORT).show();
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
    private void uploadLeaveObjIdInfo() {
        UserBean user = new UserBean();
        user.setLeaveRequestObjId(leaveRequestObjId);
        user.setIsInstructor(mIsInstructor);
        user.setIsTeacher(mIsTeacher);
        user.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d("TAG", "success");
                } else {
                    Log.d("TAG", "error" + e.getMessage());
                }
            }
        });

        LeaveRequestBean leaveRequest = new LeaveRequestBean();
        leaveRequest.setIsTeacher(mIsTeacher);
        leaveRequest.update(leaveRequestObjId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                } else {
                    Log.d("TAG", e.getMessage());
                }
            }
        });

    }
}
