package com.hdu.newe.here.page.main.variousdata.student;

import com.hdu.newe.here.biz.ModelFactory;
import com.hdu.newe.here.biz.variousdata.student.VariousDataInterface;
import com.hdu.newe.here.biz.variousdata.student.bean.LeaveRequestBean;
import com.hdu.newe.here.biz.variousdata.student.bean.VariousDataBean;
import com.hdu.newe.here.page.base.BasePresenterImpl;

/**
 *
 * @author pope
 * @date 2018/4/5
 */

public class VariousDataPresenter extends BasePresenterImpl implements VariousDataContract.Presenter {

    private VariousDataContract.View view;
    private VariousDataInterface variousDataInterface = ModelFactory.getVariousDataInterface();

    public VariousDataPresenter(VariousDataContract.View view) {
        this.view = view;
        view.bindPresenter(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getVariousData();
    }

    @Override
    public void getVariousData() {

        variousDataInterface.getAttendanceData(new VariousDataInterface.OnVariousDataCallback() {

            @Override
            public void onGetSuccess(VariousDataBean variousDataBean) {
                view.loadVariousData(variousDataBean);
            }

            @Override
            public void onGetSuccess(LeaveRequestBean leaveRequestBean) {
                view.loadLeaverequestData(leaveRequestBean);
            }

            @Override
            public void onStartGetData() {
                // 根据需要添加
            }

            @Override
            public void onGetSuccess() {
                // 根据需要添加
            }

            @Override
            public void onGetFailed(String errorMsg) {
                view.showMessage(errorMsg);
            }
        });

    }
}
