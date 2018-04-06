package com.hdu.newe.here.page.main.variousdata;

import com.hdu.newe.here.biz.ModelFactory;
import com.hdu.newe.here.biz.variousdata.VariousDataInterface;
import com.hdu.newe.here.page.base.BasePresenterImpl;

/**
 *
 * @author pope
 * @date 2018/4/5
 */

public class VariousDaraPresenter extends BasePresenterImpl implements VariousDataContract.Presenter {

    private VariousDataContract.View view;
    private VariousDataInterface variousDataInterface = ModelFactory.getVariousDataInterface();

    public VariousDaraPresenter(VariousDataContract.View view) {
        this.view = view;
        view.bindPresenter(this);
    }

    @Override
    public void getAttendanceData(String objectId) {

    }

    @Override
    public void getHistoryData(String objectId) {

    }

    @Override
    public void getBuffData(String objectId) {

    }
}
