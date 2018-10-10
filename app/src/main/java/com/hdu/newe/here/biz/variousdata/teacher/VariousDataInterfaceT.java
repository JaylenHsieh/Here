package com.hdu.newe.here.biz.variousdata.teacher;

import com.hdu.newe.here.biz.signin.bean.SignInDataBean;
import com.hdu.newe.here.biz.variousdata.teacher.bean.UserBeanForList;
import com.hdu.newe.here.page.base.BaseDataCallback;

import java.util.List;

/**
 * 教师端M层接口
 * @author pope
 * @date 2018/5/3
 */

public interface VariousDataInterfaceT {

    void getAbsentData(String subjectId);

    interface OnAbsentDataCallback extends BaseDataCallback{

        void onGetSuccess(List<String> studentNameList,List<String> absentTimeList,List<String> studentNumList);

    }

    /**
     * 获取考勤数据的方法
     * @param subjectId 被查询考勤数据的课程的Id
     */
    void getCheckData(String subjectId,OnCheckDataCallback onCheckDataCallback);

    interface OnCheckDataCallback extends BaseDataCallback{

        /**
         * @param signInDataBean 回调SignInDataBean数据
         * @param leaveRequestStudentList 回调请假学生列表数据
         * @param absentStudentList 回调旷课学生列表数据
         */
        void onGetSuccess(SignInDataBean signInDataBean
                , List<UserBeanForList> leaveRequestStudentList,List<UserBeanForList> absentStudentList);
    }

    /**
     * 初步获取所带班级和所教班级的数据
     * @param onClassDataCallback 获取数据的回调
     */
    void getClassData(OnClassDataCallback onClassDataCallback);

    interface OnClassDataCallback extends BaseDataCallback{

        /**
         * 获取所带班级数据成功时回调的方法
         * @param classList 回调一个含有该老师所带和所教班级的对应objectId的List
         * @param classCount 回调该老师所带班级数量
         * @param checkTimes 某科目已点名次数
         */
        void onGetSuccess(List<String> classList,int classCount,List<Integer> checkTimes);

    }

    /**
     * 获取自然班出勤率方法
     * @param classId 自然班Id
     * @param onAttendanceDataCallbackT 数据回调
     */
    void getAttendanceRateData(String classId,OnAttendanceDataCallbackT onAttendanceDataCallbackT);

    interface OnAttendanceDataCallbackT extends BaseDataCallback{

        /**
         * 获取学生出勤率成功时的回调
         * @param studentList
         * @param attendanceRateList
         */
        void onGetSuccess(List<String> studentList,List<Number> attendanceRateList);

    }
}
