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

    /**
     * 上课地点码
     */
    private String placeCode;

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

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

    /**
     * 将传入的时间码转换成分为单位的时间码
     *
     * @param timeCode 课程码中提取出来的时间码（第5~7位和第9~11位）
     * @return 返回用分作为单位时间码（第1~4位标识课程开始时间，第5~8位标识课程结束时间）
     */
    public String changeToMinTime(String timeCode) {

        String startTime = "";
        int finishTime = 0;
        int firstClassTime = Integer.valueOf(timeCode.substring(0, 2));
        int countOfClass = Integer.valueOf(timeCode.substring(2, 3));
        switch (firstClassTime) {
            case 1:
                startTime = "0485";
                if (countOfClass >= 2) {
                    finishTime += 15;
                }
                break;
            case 2:
                startTime = "0535";
                finishTime += 15;
                break;
            case 3:
                startTime = "0600";
                break;
            case 4:
                startTime = "0650";
                break;
            case 5:
                startTime = "0700";
                break;
            case 6:
                startTime = "0815";
                break;
            case 7:
                startTime = "0865";
                break;
            case 8:
                startTime = "0915";
                break;
            case 9:
                startTime = "0965";
                break;
            case 10:
                startTime = "1110";
                break;
            case 11:
                startTime = "1160";
                break;
            case 12:
                startTime = "1210";
                break;
            default:
                break;
        }
        finishTime += Integer.valueOf(startTime) + 50 * countOfClass;
        String time = startTime;
        if (String.valueOf(finishTime).length() < 4) {
            time += "0";
        }
        time += String.valueOf(finishTime);
        return time;

    }

    /**
     * 将去除了随机三位数的课程码中的时间码提取出来，转换成文字描述并返回
     *
     * @param timeCode 去除了随机数的课程码
     * @return 上课时间的文字描述
     */
    public String changeToDescription(String timeCode) {

        String time = timeCode.substring(0, 4);
        String description = "周";
        switch (Integer.valueOf(time.substring(0, 1))) {
            case 1:
                description += "一 ";
                break;
            case 2:
                description += "二 ";
                break;
            case 3:
                description += "三 ";
                break;
            case 4:
                description += "四 ";
                break;
            case 5:
                description += "五 ";
                break;
            case 6:
                description += "六 ";
                break;
            case 7:
                description += "日 ";
                break;
            default:
                break;
        }
        int startClass = Integer.valueOf(time.substring(1, 3));
        description += startClass + "~" + (startClass + Integer.valueOf(time.substring(3, 4))) + "节";
        if (time.length() == 8) {
            description += " 和 " + changeToDescription(time.substring(4));
        }
        return description;
    }

}