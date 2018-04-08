package com.hdu.newe.here.biz.variousdata;

import com.hdu.newe.here.bean.AttendanceDataBean;
import com.hdu.newe.here.bean.BuffDataBean;
import com.hdu.newe.here.bean.HistoryDataBean;
import com.hdu.newe.here.page.base.BaseDataCallback;

/**
 * Model层接口 包含各种业务逻辑需要用到的方法以及与Presenter联系所用上的回调
 * @author pope
 * @date 2018/4/6
 */

public interface VariousDataInterface {

    interface OnAttendanceDataCallback extends BaseDataCallback {

        /**
         * 出勤数据获取成功回调出勤数据的Bean
         * @param attendanceDataBean 传入AttendanceDataBean
         */
        void onGetSuccess(AttendanceDataBean attendanceDataBean);

    }

    interface OnHistoryDataCallback extends BaseDataCallback{

        /**
         * 历史数据获取成功回调历史数据的Bean
         * @param historyDataBean 传入HistoryDataBean
         */
        void onGetSuccess(HistoryDataBean historyDataBean);
    }

    interface OnBuffDataCallback extends BaseDataCallback{

        /**
         * 标识数据获取成功回调出标识数据的Bean
         * @param buffDataBean 传入BuffDataBean
         */
        void onGetSuccess(BuffDataBean buffDataBean);

    }

    void getAttendanceData(String objectId,OnAttendanceDataCallback onAttendanceDataCallback);

    void getHistoryData(String objectId,OnHistoryDataCallback onHistoryDataCallback);

    void getBuffData(String objectId,OnBuffDataCallback onBuffDataCallback);

}
