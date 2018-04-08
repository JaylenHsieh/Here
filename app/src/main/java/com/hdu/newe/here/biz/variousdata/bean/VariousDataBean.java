package com.hdu.newe.here.biz.variousdata.bean;

import com.hdu.newe.here.biz.user.entity.UserBean;

/**
 * 数据模块的全部数据的Bean
 * @author pope
 * @date 2018/4/8
 */

public class VariousDataBean extends UserBean {

    private String subjectName;
    private Number attendanceRate;
    private String buffType;
    /**
     * 历史数据表关键字：
     * 请假类型 请假原因 请假状态 警告标题 警告内容 手机更换历史标题 手机更换历史内容
     */
    private String leaveRequestType;
    private String leaveRequestReason;
    private String leaveRequestState;
    private String warningTitle;
    private String warningContent;
    private String changeHistoryTitle;
    private String changeHistoryContent;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Number getAttendanceRate() {
        return attendanceRate;
    }

    public void setAttendanceRate(Number attendanceRate) {
        this.attendanceRate = attendanceRate;
    }

    public String getBuffType() {
        return buffType;
    }

    public void setBuffType(String buffType) {
        this.buffType = buffType;
    }

    public String getLeaveRequestType() {
        return leaveRequestType;
    }

    public void setLeaveRequestType(String leaveRequestType) {
        this.leaveRequestType = leaveRequestType;
    }

    public String getLeaveRequestReason() {
        return leaveRequestReason;
    }

    public void setLeaveRequestReason(String leaveRequestReason) {
        this.leaveRequestReason = leaveRequestReason;
    }

    public String getLeaveRequestState() {
        return leaveRequestState;
    }

    public void setLeaveRequestState(String leaveRequestState) {
        this.leaveRequestState = leaveRequestState;
    }

    public String getWarningTitle() {
        return warningTitle;
    }

    public void setWarningTitle(String warningTitle) {
        this.warningTitle = warningTitle;
    }

    public String getWarningContent() {
        return warningContent;
    }

    public void setWarningContent(String warningContent) {
        this.warningContent = warningContent;
    }

    public String getChangeHistoryTitle() {
        return changeHistoryTitle;
    }

    public void setChangeHistoryTitle(String changeHistoryTitle) {
        this.changeHistoryTitle = changeHistoryTitle;
    }

    public String getChangeHistoryContent() {
        return changeHistoryContent;
    }

    public void setChangeHistoryContent(String changeHistoryContent) {
        this.changeHistoryContent = changeHistoryContent;
    }
}
