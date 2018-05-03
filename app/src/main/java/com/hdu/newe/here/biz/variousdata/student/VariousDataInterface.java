package com.hdu.newe.here.biz.variousdata.student;

import com.hdu.newe.here.biz.variousdata.student.bean.VariousDataBean;
import com.hdu.newe.here.page.base.BaseDataCallback;

/**
 * Model层接口 包含各种业务逻辑需要用到的方法以及与Presenter联系所用上的回调
 * @author pope
 * @date 2018/4/6
 */

public interface VariousDataInterface {

    interface OnVariousDataCallback extends BaseDataCallback {

        /**
         * 出勤数据获取成功回调出勤数据的Bean
         * @param variousDataBean 传入VariousDataBean
         */
        void onGetSuccess(VariousDataBean variousDataBean);

    }

    void getAttendanceData(String objectId,OnVariousDataCallback onVariousDataCallback);

}
