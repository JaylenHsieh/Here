package com.hdu.newe.here.page.main.variousdata.student;

import com.hdu.newe.here.biz.variousdata.student.bean.LeaveRequestBean;
import com.hdu.newe.here.biz.variousdata.student.bean.VariousDataBean;
import com.hdu.newe.here.page.base.BasePresenter;
import com.hdu.newe.here.page.base.BaseView;

/**
 * @author pope
 * @date 2018/4/5
 */

public interface VariousDataContract {

    interface Presenter extends BasePresenter {

        /**
         * 获取多种数据
         */
        void getVariousData();

    }

    interface View extends BaseView<Presenter> {

        /**
         * 加载多种数据
         *
         * @param variousDataBean 被加载的数据
         */
        void loadVariousData(VariousDataBean variousDataBean);

        void loadHistoryData(String objectId);

        /**
         * 加载请假数据
         *
         * @param leaveRequestBean 请假数据bean
         */
        void loadLeaverequestData(LeaveRequestBean leaveRequestBean);
    }

}
