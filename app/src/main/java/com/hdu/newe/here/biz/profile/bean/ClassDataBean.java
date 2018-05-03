package com.hdu.newe.here.biz.profile.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 班级数据表
 * @author pope
 * @date 2018/5/3
 */

public class ClassDataBean extends BmobObject {

    public final static int CLASS_TYPE_NATURAL = 2;
    public final static int CLASS_TYPE_SUBJECT = 4;

    /**
     * 课程编号
     */
    private String subjectCode;

    /**
     * 班级号
     */
    private String classNum;

    /**
     * 该班辅导员 存放辅导员在UserBean中的objectId
     */
    private String instrructor;

    /**
     * 该班教师
     */
    private String teacherId;

    /**
     * 该班学生列表 存放每一位学生在UserBean的objectId
     */
    private List<String> classMember;

    /**
     * 课程名字
     */
    private String subjectName;

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getInstrructor() {
        return instrructor;
    }

    public void setInstrructor(String instrructor) {
        this.instrructor = instrructor;
    }

    public List<String> getClassMember() {
        return classMember;
    }

    public void setClassMember(List<String> classMember) {
        this.classMember = classMember;
    }
}
