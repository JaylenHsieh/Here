package com.hdu.newe.here.page.main.variousdata.student.bean;

import com.hdu.newe.here.biz.variousdata.student.bean.VariousDataBean;

import java.util.List;

/**
 *
 * @author pope
 * @date 2018/4/10
 */

public class ExpandDataBean extends VariousDataBean {

    public static final int ITEM_PARENT = 0;
    public static final int ITEM_CHILD_LEAVE_REQUEST = 1;
    public static final int ITEM_CHILD_WARNING = 2;
    public static final int ITEM_CHILD_CHANGE = 3;

    /**
     * 显示类型 ALL
     */
    private int showType;

    /**
     * 是否展开 PARENT
     */
    private boolean  isExpand;

    /**
     * 子布局所加载的数据Bean
     */
    private VariousDataBean childBean;

    /**
     * 父类的标题 PARENT
     */
    private String parentTitle;

    /**
     * 父布局的ID PARENT
     */
    private String ID;

    private List<String> leaveRequestType;
    private List<String> leaveRequestReason;
    private List<String> leaveRequestState;
    private List<String> leaveRequestTime;

    private int childNum;

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

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public String getParentTitle() {
        return parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public VariousDataBean getChildBean() {
        return childBean;
    }

    public void setChildBean(VariousDataBean childBean) {
        this.childBean = childBean;
    }

    public int getChildNum() {
        return childNum;
    }

    public void setChildNum(int childNum) {
        this.childNum = childNum;
    }
}
