package com.hdu.newe.here.page.main.variousdata;

import com.hdu.newe.here.bean.AttendanceDataBean;
import com.hdu.newe.here.bean.BuffDataBean;
import com.hdu.newe.here.bean.HistoryDataBean;
import com.hdu.newe.here.page.base.BasePresenter;
import com.hdu.newe.here.page.base.BaseView;

import java.util.List;

/**
 *
 * @author pope
 * @date 2018/4/5
 */

public interface VariousDataContract {

    interface Presenter extends BasePresenter{

        void getAttendanceData(String objectId);

        void getHistoryData(String objectId);

        void getBuffData(String objectId);

    }

    interface View extends BaseView<Presenter> {

        void loadAttendanceData(List<AttendanceDataBean> attendanceDataBeanList);

        void loadHistoryData(List<HistoryDataBean> historyDataBeanList);

        void loadBuffHistoryData(List<BuffDataBean> buffDataBeanList);

    }

}
