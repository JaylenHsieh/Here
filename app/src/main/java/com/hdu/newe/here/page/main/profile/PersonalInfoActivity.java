package com.hdu.newe.here.page.main.profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_user_sex)
    EditText mTvUserSex;
    @BindView(R.id.tv_user_number_change)
    TextView tvUserNumberChange;
    @BindView(R.id.tv_instructor_phonenum_change)
    TextView tvInstructorPhonenumChange;
    @BindView(R.id.tv_disappear1)
    TextView tvDisappear1;
    @BindView(R.id.tv_instructor_addr_change)
    TextView tvInstructorAddrChange;
    @BindView(R.id.tv_disappear2)
    TextView tvDisappear2;
    @BindView(R.id.tv_instructor_name)
    TextView tvInstructorName;
    @BindView(R.id.tv_instructor_sex)
    TextView tvInstructorSex;
    @BindView(R.id.tv_instrutor_usernum)
    TextView tvInstrutorUsernum;
    @BindView(R.id.tv_instrutor_college)
    TextView tvInstrutorCollege;
    @BindView(R.id.tv_instructor_phonenum)
    TextView tvInstructorPhonenum;
    @BindView(R.id.tv_instructor_addr)
    TextView tvInstructorAddr;
    @BindView(R.id.tv_instructor_search)
    TextView tvInstructorSearch;
    @BindView(R.id.group_disappear)
    CardView groupDisappear;
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
    UserBean user;
    Boolean isTeacher;
    Integer flag = 0;
    Boolean isConfirm = false;
    String instructorId;
    Boolean isSetMsg = false;
    Boolean isShowing = false;

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static final String LEAVE_REQUEST_OBJ_ID = "userLeaveObjId";

    private List<String> mList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        objectId = pref.getString("objId", "");
        isTeacher = pref.getBoolean("isTeacher", false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (isTeacher) {
            initSomethingForTeacher();
        } else {
            initSomethingForStudent();
        }
    }

    private void initSomethingForTeacher() {
        //改变可视性
        hideOrShowCard();
        tvDisappear1.setVisibility(View.INVISIBLE);
        tvDisappear2.setVisibility(View.INVISIBLE);
        mTvStuClass.setVisibility(View.INVISIBLE);
        mTvInstructor.setVisibility(View.INVISIBLE);
        //更改几个标题文字
        tvInstructorAddrChange.setText("办公地址：");
        tvInstructorPhonenumChange.setText("电话：");
        tvUserNumberChange.setText("工号：");
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                if (e != null) {
                    Toast.makeText(PersonalInfoActivity.this, "error47123", Toast.LENGTH_LONG).show();
                    Log.i("报错47123", e.getMessage());
                } else {
                    setUser(userBean);
                    mTvUserName.setText(userBean.getUserName());
                    mTvUserSex.setText(userBean.getUserSex());
                    mTvUserNumber.setFocusable(true);
                    mTvUserNumber.setText(userBean.getUserNumber());
                    mTvUserNumber.setFocusable(false);
                    mTvStuFaculty.setText(userBean.getUserCollege());
                    mTvStuSpeciality.setText(userBean.getUserPhoneNum());
                    mTvStuClassNum.setText(userBean.getUserAddr());
                    createLeaveRequestData(userBean);
                }
            }
        });
    }

    private void initSomethingForStudent() {
        hideOrShowCard();
        BmobQuery<UserBean> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(objectId, new QueryListener<UserBean>() {
            @Override
            public void done(UserBean user, BmobException e) {
                if (e == null) {
                    setUser(user);
                    if (user.getInstructorId() != null && !"".equals(user.getInstructorId())) {
                        instructorId = user.getInstructorId();
                        BmobQuery<UserBean> query = new BmobQuery<>();
                        query.getObject(user.getInstructorId(), new QueryListener<UserBean>() {
                            @Override
                            public void done(UserBean userBean, BmobException e) {
                                if (e != null) {
                                    Toast.makeText(PersonalInfoActivity.this, "报错874787", Toast.LENGTH_LONG).show();
                                    Log.i("报错874787", e.getMessage());
                                } else {
                                    mTvInstructor.setText(userBean.getUserName());
                                }
                            }
                        });
                    } else {
                        instructorId = null;
                    }
                    mTvUserName.setText(user.getUserName());
                    mTvUserSex.setText(user.getUserSex());
                    mTvUserNumber.setText(user.getUserNumber());
                    mTvStuFaculty.setText(user.getUserCollege());
                    mTvStuSpeciality.setText(user.getUserMajor());
                    mTvStuClass.setText(user.getUserClass());
                    mTvStuClassNum.setText(user.getUserClassNum());
                    isSetMsg = true;
                    isConfirm = true;
                    createLeaveRequestData(user);
                } else {
                    Toast.makeText(PersonalInfoActivity.this, "error84541", Toast.LENGTH_SHORT).show();
                    Log.d("报错84541", e.getMessage());
                }
            }
        });
        mTvInstructor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isSetMsg) {
                    if (!isShowing) {
                        hideOrShowCard();
                        if (instructorId != null) {
                            initInstructorMsg();
                        }
                        isShowing = true;
                    }else {
                        tvInstructorSearch.setText("查找");
                        tvInstructorName.setText("");
                        tvInstructorSex.setText("");
                        tvInstrutorUsernum.setText("");
                        tvInstrutorCollege.setText("");
                        tvInstructorPhonenum.setText("");
                        tvInstructorAddr.setText("");
                    }
                    isConfirm = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void initInstructorMsg() {
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.getObject(instructorId, new QueryListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                if (e != null) {
                    Toast.makeText(PersonalInfoActivity.this, "error84154", Toast.LENGTH_LONG).show();
                    Log.i("报错84154", e.getMessage());
                } else {
                    tvInstructorName.setText(userBean.getUserName());
                    tvInstructorSex.setText(userBean.getUserSex());
                    tvInstrutorUsernum.setText(userBean.getUserNumber());
                    tvInstrutorCollege.setText(userBean.getUserCollege());
                    tvInstructorPhonenum.setText(userBean.getUserPhoneNum());
                    tvInstructorAddr.setText(userBean.getUserAddr());
                }
            }
        });
    }

    @OnClick({R.id.tvEdit, R.id.tvSubmit, R.id.tv_instructor_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvEdit:
                Toast.makeText(this, "现在可以编辑信息了", Toast.LENGTH_SHORT).show();
                mTvUserName.setFocusable(true);
                mTvUserName.setFocusableInTouchMode(true);
                mTvUserName.requestFocus();
                mTvUserSex.setFocusable(true);
                mTvUserSex.setFocusableInTouchMode(true);
                mTvUserSex.requestFocus();
                mTvStuFaculty.setFocusable(true);
                mTvStuFaculty.setFocusableInTouchMode(true);
                mTvStuFaculty.requestFocus();
                mTvStuSpeciality.setFocusable(true);
                mTvStuSpeciality.setFocusableInTouchMode(true);
                mTvStuSpeciality.requestFocus();
                mTvStuClassNum.setFocusable(true);
                mTvStuClassNum.setFocusableInTouchMode(true);
                mTvStuClassNum.requestFocus();
                if (!isTeacher) {
                    mTvStuClass.setFocusable(true);
                    mTvStuClass.setFocusableInTouchMode(true);
                    mTvStuClass.requestFocus();
                    mTvInstructor.setFocusable(true);
                    mTvInstructor.setFocusableInTouchMode(true);
                    mTvInstructor.requestFocus();
                }
                mTvEdit.setVisibility(View.INVISIBLE);
                mTvSubmit.setVisibility(View.VISIBLE);
                break;
            case R.id.tvSubmit:
                if ("".equals(mTvUserName.getText().toString())) {
                    Toast.makeText(PersonalInfoActivity.this, "未输入姓名，请输入姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                user.setUserName(mTvUserName.getText().toString());

                if ("".equals(mTvUserSex.getText().toString())) {
                    Toast.makeText(PersonalInfoActivity.this, "未输入性别，请输入性别", Toast.LENGTH_SHORT).show();
                    return;
                }
                user.setUserSex(mTvUserSex.getText().toString());

                if ("".equals(mTvStuFaculty.getText().toString())) {
                    Toast.makeText(PersonalInfoActivity.this, "未输入学院，请输入学院", Toast.LENGTH_SHORT).show();
                    return;
                }
                user.setUserCollege(mTvStuFaculty.getText().toString());

                if (!isTeacher) {
                    if (!isConfirm) {
                        Toast.makeText(PersonalInfoActivity.this, "未确定导员，请先输入导员姓名查找后确认", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ("".equals(mTvStuSpeciality.getText().toString())) {
                        Toast.makeText(PersonalInfoActivity.this, "未输入专业，请输入专业", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    user.setUserMajor(mTvStuSpeciality.getText().toString());

                    if ("".equals(mTvStuClass.getText().toString())) {
                        Toast.makeText(PersonalInfoActivity.this, "未输入班级，请输入班级", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    user.setUserClass(mTvStuClass.getText().toString());

                    if ("".equals(mTvStuClassNum.getText().toString())) {
                        Toast.makeText(PersonalInfoActivity.this, "未输入班级号，请输入班级号", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    user.setUserClassNum(mTvStuClassNum.getText().toString());

                    if (instructorId == null) {
                        Toast.makeText(PersonalInfoActivity.this, "未确定导员，请先输入导员姓名查找后确认", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        user.setInstructorId(instructorId);
                    }

                    if (leaveRequestObjId == null || "".equals(leaveRequestObjId)) {
                        Toast.makeText(PersonalInfoActivity.this, "error:未建立请假数据表", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        user.setLeaveRequestObjId(leaveRequestObjId);
                    }
                    user.update(objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                                editor.putString("userName", mTvUserName.getText().toString());
                                editor.putString("userSex", mTvUserSex.getText().toString());
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
                } else {
                    user.setUserPhoneNum(mTvStuSpeciality.getText().toString());
                    user.setUserAddr(mTvStuClassNum.getText().toString());
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                                editor.putString("userName", mTvUserName.getText().toString());
                                editor.putString("userSex", mTvUserSex.getText().toString());
                                editor.putString("userPhoneNum", mTvStuSpeciality.getText().toString());
                                editor.putString("userCollege", mTvStuFaculty.getText().toString());
                                editor.putString("userAddr", mTvStuClassNum.getText().toString());
                                editor.putString("userLeaveObjId", leaveRequestObjId);
                                editor.apply();
                                createVariousDataBean(user.getObjectId());
                                Toast.makeText(PersonalInfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(PersonalInfoActivity.this, "error94789", Toast.LENGTH_LONG).show();
                                Log.i("报错94789", e.getMessage());
                            }
                        }
                    });
                }
                break;
            case R.id.tv_instructor_search:
                if (tvInstructorSearch.getText().equals("确定")) {
                    isConfirm = true;
                    Toast.makeText(PersonalInfoActivity.this, "已确定", Toast.LENGTH_SHORT).show();
                } else {
                    BmobQuery<UserBean> query = new BmobQuery<>();
                    query.addWhereEqualTo("userName", mTvInstructor.getText().toString());
                    query.findObjects(new FindListener<UserBean>() {
                        @Override
                        public void done(List<UserBean> list, BmobException e) {
                            if (e != null) {
                                Toast.makeText(PersonalInfoActivity.this, "error32364", Toast.LENGTH_LONG).show();
                                Log.i("报错32364", e.getMessage());
                            } else {
                                if (list == null || list.isEmpty()) {
                                    tvInstrutorUsernum.setText("抱歉，查无此人！\n请核对姓名是否填写有误！");
                                    flag++;
                                    if (flag == 2) {
                                        flag = 0;
                                        Toast.makeText(PersonalInfoActivity.this, "若未填写错误，请联系导员开通其账号！", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    if (list.size() > 1) {
                                        Toast.makeText(PersonalInfoActivity.this, "该姓名的教师不止一个,当前只显示第一个", Toast.LENGTH_LONG).show();
                                    }
                                    UserBean userBean = list.get(0);
                                    tvInstructorName.setText(userBean.getUserName());
                                    tvInstructorSex.setText(userBean.getUserSex());
                                    tvInstrutorUsernum.setText(userBean.getUserNumber());
                                    tvInstrutorCollege.setText(userBean.getUserCollege());
                                    tvInstructorPhonenum.setText(userBean.getUserPhoneNum());
                                    tvInstructorAddr.setText(userBean.getUserAddr());
                                    tvInstructorSearch.setText("确定");
                                    instructorId = userBean.getObjectId();
                                }
                            }
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    /**
     * 初始化用户的多种数据表
     *
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
                        variousDataBean.setAllAttendanceRate(100);
                        variousDataBean.setBuffType(VariousDataBean.getBuffType(100));
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
        BmobQuery<LeaveRequestBean> query = new BmobQuery<>();
        query.addWhereEqualTo("userObjectId", user.getObjectId());
        query.findObjects(new FindListener<LeaveRequestBean>() {
            @Override
            public void done(List<LeaveRequestBean> list, BmobException e) {
                if (e != null) {
                    Toast.makeText(PersonalInfoActivity.this, "error496137", Toast.LENGTH_LONG).show();
                    Log.i("报错496137", e.getMessage());
                } else {
                    if (!list.isEmpty()) {
                        leaveRequestObjId = list.get(0).getObjectId();
                        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                        editor.putString(LEAVE_REQUEST_OBJ_ID, leaveRequestObjId);
                        return;
                    } else {
                        LeaveRequestBean leaveRequestBean = new LeaveRequestBean();
                        // 新建的一行数据的列表赋值一个空的 List
                        leaveRequestBean.setLeaveRequestReason(mList);
                        leaveRequestBean.setLeaveRequestState(mList);
                        leaveRequestBean.setLeaveRequestTime(mList);
                        leaveRequestBean.setUserObjectId(objectId);
                        leaveRequestBean.setLeaveStudents(mList);
                        leaveRequestBean.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                if (e == null) {
                                    leaveRequestObjId = objectId;
                                    SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                                    editor.putString(LEAVE_REQUEST_OBJ_ID, leaveRequestObjId);
                                    editor.apply();
                                } else {
                                    Toast.makeText(PersonalInfoActivity.this, "创建失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * 改变卡片可见性
     */
    private void hideOrShowCard() {
        if (groupDisappear.getVisibility() == View.VISIBLE) {
            groupDisappear.setVisibility(View.GONE);
        } else {
            groupDisappear.setVisibility(View.VISIBLE);
        }
    }
}
