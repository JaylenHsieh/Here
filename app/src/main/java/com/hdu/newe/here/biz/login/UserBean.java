package com.hdu.newe.here.biz.login;

/**
 * Created by Jaylen Hsieh on 2018/04/05.
 */
public class UserBean {
    private String studentNumber;
    private String imei;
    private boolean isTeacher;

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
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
