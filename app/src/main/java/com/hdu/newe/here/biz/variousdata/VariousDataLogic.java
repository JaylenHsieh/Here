package com.hdu.newe.here.biz.variousdata;

import com.hdu.newe.here.biz.BaseLogic;
import com.hdu.newe.here.biz.variousdata.bean.VariousDataBean;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

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
        BmobQuery<VariousDataBean> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<VariousDataBean>() {
            @Override
            public void done(VariousDataBean variousDataBean, BmobException e) {
                if (e == null) {
                    onVariousDataCallback.onGetSuccess(variousDataBean);
                } else {
                    onVariousDataCallback.onGetFailed(e.getMessage());
                }
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
