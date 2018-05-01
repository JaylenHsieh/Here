package com.hdu.newe.here.biz.profile.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 *
 * @author pope
 * @date 2018/4/25
 */

public class SubjectDataBean extends BmobObject {

    /**
     * 课程名
     */
    private String subjectName;

    /**
     * 课程号
     */
    private String subjectNum;

    /**
     * 任课老师 该老师的UserBean中objectId
     */
    private String subjectTeacher;

    /**
     * 上课时间
     */
    private BmobDate subjectBeginTime;

    /**
     * 下课时间
     */
    private BmobDate subjectFinishTime;

    /**
     * 上课地点 即经纬度坐标
     */
    private BmobGeoPoint subjectLocation;

    /**
     * 学生名单 学生在UserBean中的objectId
     */
    private List<String> studentList;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectNum() {
        return subjectNum;
    }

    public void setSubjectNum(String subjectNum) {
        this.subjectNum = subjectNum;
    }

    public String getSubjectTeacher() {
        return subjectTeacher;
    }

    public void setSubjectTeacher(String subjectTeacher) {
        this.subjectTeacher = subjectTeacher;
    }

    public BmobDate getSubjectBeginTime() {
        return subjectBeginTime;
    }

    public void setSubjectBeginTime(BmobDate subjectBeginTime) {
        this.subjectBeginTime = subjectBeginTime;
    }

    public BmobDate getSubjectFinishTime() {
        return subjectFinishTime;
    }

    public void setSubjectFinishTime(BmobDate subjectFinishTime) {
        this.subjectFinishTime = subjectFinishTime;
    }

    public BmobGeoPoint getSubjectLocation() {
        return subjectLocation;
    }

    public void setSubjectLocation(BmobGeoPoint subjectLocation) {
        this.subjectLocation = subjectLocation;
    }

    public List<String> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<String> studentList) {
        this.studentList = studentList;
    }
}
