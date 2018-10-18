package com.hdu.newe.here.page.main.leaverequest;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.profile.bean.ClassDataBean;
import com.hdu.newe.here.biz.user.entity.UserBean;
import com.hdu.newe.here.biz.variousdata.student.bean.LeaveRequestBean;
import com.hdu.newe.here.page.main.profile.PersonalInfoActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
 * @author pope
 */
public class LeaveRequestActivity2 extends Activity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mTvUserName)
    TextView mTvUserName;
    @BindView(R.id.mTvUserNumber)
    TextView mTvUserNumber;
    @BindView(R.id.mTvStuFaculty)
    TextView mTvStuFaculty;
    @BindView(R.id.mTvStuSpeciality)
    TextView mTvStuSpeciality;
    @BindView(R.id.mTvStuInstructor)
    TextView mTvStuInstructor;
    @BindView(R.id.view_account)
    ConstraintLayout viewAccount;
    @BindView(R.id.edRequestContent)
    EditText edRequestContent;
    @BindView(R.id.view_request)
    FrameLayout viewRequest;
    @BindView(R.id.tvStartTime)
    TextView tvStartTime;
    @BindView(R.id.tvFinishTime)
    TextView tvFinishTime;
    @BindView(R.id.leaveRequestState)
    TextView leaveRequestState;
    @BindView(R.id.view_time)
    ConstraintLayout viewTime;
    @BindView(R.id.view_submit)
    FrameLayout viewSubmit;

    UserBean thisUser;
    String userId;
    String userName;
    String userStudentNum;
    String userMajor;
    String userCollege;
    String userInstructor;
    String userLeaveObjId;
    String leaveStartTime;
    String leaveFinishTime;
    Calendar calendar;
    int year;
    int month;
    int day;
    int hour;
    int minute;
    int mYear;
    int mMonth;
    int mDay;
    int mHour;
    int mMinute;
    int start1;
    int start2;
    int start3;
    int start4;
    int start5;
    int finish1;
    int finish2;
    int finish3;
    int finish4;
    int finish5;
    /**
     * 记录结束时间是否与开始时间同一天
     */
    boolean isSameDay = true;
    /**
     * 记录需要发送请假申请的数量
     * 用作判断发送的申请是否发送完毕
     */
    int askCount = 0;
    /**
     * 记录已经发送请假申请的数量
     * 用作判断发送的申请是否发送完毕
     */
    int haveAskCount = 0;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_leave_request);
        ButterKnife.bind(this);
        //初始化相关内容
        init();

    }

    /**
     * 初始化相关内容
     */
    private void init() {
        //对toolbar的按钮设置点击监听
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //结束本活动
                finish();
            }
        });

        progressDialog = new ProgressDialog(LeaveRequestActivity2.this);
        progressDialog.setTitle("请稍等");

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        userId = sharedPreferences.getString("objId", "");
        userName = sharedPreferences.getString("userName", "");
        userStudentNum = sharedPreferences.getString("userNumber", "");
        userMajor = sharedPreferences.getString("userMajor", "");
        userCollege = sharedPreferences.getString("userCollege", "");
        userInstructor = sharedPreferences.getString("userInstructor", "");
        userLeaveObjId = sharedPreferences.getString("userLeaveObjId", "");
        //检测用户是否完善个人信息 若无则自动跳转至个人中心以完善个人信息
        if ("".equals(userId) || "".equals(userName) || "".equals(userStudentNum) || "".equals(userMajor)
                || "".equals(userCollege) || "".equals(userInstructor) || "".equals(userLeaveObjId)) {
            Toast.makeText(LeaveRequestActivity2.this, "请先完善您的个人信息喔~", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LeaveRequestActivity2.this, PersonalInfoActivity.class);
            startActivity(intent);
            finish();
        }
        //加载该用户的个人信息
        mTvUserName.setText(userName);
        mTvUserNumber.setText(userStudentNum);
        mTvStuSpeciality.setText(userMajor);
        mTvStuFaculty.setText(userCollege);
        mTvStuInstructor.setText(userInstructor);

        //修改申请状态
        leaveRequestState.setText("未申请");

        //初始化请假开始时间和技术时间为手机当前系统时间
        calendar = Calendar.getInstance();
        //获取系统日期
        //年
        year = calendar.get(Calendar.YEAR);
        //月
        month = calendar.get(Calendar.MONTH) + 1;
        //日
        day = calendar.get(Calendar.DAY_OF_MONTH);
        //获取系统时间
        //小时
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        //分钟
        minute = calendar.get(Calendar.MINUTE);
        //请假开始时间、结束时间初始显示
        tvStartTime.setText(changeToString(year, month, day, hour, minute));
        tvFinishTime.setText(changeToString(year, month, day, hour, minute));
        //请假开始时间、结束时间选择值的初始化
        start1 = year;
        finish1 = year;
        start2 = month;
        finish2 = month;
        start3 = day;
        finish3 = day;
        start4 = hour;
        finish4 = hour;
        start5 = minute;
        finish5 = minute;
    }

    /**
     * 将时间转换成 xxxx年xx月xx日 xx:xx 的形式
     *
     * @param year   年
     * @param month  月
     * @param day    日
     * @param hour   时
     * @param minute 分
     * @return String字符串
     */
    private String changeToString(int year, int month, int day, int hour, int minute) {
        String time = year + "年";
        if (month < 10) {
            time += "0";
        }
        time += month + "月";
        if (day < 10) {
            time += "0";
        }
        time += day + "日 ";
        if (hour < 10) {
            time += "0";
        }
        time += hour + ":";
        if (minute < 10) {
            time += "0";
        }
        time += minute;
        return time;
    }

    /**
     * 请假开始时间 结束时间 提交按钮 的监听
     *
     * @param view
     */
    @OnClick({R.id.tvStartTime, R.id.tvFinishTime, R.id.view_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvStartTime:
                progressDialog.setMessage("请稍等");
                progressDialog.show();
                //设置时间选择器的监听
                TimePickerDialog.OnTimeSetListener listener1 = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        mHour = i;
                        mMinute = i1;
                        //开始时间选择值赋值
                        start4 = mHour;
                        start5 = mMinute;
                        tvStartTime.setText(changeToString(mYear, mMonth, mDay, mHour, mMinute));
                    }
                };
                //建立时间选择器的对象
                final TimePickerDialog timePickerDialog = new TimePickerDialog(LeaveRequestActivity2.this,
                        listener1, hour, minute, true);
                //设置日期选择器的监听
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Log.i("测试", "i=" + i + "i1=" + i1 + "i2=" + i2);
                        mYear = i;
                        mMonth = i1 + 1;
                        mDay = i2;
                        //开始日期选择值赋值
                        start1 = mYear;
                        start2 = mMonth;
                        start3 = mDay;
                        //显示时间选择器
                        timePickerDialog.show();
                    }
                };
                //建立日期选择器的对象
                DatePickerDialog datePickerDialog = new DatePickerDialog(LeaveRequestActivity2.this,
                        0, listener, year, month - 1, day);
                datePickerDialog.show();
                progressDialog.dismiss();
                break;
            case R.id.tvFinishTime:
                progressDialog.setMessage("请稍等");
                progressDialog.show();
                //设置时间选择器的监听
                TimePickerDialog.OnTimeSetListener listener2 = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        //合法性判断 结束时间大于开始时间
                        if (isSameDay) {
                            if (i < mHour) {
                                Toast.makeText(LeaveRequestActivity2.this, "结束时间早于开始时间，请检查是否填写错误！", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                return;
                            }
                            if (i == mHour && i1 < mMinute) {
                                Toast.makeText(LeaveRequestActivity2.this, "结束时间早于开始时间，请检查是否填写错误！", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                return;
                            }
                        }
                        mHour = i;
                        mMinute = i1;
                        //结束时间选择值赋值
                        finish4 = mHour;
                        finish5 = mMinute;
                        tvFinishTime.setText(changeToString(mYear, mMonth, mDay, mHour, mMinute));
                    }
                };
                //建立时间选择器的对象
                final TimePickerDialog timePickerDialog1 = new TimePickerDialog(LeaveRequestActivity2.this,
                        listener2, hour, minute, true);
                //设置日期选择器的监听
                DatePickerDialog.OnDateSetListener listener3 = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //合法性判断 结束时间大于开始时间
                        if (i < mYear) {
                            Toast.makeText(LeaveRequestActivity2.this, "结束时间早于开始时间，请检查是否填写错误！", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            return;
                        }
                        if (i == mYear && (i1 + 1) < mMonth) {
                            Toast.makeText(LeaveRequestActivity2.this, "结束时间早于开始时间，请检查是否填写错误！", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            return;
                        }
                        if (i == mYear && (i1 + 1) == mMonth && i2 < mDay) {
                            Toast.makeText(LeaveRequestActivity2.this, "结束时间早于开始时间，请检查是否填写错误！", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            return;
                        }
                        if (i == mYear && (i1 + 1) == mMonth && i2 == mDay) {
                            isSameDay = true;
                        } else {
                            isSameDay = false;
                        }
                        mYear = i;
                        mMonth = i1 + 1;
                        mDay = i2;
                        //结束日期选择值赋值
                        finish1 = mYear;
                        finish2 = mMonth;
                        finish3 = mDay;
                        //显示时间选择器
                        timePickerDialog1.show();
                    }
                };
                //建立日期选择器的对象
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(LeaveRequestActivity2.this,
                        0, listener3, year, month - 1, day);
                datePickerDialog1.show();
                progressDialog.dismiss();
                break;
            case R.id.view_submit:
                progressDialog.setMessage("提交中");
                progressDialog.show();
                //查询该用户的UserBean 获取其课程列表
                BmobQuery<UserBean> query = new BmobQuery<>();
                query.getObject(userId, new QueryListener<UserBean>() {
                    @Override
                    public void done(UserBean userBean, BmobException e) {
                        if (e != null) {
                            Toast.makeText(LeaveRequestActivity2.this, "error47474", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Log.i("报错47474", e.getMessage());
                        } else {
                            List<String> subjectList = userBean.getSubjectList();
                            if (subjectList == null || subjectList.isEmpty()) {
                                Toast.makeText(LeaveRequestActivity2.this, "您未加入任何课程，无需请假", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            } else {
                                try {
                                    foundTeacherReceiver(subjectList);
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 找出需要接收请假条的任课教师
     * 将请假时间范围分为三个部分：
     * 1、整天都在请假时间范围内的
     * 2、请假开始时间所在那天到那天的23:59
     * 3、请假结束时间所在那天到那天的00:00
     * 将三个时间范围内的课程通过时间码比对后找出在请假时间范围内的课程，并将请假
     * 条发送给该课程的任课教师
     *
     * @param subjectList 课程列表
     * @throws ParseException 异常信息
     */
    private void foundTeacherReceiver(final List<String> subjectList) throws ParseException {
        /**请假天数大于一周的情况*/
        //判断开始时间与结束时间相隔天数是否大于等于7天
        DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date startDate = dateFormat.parse(changeToString(start1, start2, start3, start4, start5));
        Date finishDate = dateFormat.parse(changeToString(finish1, finish2, finish3, finish4, finish5));
        int betweenDays = getGapCount(startDate, finishDate);
        //若大于等于7天，则所有的课的老师都需要收到这张请假条
        if (betweenDays >= 7) {
            //课程数量即为需要发送请假申请的数量
            askCount = subjectList.size();
            for (int c = 0; c < subjectList.size(); c++) {
                BmobQuery<ClassDataBean> query = new BmobQuery<>();
                query.addWhereEqualTo("subjectCode", subjectList.get(c));
                final int finalC = c;
                query.findObjects(new FindListener<ClassDataBean>() {
                    @Override
                    public void done(List<ClassDataBean> list, BmobException e) {
                        if (e != null) {
                            Toast.makeText(LeaveRequestActivity2.this, "error714541", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Log.i("报错714541", "在for循环第" + finalC + "次出错 错误信息：" + e.getMessage());
                        } else {
                            if (list == null || list.isEmpty()) {
                                Toast.makeText(LeaveRequestActivity2.this, "未查询到课程码为" + subjectList.get(finalC) + "的课程信息",
                                        Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            } else {
                                //将申请发送给该老师
                                sendToTeacher(list.get(0).getTeacherId());
                            }
                        }
                    }
                });
            }
            return;
        }
        /**请假天数小于一周的情况*/
        int startDayOfWeek = getWeek(changeToString(start1, start2, start3, start4, start5));
        int finishDayOfWeek = getWeek(changeToString(finish1, finish2, finish3, finish4, finish5));
        //初始化一个列表用来存放请假时间范围内全天有课的星期几
        List<Integer> leaveAllDayList = new ArrayList<>();
        if (finishDayOfWeek >= startDayOfWeek) {
            //请假开始时间与结束时间在同一周的情况
            //请假时间范围内包含某个一整天的情况
            if ((finishDayOfWeek - startDayOfWeek) > 1) {
                for (int i = 0; i < (finishDayOfWeek - startDayOfWeek - 1); i++) {
                    leaveAllDayList.add(finishDayOfWeek + 1 + i);
                }
            }
        } else {
            //请假开始时间与结束时间不在同一周的情况
            if ((7 - finishDayOfWeek) + (startDayOfWeek - 1) >= 1) {
                for (int a = 0; a < (finishDayOfWeek - 1); a++) {
                    leaveAllDayList.add(a + 1);
                }
                for (int b = 0; b < (7 - startDayOfWeek); b++) {
                    leaveAllDayList.add(startDayOfWeek + b + 1);
                }
            }
        }
        //定义两个列表 第一个用来存放将课程码剔除掉随机码的时间码 第二个用来存放时间码一一对应的课程Id
        List<String> timeCodeList = new ArrayList<>();
        List<String> subjCodeList = new ArrayList<>();
        //时间码的剔除与保存以及与时间码一一对应的课程Id的保存
        for (int d = 0; d < subjectList.size(); d++) {
            String subjCode = subjectList.get(d);
            //时间码
            String timeCode = subjCode.substring(3);
            //时间码长度为8即该课程有两个时间段
            if (timeCode.length() == 8) {
                timeCodeList.add(timeCode.substring(4));
                subjCodeList.add(subjCode);
                timeCode = timeCode.substring(0, 4);
            }
            timeCodeList.add(timeCode);
            subjCodeList.add(subjCode);
        }
        //先比对全天请假的那几天里有哪些课 并发送请假申请
        for (int e = 0; e < leaveAllDayList.size(); e++) {
            int leaveDayOfWeek = leaveAllDayList.get(e);
            //会涉及到删除list元素的操作 采用倒序来比对
            for (int f = timeCodeList.size(); f > 0; f--) {
                //正在操作的元素的位置
                int position = f - 1;
                int haveLessonDayOfWeek = Integer.valueOf(timeCodeList.get(position).substring(0, 1));
                //该课程属于全天将被请假的范围内 对其任课老师发送请假请求并从list中剔除
                if (leaveDayOfWeek == haveLessonDayOfWeek) {
                    //需要发送请假申请的数量+1
                    askCount++;
                    sendToTeacherBySubjectCode(subjCodeList.get(position));
                    timeCodeList.remove(position);
                    subjCodeList.remove(position);
                }
            }
        }
        //定义两个变量用来存放开始时间与结束时间之后与之前最近的一节课是第几节课
        int beforeFinishTimePoint = whichLesson(finish4, finish5, 1);
        int behindStartTimePoint = whichLesson(start4, start5, -1);
        for (int g = timeCodeList.size(); g > 0; g--) {
            //标记当前课程是否发生过操作
            boolean isRemove = false;
            int position = g - 1;
            int haveLessonDayOfWeek = Integer.valueOf(timeCodeList.get(position).substring(0, 1));
            //请假开始的那天有课
            if (haveLessonDayOfWeek == startDayOfWeek) {
                int firstLesson = Integer.valueOf(timeCodeList.get(position).substring(1, 3));
                int lastLesson = firstLesson + Integer.valueOf(timeCodeList.get(position).substring(3));
                //判断请假开始的时间点之后最近一节课是否为当前比对的这节课的时间范围内
                if (behindStartTimePoint >= firstLesson && behindStartTimePoint <= lastLesson) {
                    //需要发送请假申请的数量+1
                    askCount++;
                    //满足条件即为该课程范围内
                    sendToTeacherBySubjectCode(subjCodeList.get(position));
                    timeCodeList.remove(position);
                    subjCodeList.remove(position);
                    isRemove = true;
                }
            }
            //判断该课程是否已经被操作过了 避免重复操作
            if (!isRemove) {
                int firstLesson = Integer.valueOf(timeCodeList.get(position).substring(1, 3));
                int lastLesson = firstLesson + Integer.valueOf(timeCodeList.get(position).substring(3));
                //请假结束的那天有课
                if (haveLessonDayOfWeek == finishDayOfWeek) {
                    //判断请假结束的时间点之前最近一节课是否为当前比对的这节课的时间范围内
                    if (beforeFinishTimePoint >= firstLesson && beforeFinishTimePoint <= lastLesson) {
                        //需要发送请假申请的数量+1
                        askCount++;
                        //满足条件即为该课程范围内
                        sendToTeacherBySubjectCode(subjCodeList.get(position));
                        timeCodeList.remove(position);
                        subjCodeList.remove(position);
                    }
                }
            }
        }
    }

    /**
     * 将请假申请发送至该课程任课教师请假数据表中
     *
     * @param subjectCode 课程码
     */
    private void sendToTeacherBySubjectCode(final String subjectCode) {
        BmobQuery<ClassDataBean> query = new BmobQuery<>();
        query.addWhereEqualTo("subjectCode", subjectCode);
        query.findObjects(new FindListener<ClassDataBean>() {
            @Override
            public void done(List<ClassDataBean> list, BmobException e) {
                if (e != null) {
                    Toast.makeText(LeaveRequestActivity2.this,
                            "error361474", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    Log.i("报错361474", e.getMessage());
                } else {
                    if (list == null || list.isEmpty()) {
                        Toast.makeText(LeaveRequestActivity2.this,
                                "未查询到课程码为" + subjectCode + "的课程信息", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    } else {
                        sendToTeacher(list.get(0).getTeacherId());
                    }
                }
            }
        });
    }

    /**
     * 将请假申请发送至该老师
     *
     * @param teacherId 接收申请的老师的Id
     */
    private void sendToTeacher(final String teacherId) {
        //已经发送申请数+1
        haveAskCount++;
        //查询该教师在请假数据表中的数据
        BmobQuery<LeaveRequestBean> q = new BmobQuery<>();
        q.addWhereEqualTo("userObjectId", teacherId);
        q.findObjects(new FindListener<LeaveRequestBean>() {
            @Override
            public void done(List<LeaveRequestBean> list, BmobException e) {
                if (e != null) {
                    Toast.makeText(LeaveRequestActivity2.this, "error714447", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    Log.i("报错714447", e.getMessage());
                } else {
                    List<String> leaveReason = new ArrayList<>();
                    List<String> leaveState = new ArrayList<>();
                    List<String> leaveStartTime = new ArrayList<>();
                    List<String> leaveFinishTime = new ArrayList<>();
                    List<String> leaveStudents = new ArrayList<>();
                    //TODO 请假条是否有效的逻辑补充
                    if (list == null || list.isEmpty()) {
                        //该教师无请假数据表
                        leaveReason.add(edRequestContent.getText().toString());
                        leaveState.add("审核中");
                        leaveStartTime.add(changeToString(start1, start2, start3, start4, start5));
                        leaveFinishTime.add(changeToString(finish1, finish2, finish3, finish4, finish5));
                        leaveStudents.add(userId);
                        LeaveRequestBean leaveRequestBean = new LeaveRequestBean();
                        leaveRequestBean.setUserObjectId(teacherId);
                        leaveRequestBean.setLeaveRequestReason(leaveReason);
                        leaveRequestBean.setLeaveRequestState(leaveState);
                        leaveRequestBean.setLeaveStartTime(leaveStartTime);
                        leaveRequestBean.setLeaveFinishTime(leaveFinishTime);
                        leaveRequestBean.setLeaveStudents(leaveStudents);
                        leaveRequestBean.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e != null) {
                                    Toast.makeText(LeaveRequestActivity2.this,
                                            "error447144", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                    Log.i("报错447144", e.getMessage());
                                }
                            }
                        });
                    } else {
                        //该教师已建表 对数据进行更新
                        LeaveRequestBean bean = list.get(0);
                        leaveReason = bean.getLeaveRequestReason();
                        leaveState = bean.getLeaveRequestState();
                        leaveStartTime = bean.getLeaveStartTime();
                        leaveFinishTime = bean.getLeaveFinishTime();
                        leaveStudents = bean.getLeaveStudents();
                        if (leaveReason == null) {
                            leaveReason = new ArrayList<>();
                        }
                        if (leaveState == null) {
                            leaveState = new ArrayList<>();
                        }
                        if (leaveStartTime == null) {
                            leaveStartTime = new ArrayList<>();
                        }
                        if (leaveFinishTime == null) {
                            leaveFinishTime = new ArrayList<>();
                        }
                        if (leaveStudents == null) {
                            leaveStudents = new ArrayList<>();
                        }
                        leaveReason.add(edRequestContent.getText().toString());
                        leaveState.add("审核中");
                        leaveStartTime.add(changeToString(start1, start2, start3, start4, start5));
                        leaveFinishTime.add(changeToString(finish1, finish2, finish3, finish4, finish5));
                        leaveStudents.add(userId);
                        bean.setLeaveRequestReason(leaveReason);
                        bean.setLeaveRequestState(leaveState);
                        bean.setLeaveStartTime(leaveStartTime);
                        bean.setLeaveFinishTime(leaveFinishTime);
                        bean.setLeaveStudents(leaveStudents);
                        bean.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e != null) {
                                    Toast.makeText(LeaveRequestActivity2.this,
                                            "error154178", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                    Log.i("报错154178", e.getMessage());
                                }
                            }
                        });
                    }
                }
            }
        });
        //判断是否全部的申请发送完毕
        if (askCount == haveAskCount) {
            //保存记录到自己账户
            saveLeaveHistory();
            progressDialog.dismiss();
            Toast.makeText(LeaveRequestActivity2.this, "申请发送成功！", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveLeaveHistory() {
        BmobQuery<LeaveRequestBean> query = new BmobQuery<>();
        query.getObject(userLeaveObjId, new QueryListener<LeaveRequestBean>() {
            @Override
            public void done(LeaveRequestBean leaveRequestBean, BmobException e) {
                if (e != null) {
                    Toast.makeText(LeaveRequestActivity2.this, "error96484", Toast.LENGTH_LONG).show();
                    Log.i("报错96484", e.getMessage());
                } else {
                    List<String> leaveReason = leaveRequestBean.getLeaveRequestReason();
                    List<String> leaveState = leaveRequestBean.getLeaveRequestState();
                    List<String> leaveStartTime = leaveRequestBean.getLeaveStartTime();
                    List<String> leaveFinishTime = leaveRequestBean.getLeaveFinishTime();
                    if (leaveReason == null) {
                        leaveReason = new ArrayList<>();
                    }
                    if (leaveState == null) {
                        leaveState = new ArrayList<>();
                    }
                    if (leaveStartTime == null) {
                        leaveStartTime = new ArrayList<>();
                    }
                    if (leaveFinishTime == null) {
                        leaveFinishTime = new ArrayList<>();
                    }
                    leaveReason.add(edRequestContent.getText().toString());
                    leaveState.add("审核中");
                    leaveStartTime.add(changeToString(start1, start2, start3, start4, start5));
                    leaveFinishTime.add(changeToString(finish1, finish2, finish3, finish4, finish5));
                    leaveRequestBean.setLeaveRequestReason(leaveReason);
                    leaveRequestBean.setLeaveRequestState(leaveState);
                    leaveRequestBean.setLeaveStartTime(leaveStartTime);
                    leaveRequestBean.setLeaveFinishTime(leaveFinishTime);
                    leaveRequestBean.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e != null) {
                                Toast.makeText(LeaveRequestActivity2.this, "error664745", Toast.LENGTH_LONG).show();
                                Log.i("报错664745", e.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 根据当前日期获得是星期几
     * time=yyyy-MM-dd
     *
     * @return 星期几
     */
    private int getWeek(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int wek = c.get(Calendar.DAY_OF_WEEK);
        wek--;
        if (wek == 0) {
            wek = 7;
        }
        return wek;
    }

    /**
     * 获取两个日期之间的间隔天数
     *
     * @return 相隔天数
     */
    private int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 计算出所给出时间之前或之后最近的一节课是第几节课
     * 若改时间处于某节课时间段内，则给出该节课是第几节课
     *
     * @param hour 时
     * @param min  分
     * @param type 之前=1/之后=-1
     * @return 返回时间之前或之后最近的一节课
     */
    private int whichLesson(int hour, int min, int type) {

        switch (type) {
            case 1:
                //时间点之前最近的一节课
                switch (hour) {
                    case 8:
                        if (min > 55) {
                            return 2;
                        } else {
                            if (min <= 5) {
                                return 0;
                            } else {
                                return 1;
                            }
                        }
                    case 9:
                        return 2;
                    case 10:
                        return min > 50 ? 4 : 3;
                    case 11:
                        return min > 40 ? 5 : 4;
                    case 12:
                        return 5;
                    case 13:
                        return min > 35 ? 6 : 5;
                    case 14:
                        return min > 25 ? 7 : 6;
                    case 15:
                        return min > 15 ? 8 : 7;
                    case 16:
                        return min > 5 ? 9 : 8;
                    case 17:
                        return 9;
                    case 18:
                        return min > 30 ? 10 : 9;
                    case 19:
                        return min > 20 ? 11 : 10;
                    case 20:
                        return min > 10 ? 12 : 11;
                    case 21:
                        return 12;
                    case 22:
                        return 12;
                    case 23:
                        return 12;
                    default:
                        return 0;
                }
            case -1:
                //时间点之后最近一节课
                switch (hour) {
                    case 8:
                        return min >= 50 ? 2 : 1;
                    case 9:
                        return min >= 40 ? 3 : 2;
                    case 10:
                        return min >= 45 ? 4 : 3;
                    case 11:
                        return min >= 35 ? 5 : 4;
                    case 12:
                        return min >= 25 ? 6 : 5;
                    case 13:
                        return 6;
                    case 14:
                        return min >= 20 ? 7 : 6;
                    case 15:
                        return min >= 10 ? 8 : 7;
                    case 16:
                        return min >= 50 ? 10 : 9;
                    case 17:
                        return 10;
                    case 18:
                        return 10;
                    case 19:
                        return min >= 15 ? 11 : 10;
                    case 20:
                        return min >= 5 ? 12 : 11;
                    case 21:
                        return 13;
                    case 22:
                        return 13;
                    case 23:
                        return 13;
                    default:
                        return 1;
                }
            default:
                return 0;
        }
    }

}