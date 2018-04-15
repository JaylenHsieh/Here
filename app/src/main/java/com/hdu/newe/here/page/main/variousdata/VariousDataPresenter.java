package com.hdu.newe.here.page.main.variousdata;

import com.hdu.newe.here.biz.ModelFactory;
import com.hdu.newe.here.biz.variousdata.VariousDataInterface;
import com.hdu.newe.here.biz.variousdata.bean.VariousDataBean;
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
        getVariousData("eebc694aa0");
    }

    @Override
    public void getVariousData(String objectId) {

        variousDataInterface.getAttendanceData(objectId, new VariousDataInterface.OnVariousDataCallback() {

            @Override
            public void onGetSuccess(VariousDataBean variousDataBean) {
                view.loadVariousData(variousDataBean);
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
