package com.hdu.newe.here.biz.variousdata.bean;

import com.hdu.newe.here.biz.user.entity.UserBean;

import java.util.List;

/**
 * 数据模块的全部数据的Bean
 *
 * @author pope
 * @date 2018/4/8
 */

public class VariousDataBean extends UserBean {

    /**
     * 标识类型 学习积极分子 摇摆边缘流派 翘课高发人群
     */
    public static final int BUFF_TYPE_STUDY_HARD = 111;
    public static final int BUFF_TYPE_IRRESOLUTION = 222;
    public static final int BUFF_TYPE_SKIP_CLASS = 333;

    private List<String> subjectName;
    private List<Number> attendanceRate;
    private int buffType;
    private Number allAttendanceRate;
    /**
     * 历史数据表关键字：
     * 请假类型 请假原因 请假状态 警告标题 警告内容 手机更换历史标题 手机更换历史内容
     */
    private List<String> leaveRequestType;
    private List<String> leaveRequestReason;
    private List<String> leaveRequestState;
    private List<String> leaveRequestTime;
    private List<String> warningTitle;
    private List<String> warningContent;
    private List<String> changeHistoryTitle;
    private List<String> changeHistoryContent;

    public List<String> getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(List<String> subjectName) {
        this.subjectName = subjectName;
    }

    public List<Number> getAttendanceRate() {
        return attendanceRate;
    }

    public void setAttendanceRate(List<Number> attendanceRate) {
        this.attendanceRate = attendanceRate;
    }

    public int getBuffType() {
        return buffType;
    }

    public void setBuffType(int buffType) {
        this.buffType = buffType;
    }

    public Number getAllAttendanceRate() {
        return allAttendanceRate;
    }

    public void setAllAttendanceRate(Number allAttendanceRate) {
        this.allAttendanceRate = allAttendanceRate;
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

    public List<String> getWarningTitle() {
        return warningTitle;
    }

    public void setWarningTitle(List<String> warningTitle) {
        this.warningTitle = warningTitle;
    }

    public List<String> getWarningContent() {
        return warningContent;
    }

    public void setWarningContent(List<String> warningContent) {
        this.warningContent = warningContent;
    }

    public List<String> getChangeHistoryTitle() {
        return changeHistoryTitle;
    }

    public void setChangeHistoryTitle(List<String> changeHistoryTitle) {
        this.changeHistoryTitle = changeHistoryTitle;
    }

    public List<String> getChangeHistoryContent() {
        return changeHistoryContent;
    }

    public void setChangeHistoryContent(List<String> changeHistoryContent) {
        this.changeHistoryContent = changeHistoryContent;
    }

    public List<String> getLeaveRequestTime() {
        return leaveRequestTime;
    }

    public void setLeaveRequestTime(List<String> leaveRequestTime) {
        this.leaveRequestTime = leaveRequestTime;
    }
}
