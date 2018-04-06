package com.hdu.newe.here.bean;

/**
 *
 * @author pope
 * @date 2018/4/5
 */

public class HistoryDataBean extends UserBean {

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
