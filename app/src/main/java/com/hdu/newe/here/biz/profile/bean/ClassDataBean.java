package com.hdu.newe.here.biz.profile.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 班级数据表
 * 面向学生端和教师端
 * 该表保存所有的教学班和行政班的班级数据，学生用户或教师用户可通过classNum或者subjectCode在该表中查询获取需要的数据
 *
 * @author pope
 * @date 2018/5/3
 */

public class ClassDataBean extends BmobObject {

    /**
     * 课程编号
     */
    private String subjectCode;

    /**
     * 班级号
     */
    private String classNum;

    /**
     * 该班辅导员
     */
    private String instructor;

    /**
     * 该班教师
     */
    private String teacherId;

    /**
     * 该班成员列表 存放每一位学生在UserBean的objectId
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

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public List<String> getClassMember() {
        return classMember;
    }

    public void setClassMember(List<String> classMember) {
        this.classMember = classMember;
    }
}
