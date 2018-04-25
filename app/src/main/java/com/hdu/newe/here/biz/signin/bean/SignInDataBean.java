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

    /**
     * 该课在课程数据表中的objectId
     */
    private String subjectId;

    /**
     * 该课发起签到的老师的UserBean的objectId
     */
    private String initiator;

    /**
     * 该课拥有的学生名单 其中保存的是学生在UserBean中objectId
     */
    private List<String> studentList;

    /**
     * 该课当前是否正在点名
     */
    private boolean isChecking;

    /**
     * 该课发起点名的时间
     */
    private BmobDate checkTime;

    /**
     * 该课发起点名的位置
     */
    private BmobGeoPoint checkLocation;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
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
}
