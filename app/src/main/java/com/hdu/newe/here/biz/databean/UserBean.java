package com.hdu.newe.here.biz.databean;

import java.net.URL;

import cn.bmob.v3.BmobObject;

/**
 * 用户的基本信息的数据表 其他所有将会保存到Bmob的数据表都继承该表。
 * 当有数据需求时，一个objectId就可在不同表中查到需要的所有数据
 * @author pope
 * @date 2018/4/5
 */

public class UserBean extends BmobObject {

    private String userName;
    private Integer userStudentNum;
    private String userClass;
    private Integer userClassNum;
    private String userCollege;
    private String userMajor;
    private URL userAvatar;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserStudentNum() {
        return userStudentNum;
    }

    public void setUserStudentNum(Integer userStudentNum) {
        this.userStudentNum = userStudentNum;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public Integer getUserClassNum() {
        return userClassNum;
    }

    public void setUserClassNum(Integer userClassNum) {
        this.userClassNum = userClassNum;
    }

    public String getUserCollege() {
        return userCollege;
    }

    public void setUserCollege(String userCollege) {
        this.userCollege = userCollege;
    }

    public String getUserMajor() {
        return userMajor;
    }

    public void setUserMajor(String userMajor) {
        this.userMajor = userMajor;
    }
}
