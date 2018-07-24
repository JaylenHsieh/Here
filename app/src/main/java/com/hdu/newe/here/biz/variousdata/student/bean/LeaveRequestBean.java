package com.hdu.newe.here.biz.variousdata.student.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 请假数据表
 * 面向学生端和教师端用户
 * 学生端：学生端用到表中的请假类型 请假原因 请假状态 请假时间等关键字 数据表中其余关键字无数据
 *       这些关键字用来保存学生相关的请假历史数据，当发起请假条的请求时，即向该表中添加数据
 * 教师端：教师端用到表中所有关键字
 *       其中“请假学生列表”关键字中保存某教师用户所收到的向其发起请假的学生的列表
 *       其余关键字中的数据均与“请假学生列表”中各学生一一对应。
 *       当学生发起请假请求时即对应更改其请求的老师在该表中的数据。
 *
 * @author pope
 * @date 2018/7/12
 */

public class LeaveRequestBean extends BmobObject {

    /**
     * 以下为表示学生端和教师端请假状态的各个常量
     * 表示含义依次为：已同意/已驳回/审核中/待联系/有效/失效
     */
    public final static int STATE_AGREED = 666;
    public final static int STATE_DISAGREE = 555;
    public final static int STATE_REVIEW = 444;
    public final static int STATE_CONTACT = 333;
    public final static int STATE_USEFUL = 123;
    public final static int STATE_USELESS = 321;

    /**
     * 用户在UserBean中的objectId
     */
    private String userObjectId;

    /**
     * 请假类型
     */
    private List<String> leaveRequestType;

    /**
     * 请假原因
     */
    private List<String> leaveRequestReason;

    /**
     * 请假状态
     */
    private List<String> leaveRequestState;

    /**
     * 请假时间
     */
    private List<String> leaveRequestTime;

    /**
     * 请假学生列表
     */
    private List<String> leaveStudents;

    /**
     * 请假条是否有效 与请假的学生列表一一对应
     */
    private List<Boolean> isUseful;

    public String getUserObjectId() {
        return userObjectId;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
    }

    public List<String> getLeaveRequestType() {
        return leaveRequestType;
    }

    public void setLeaveRequestType(List<String> leaveRequestType) {
        this.leaveRequestType = leaveRequestType;
    }

    public List<String> getLeaveRequestReason() {
        return leaveRequestReason;
    }

    public void setLeaveRequestReason(List<String> leaveRequestReason) {
        this.leaveRequestReason = leaveRequestReason;
    }

    public List<String> getLeaveRequestState() {
        return leaveRequestState;
    }

    public void setLeaveRequestState(List<String> leaveRequestState) {
        this.leaveRequestState = leaveRequestState;
    }

    public List<String> getLeaveRequestTime() {
        return leaveRequestTime;
    }

    public void setLeaveRequestTime(List<String> leaveRequestTime) {
        this.leaveRequestTime = leaveRequestTime;
    }

    public List<String> getLeaveStudents() {
        return leaveStudents;
    }

    public void setLeaveStudents(List<String> leaveStudents) {
        this.leaveStudents = leaveStudents;
    }

    public List<Boolean> getIsUseful() {
        return isUseful;
    }

    public void setIsUseful(List<Boolean> isUseful) {
        this.isUseful = isUseful;
    }
}
