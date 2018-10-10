package com.hdu.newe.here.biz.variousdata.teacher;

import android.content.Context;

import com.hdu.newe.here.biz.BaseLogic;
import com.hdu.newe.here.biz.profile.bean.ClassDataBean;
import com.hdu.newe.here.biz.signin.bean.SignInDataBean;
import com.hdu.newe.here.biz.user.entity.UserBean;
import com.hdu.newe.here.biz.variousdata.student.bean.VariousDataBean;
import com.hdu.newe.here.biz.variousdata.teacher.bean.UserBeanForList;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * @author pope
 * @date 2018/5/3
 */

public class VariousDataLogicT extends BaseLogic implements VariousDataInterfaceT {

    private String userObjectId = null;

    private int count = -1;
    private int count1 = -1;

    private static VariousDataLogicT INSTANCE;

    @Override
    public void getAbsentData(String subjectId) {

    }

    /**
     * 获取某课程考勤数据
     *
     * @param subjectId           被查询考勤数据的课程的Id
     * @param onCheckDataCallback 回调数据
     */
    @Override
    public void getCheckData(String subjectId, final OnCheckDataCallback onCheckDataCallback) {
        onCheckDataCallback.onStartGetData();
        BmobQuery<SignInDataBean> query = new BmobQuery<>();
        query.getObject(subjectId, new QueryListener<SignInDataBean>() {
            @Override
            public void done(final SignInDataBean signInDataBean, BmobException e) {
                if (e == null) {

                    //初始化两个与目标数据长度等长的列表用以存放数据
                    final List<UserBeanForList> leaveRequestList = new ArrayList<>();
                    final List<UserBeanForList> absentList = new ArrayList<>();
                    for (int i = 0; i < signInDataBean.getLeaveStudentList().size(); i++) {
                        leaveRequestList.add(new UserBeanForList());
                    }
                    for (int i = 0; i < signInDataBean.getAbsentStudentList().size(); i++) {
                        absentList.add(new UserBeanForList());
                    }

                    count = -1;

                    //for循环检索每一个请假用户的个人信息
                    for (int i = 0; i < signInDataBean.getLeaveStudentList().size(); i++) {
                        BmobQuery<UserBean> query1 = new BmobQuery<>();
                        final int finalI = i;
                        query1.getObject(signInDataBean.getLeaveStudentList().get(i)
                                , new QueryListener<UserBean>() {
                                    @Override
                                    public void done(UserBean userBean, BmobException e) {
                                        if (e == null) {
                                            leaveRequestList.get(finalI).setStudentName(userBean.getUserName());
                                            leaveRequestList.get(finalI).setStudentNum(userBean.getUserNumber());
                                            if (finalI == signInDataBean.getLeaveStudentList().size() - 1) {
                                                count++;
                                                if (count == 1) {
                                                    onCheckDataCallback.onGetSuccess(signInDataBean, leaveRequestList, absentList);
                                                }
                                            }
                                        } else {
                                            onCheckDataCallback.onGetFailed("查询请假学生列表的用户数据出错" + e.getMessage());
                                        }
                                    }
                                });
                    }

                    //for循环检索每一个旷课用户的个人信息
                    for (int i = 0; i < signInDataBean.getAbsentStudentList().size(); i++) {
                        BmobQuery<UserBean> query2 = new BmobQuery<>();
                        final int finalI = i;
                        query2.getObject(signInDataBean.getAbsentStudentList().get(i)
                                , new QueryListener<UserBean>() {
                                    @Override
                                    public void done(UserBean userBean, BmobException e) {
                                        if (e == null) {
                                            absentList.get(finalI).setStudentName(userBean.getUserName());
                                            absentList.get(finalI).setStudentNum(userBean.getUserNumber());
                                            if (finalI == signInDataBean.getAbsentStudentList().size() - 1) {
                                                count++;
                                                if (count == 1) {
                                                    onCheckDataCallback.onGetSuccess(signInDataBean, leaveRequestList, absentList);
                                                }
                                            }
                                        }
                                    }
                                });
                    }
                } else {
                    onCheckDataCallback.onGetFailed("查询该班级考勤数据出错" + e.getMessage());
                }
            }
        });
    }

