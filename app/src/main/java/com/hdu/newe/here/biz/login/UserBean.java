package com.hdu.newe.here.biz.login;

/**
 * Created by Jaylen Hsieh on 2018/04/05.
 */
public class UserBean {
    private String userNumber;
    private String imei;
    private boolean isTeacher;

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }
}
