package com.hdu.newe.here.bean;

import java.net.URL;

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
     * 姓名 学号 班级 班级号 专业 学院 手机序列号 辅导员 头像
     */
    private String userName;
    private String userStudentNum;
    private String userClass;
    private String userClassNum;
    private String userMajor;
    private String userCollege;
    private String userIMEI;
    private String userInstructor;
    private URL userAvatar;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserStudentNum() {
        return userStudentNum;
    }

    public void setUserStudentNum(String userStudentNum) {
        this.userStudentNum = userStudentNum;
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

    public String getUserIMEI() {
        return userIMEI;
    }

    public void setUserIMEI(String userIMEI) {
        this.userIMEI = userIMEI;
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

    public void setUserAvatar(URL userAvatar) {
        this.userAvatar = userAvatar;
    }
}