    /**
     * 初步获取班级数据方法
     *
     * @param onClassDataCallback 班级数据获取的回调
     */
    @Override
    public void getClassData(final OnClassDataCallback onClassDataCallback) {

        onClassDataCallback.onStartGetData();

        userObjectId = getContext().getSharedPreferences("user", Context.MODE_PRIVATE)
                .getString("objId", null);

        if (userObjectId == null) {
            onClassDataCallback.onGetFailed("sharedPreferences里的userObjectId为空");
            return;
        }

        final BmobQuery<UserBean> query = new BmobQuery<>();
        query.getObject(userObjectId, new QueryListener<UserBean>() {
            @Override
            public void done(final UserBean userBean, BmobException e) {
                List<String> classList = new ArrayList<>();
                final List<Integer> checkTimes = new ArrayList<>();
                if (e == null) {

                    //当所带班级列表不为空时，该用户肯定有辅导员身份
                    if (userBean.getClassList() != null && !userBean.getClassList().isEmpty()) {
                        classList = userBean.getClassList();
                    }

                    //当课程列表不为空时，该用户就有教师身份
                    if (userBean.getSubjectList() != null) {
                        //检索课程列表里每一个课都发生过点名的次数
                        for (int i = 0; i < userBean.getSubjectList().size(); i++) {
                            classList.add(userBean.getSubjectList().get(i));
                            BmobQuery<SignInDataBean> query1 = new BmobQuery<>();
                            query1.addWhereEqualTo("subjectId", userBean.getSubjectList().get(i));
                            final int finalI = i;
                            final List<String> finalClassList = classList;
                            query1.findObjects(new FindListener<SignInDataBean>() {
                                @Override
                                public void done(List<SignInDataBean> list, BmobException e) {
                                    if (e == null) {
                                        checkTimes.add(finalI, list.size());
                                        if (finalI == userBean.getSubjectList().size() - 1) {
                                            if (userBean.getClassList() == null || userBean.getClassList().size() == 0) {
                                                onClassDataCallback.onGetSuccess(finalClassList, 0, checkTimes);
                                            } else {
                                                onClassDataCallback.onGetSuccess(finalClassList, userBean.getClassList().size(), checkTimes);
                                            }
                                        }
                                    } else {
                                        onClassDataCallback.onGetFailed("查询课程点名次数出错" + e.getMessage());
                                    }
                                }
                            });
                        }
                    } else {
                        //若课程列表为空
                        onClassDataCallback.onGetSuccess(classList, classList.size(), checkTimes);
                    }
                } else {
                    onClassDataCallback.onGetFailed(e.getMessage());
                }
            }
        });
    }

    /**
     * 获取某班级的学生的出勤率数据
     *
     * @param classId
     * @param onAttendanceDataCallbackT
     */
    @Override
    public void getAttendanceRateData(String classId, final OnAttendanceDataCallbackT onAttendanceDataCallbackT) {

        onAttendanceDataCallbackT.onStartGetData();
        BmobQuery<ClassDataBean> query = new BmobQuery<>();
        query.getObject(classId, new QueryListener<ClassDataBean>() {
            @Override
            public void done(final ClassDataBean classDataBean, BmobException e) {
                if (e == null) {
                    //初始化计数的参数
                    count1 = -1;
                    final List<String> nameList = new ArrayList<>();
                    final List<Number> attendanceRateList = new ArrayList<>();
                    //for循环检索每一个班级成员的姓名和全体出勤率
                    for (int i = 0; i < classDataBean.getClassMember().size(); i++) {
                        final int finalI = i;
                        BmobQuery<UserBean> q = new BmobQuery<>();
                        q.getObject(classDataBean.getClassMember().get(i), new QueryListener<UserBean>() {
                            @Override
                            public void done(UserBean userBean, BmobException e) {
                                if (e == null) {
                                    nameList.add(finalI, userBean.getUserName());
                                    if (nameList.size() == classDataBean.getClassMember().size()) {
                                        count1++;
                                        if (count1 == 1){
                                            onAttendanceDataCallbackT.onGetSuccess(nameList,attendanceRateList);
                                        }
                                    }
                                } else {
                                    onAttendanceDataCallbackT.onGetFailed("查询学生姓名出错" + e.getMessage());
                                }
                            }
                        });

                        BmobQuery<VariousDataBean> q1 = new BmobQuery<>();
                        q1.getObject(classDataBean.getClassMember().get(i), new QueryListener<VariousDataBean>() {
                            @Override
                            public void done(VariousDataBean variousDataBean, BmobException e) {
                                if (e == null) {
                                    attendanceRateList.add(finalI, variousDataBean.getAllAttendanceRate());
                                    count1++;
                                    if (count1 == 1){
                                        onAttendanceDataCallbackT.onGetSuccess(nameList,attendanceRateList);
                                    }
                                } else {
                                    onAttendanceDataCallbackT.onGetFailed("查询学生的总体出勤率出错" + e.getMessage());
                                }
                            }
                        });
                    }
                } else {
                    onAttendanceDataCallbackT.onGetFailed("查询班级数据出错" + e.getMessage());
                }
            }
        });

    }

    private static class Holder {
        static VariousDataLogicT INSTANCE = new VariousDataLogicT();
    }

    public static VariousDataLogicT getINSTANCE() {
        return Holder.INSTANCE;
    }
}
