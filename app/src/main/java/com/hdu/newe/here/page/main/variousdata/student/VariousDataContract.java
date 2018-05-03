package com.hdu.newe.here.page.main.variousdata.student;

import com.hdu.newe.here.biz.variousdata.student.bean.VariousDataBean;
import com.hdu.newe.here.page.base.BasePresenter;
import com.hdu.newe.here.page.base.BaseView;

/**
 *
 * @author pope
 * @date 2018/4/5
 */

public interface VariousDataContract {

    interface Presenter extends BasePresenter{

        void getVariousData(String objectId);

    }

    interface View extends BaseView<Presenter> {

        void loadVariousData(VariousDataBean variousDataBean);

        void loadHistoryData(String objectId);
    }

}
