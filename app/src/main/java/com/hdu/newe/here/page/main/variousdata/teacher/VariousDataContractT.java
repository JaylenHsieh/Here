package com.hdu.newe.here.page.main.variousdata.teacher;

import com.hdu.newe.here.biz.signin.bean.SignInDataBean;
import com.hdu.newe.here.biz.variousdata.teacher.bean.UserBeanForList;
import com.hdu.newe.here.page.base.BasePresenter;
import com.hdu.newe.here.page.base.BaseView;

import java.util.List;

/**
 * @author pope
 * @date 2018/5/3
 */

public interface VariousDataContractT {

    interface Presenter extends BasePresenter {

        /**
         * 获取该老师所带班级的数据 若老师为辅导员，则显示其所带自然班以及教学班的数据
         * 若老师仅是教职工非辅导员则显示其教学班数据
         */
        void getClassData();

        /**
         * 获取某班级的出勤率数据
         * @param classId 被查询班级Id
         */
        void getAttendanceData(String classId);

        void getAbsentData(String classId);

        void getBuffData(String classId);

        void getLeaveRequestData(String classId);

        void getWarningData(String classId);

        void getCheckData(String subjectId);

    }

    interface View extends BaseView<Presenter> {

        /**
         * 加载班级数据方法
         * @param classList 传入该老师所带班级和所教班级的列表
         * @param classCount 传入该老师所带班级数量
         * @param checkTimes 传入该老师所教班级点过名的数量
         */
        void loadClassData(List<String> classList,int classCount,List<Integer> checkTimes);

        /**
         * 加载考勤数据方法
         * @param signInDataBean 传入需要被加载的SignInDataBean
         */
        void loadCheckData(SignInDataBean signInDataBean);

        /**
         * 加载学生考勤数据中列表的数据的方法
         * @param userBeanForList 学生简易用户数据集合
         * @param pos 加载位置
         */
        void loadListData(List<UserBeanForList> userBeanForList,int pos);

        void loadAttendanceData(List<String> studentNameList,List<Number> studentAttendanceRateList);

        void loadAbsentData();

        void loadBuffData();

        void loadLeaveRequestData();

        void loadWarningData();

        /**
         * 显示dialog
         */
        void showDialog(String txt);

    }

}
