package com.hdu.newe.here.biz.user.entity;

import java.net.URL;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 继承BmobObject即可实现数据传输
 * 其余几个与用户有关的数据表继承该表即可通过用户在UserBean的objectId在其他表里查询到该用户在其他表中的数据
 * @author pope
 * @date 2018/4/5
 */

public class UserBean extends BmobObject {

    /**
     * 用户数据表关键字
     * 姓名 学/工号 班级 班级号 专业 学院 手机序列号 辅导员 是否为教师 头像 课程列表
     */
    private String userName;
    private String userNumber;
    private String userClass;
    private String userClassNum;
    private String userMajor;
    private String userCollege;
    private String userInstructor;
    private String imei;
    private boolean isTeacher;
    private URL userAvatar;
    private List<String> subjectList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public String getUserClassNum() {
        return userClassNum;
    }

    public void setUserClassNum(String userClassNum) {
        this.userClassNum = userClassNum;
    }

    public String getUserMajor() {
        return userMajor;
    }

    public void setUserMajor(String userMajor) {
        this.userMajor = userMajor;
    }

    public String getUserCollege() {
        return userCollege;
    }

    public void setUserCollege(String userCollege) {
        this.userCollege = userCollege;
    }

    public String getUserInstructor() {
        return userInstructor;
    }

    public void setUserInstructor(String userInstructor) {
        this.userInstructor = userInstructor;
    }

    public URL getUserAvatar() {
        return userAvatar;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public void setUserAvatar(URL userAvatar) {
        this.userAvatar = userAvatar;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(boolean teacher) {
        isTeacher = teacher;
    }

    public List<String> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<String> subjectList) {
        this.subjectList = subjectList;
    }
}
