package com.hdu.newe.here.page.main.variousdata;

import com.hdu.newe.here.biz.databean.AttendaceBean;
import com.hdu.newe.here.biz.databean.BuffBean;
import com.hdu.newe.here.biz.databean.HistoryBean;
import com.hdu.newe.here.biz.databean.SubjectBean;

import java.util.List;

/**
 *
 * @author pope
 * @date 2018/4/5
 */

public interface VariousDataContarct {

    interface Presenter{

        void getSubjectData(String objectId);

        void getAttendaceData(String objectId);

        void getHistoryData(String objectId);

        void getBuffData(String objectId);

    }

    interface View{

        void loadAttendanceData(List<SubjectBean> subjectList, List<AttendaceBean> attendaceList);

        void loadHistoryData(List<HistoryBean> historyList);

        void loadBuffData(List<BuffBean> buffList);

    }

}
