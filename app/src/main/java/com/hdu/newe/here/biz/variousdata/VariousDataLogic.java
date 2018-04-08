package com.hdu.newe.here.biz.variousdata;

import com.hdu.newe.here.biz.BaseLogic;
import com.hdu.newe.here.biz.variousdata.bean.VariousDataBean;
import com.hdu.newe.here.page.base.BmobQueryListener;
import com.hdu.newe.here.utils.BmobUtil;

/**
 * Moael层业务逻辑 继承Model层接口并实现其中的方法
 *
 * @author pope
 * @date 2018/4/6
 */

public class VariousDataLogic extends BaseLogic implements VariousDataInterface {

    private static VariousDataLogic INSTANCE;

    /**
     * 获取数据的Model层数据逻辑
     *
     * @param objectId              需要查询的用户的objectId
     * @param onVariousDataCallback 相应的数据回调对象
     */
    @Override
    public void getAttendanceData(String objectId, final OnVariousDataCallback onVariousDataCallback) {
        onVariousDataCallback.onStartGetData();
        BmobUtil.queryById(objectId, new BmobQueryListener<VariousDataBean>() {
            @Override
            public void onSuccess(VariousDataBean data) {
                onVariousDataCallback.onGetSuccess();
            }

            @Override
            public void onFailed(String e) {
                onVariousDataCallback.onGetFailed(e);
            }
        });
    }

    private static class Holder {
        static VariousDataLogic INSTANCE = new VariousDataLogic();
    }

    public static VariousDataLogic getInstance() {
        return Holder.INSTANCE;
    }

    private VariousDataLogic() {
    }
}
