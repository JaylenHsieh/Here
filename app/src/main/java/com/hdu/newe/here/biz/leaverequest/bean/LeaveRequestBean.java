package com.hdu.newe.here.biz.leaverequest.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 请假数据表
 * @author pope
 * @date 2018/5/7
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
     * 该条数据的主人的Id 可以是学生 可以是老师
     */
    private String userId;

    /**
     * 教师端：某教师在接收到的请假请求中各个请假的请假学生
     */
    private List<String> leaveStudents;

    /**
     * 学生端：某学生请假历史中各次请假的类型
     * 教师端：某教师在接收到过的请假请求中各个请假的类型
     */
    private List<String> leaveRequestType;

    /**
     * 学生端：某学生请假历史中各次请假的原因
     * 教师端：某教师在接收到的请假请求中各个请假的原因
     */
    private List<String> leaveRequestReason;

    /**
     * 学生端：某学生的请假历史中各次请假的状态 共 审核中/已同意/待联系/已驳回 4种
     */
    private List<String> leaveRequestState;

    /**
     * 该条请假数据是否有效（建立在该条请假数据已被老师同意的基础上）
     * 教师端：某教师在接收到的请假请求中各个请假的状态 共 有效/失效 2种
     */
    private List<Boolean> isUseful;

    /**
     * 学生端：某学生请假历史中各次请假的时间
     * 教师端：某教师在接收到的请假请求中各个请假的时间
     */
    private List<String> leaveRequestTime;

    public List<String> getLeaveStudents() {
        return leaveStudents;
    }

    public void setLeaveStudents(List<String> leaveStudents) {
        this.leaveStudents = leaveStudents;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
