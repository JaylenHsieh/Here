package com.hdu.newe.here.biz.signin.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * 签到数据表
 * @author pope
 * @date 2018/4/25
 */

public class SignInDataBean extends BmobObject {

    public final static int LIST_TYPE_LEAVEREQUEST = 6;
    public final static int LIST_TYPE_ABSENT = 9;

    /**
     * 课程编码
     */
    private String subjectCode;

    /**
     * 该课拥有的学生名单 其中保存的是学生在UserBean中objectId
     */
    private List<String> studentList;

    /**
     * 该课当前是否正在点名
     */
    private boolean isChecking;

    /**
     * 请假学生列表
     */
    private List<String> leaveStudentList;

    /**
     * 旷课学生列表
     */
    private List<String> absentStudentList;

    /**
     * 该课发起点名的时间
     */
    private BmobDate checkTime;

    /**
     * 该课发起点名的位置
     */
    private BmobGeoPoint checkLocation;

    /**
     * 第几次考勤
     */
    private Number checkCount;

    /**
     * 发起教师的Id
     */
    private String teacherId;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public static int getListTypeLeaverequest() {
        return LIST_TYPE_LEAVEREQUEST;
    }

    public static int getListTypeAbsent() {
        return LIST_TYPE_ABSENT;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public List<String> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<String> studentList) {
        this.studentList = studentList;
    }

    public boolean isChecking() {
        return isChecking;
    }

    public void setChecking(boolean checking) {
        isChecking = checking;
    }

    public List<String> getLeaveStudentList() {
        return leaveStudentList;
    }

    public void setLeaveStudentList(List<String> leaveStudentList) {
        this.leaveStudentList = leaveStudentList;
    }

    public List<String> getAbsentStudentList() {
        return absentStudentList;
    }

    public void setAbsentStudentList(List<String> absentStudentList) {
        this.absentStudentList = absentStudentList;
    }

    public BmobDate getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(BmobDate checkTime) {
        this.checkTime = checkTime;
    }

    public BmobGeoPoint getCheckLocation() {
        return checkLocation;
    }

    public void setCheckLocation(BmobGeoPoint checkLocation) {
        this.checkLocation = checkLocation;
    }

    public Number getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(Number checkCount) {
        this.checkCount = checkCount;
    }
}
