package com.hdu.newe.here.biz.variousdata.student.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 多种数据表
 * 面向学生端用户
 * 内含：1、学生所修读的科目列表及相关出勤率数据
 *      2、学生当前标识类型（学习积极分子、摇摆边缘流派和翘课高发人群）
 *      3、学生所受到的警示历史相关数据
 *      4、学生账户所绑定的手机的变更历史记录
 *      5、学生的缺勤情况相关数据
 *
 * @author pope
 * @date 2018/4/8
 */

public class VariousDataBean extends BmobObject {

    /**
     * 标识类型 学习积极分子 摇摆边缘流派 翘课高发人群
     */
    public static final int BUFF_TYPE_STUDY_HARD = 111;
    public static final int BUFF_TYPE_IRRESOLUTION = 222;
    public static final int BUFF_TYPE_SKIP_CLASS = 333;

    /**
     * 用户在UserBean中的objectId
     */
    private String userObjectId;

    /**
     * 用户所修读的科目列表
     */
    private List<String> subjectName;

    /**
     * 用户的出勤率 与科目列表中的科目一一对应
     */
    private List<Number> attendanceRate;

    /**
     * 用户的标识类型
     */
    private int buffType;

    /**
     * 用户总出勤率
     */
    private Number allAttendanceRate;

    /**
     * 历史数据表关键字：
     * 警告标题 警告内容 旧手机IMEI 新手机IMEI 旧手机名 新手机名 更换时间
     */
    private List<String> warningTitle;
    private List<String> warningContent;
    private List<String> OldIMEI;
    private List<String> NewIMEI;
    private List<String> OldPhone;
    private List<String> NewPhone;
    private List<String> ChangeTime;

    /**
     * 旷课课程Id列表
     */
    private List<String> absentSubjectIdList;

    /**
     * 旷课发生时的考勤次数 与旷课课程列表一一对应
     */
    private List<Number> absentTimeList;

    public List<String> getOldIMEI() {
        return OldIMEI;
    }

    public void setOldIMEI(List<String> oldIMEI) {
        this.OldIMEI = oldIMEI;
    }

    public List<String> getNewIMEI() {
        return NewIMEI;
    }

    public void setNewIMEI(List<String> newIMEI) {
        this.NewIMEI = newIMEI;
    }

    public List<String> getOldPhone() {
        return OldPhone;
    }

    public void setOldPhone(List<String> oldPhone) {
        this.OldPhone = oldPhone;
    }

    public List<String> getNewPhone() {
        return NewPhone;
    }

    public void setNewPhone(List<String> newPhone) {
        this.NewPhone = newPhone;
    }

    public List<Number> getAbsentTimeList() {
        return absentTimeList;
    }

    public void setAbsentTimeList(List<Number> absentTimeList) {
        this.absentTimeList = absentTimeList;
    }

    public List<String> getAbsentSubjectIdList() {
        return absentSubjectIdList;
    }

    public void setAbsentSubjectIdList(List<String> absentSubjectIdList) {
        this.absentSubjectIdList = absentSubjectIdList;
    }

    public String getUserObjectId() {
        return userObjectId;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
    }

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

    public List<String> getChangeTime() {
        return ChangeTime;
    }

    public void setChangeTime(List<String> changeTime) {
        this.ChangeTime = changeTime;
    }
}
