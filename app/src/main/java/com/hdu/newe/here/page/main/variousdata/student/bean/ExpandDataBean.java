package com.hdu.newe.here.page.main.variousdata.student.bean;

import com.hdu.newe.here.biz.variousdata.student.bean.VariousDataBean;

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
     * 分类的标题 PARENT
     */
    private String parentTitle;
    /**
     * 父布局的ID PARENT
     */
    private String ID;
    private int childNum;

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
