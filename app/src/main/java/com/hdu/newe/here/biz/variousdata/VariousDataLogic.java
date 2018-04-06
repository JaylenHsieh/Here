package com.hdu.newe.here.biz.variousdata;

import android.content.Context;

import com.hdu.newe.here.bean.AttendanceDataBean;
import com.hdu.newe.here.bean.BuffDataBean;
import com.hdu.newe.here.bean.HistoryDataBean;
import com.hdu.newe.here.page.base.BmobQueryListener;
import com.hdu.newe.here.utils.BmobUtil;
import com.hdu.newe.here.biz.BaseLogic;

import java.lang.ref.WeakReference;

/**
 * Moael层业务逻辑 继承Model层接口并实现其中的方法
 * @author pope
 * @date 2018/4/6
 */

public class VariousDataLogic extends BaseLogic implements VariousDataInterface {

    private static VariousDataLogic INSTANCE;

    /**
     * 获取出勤率的Model层数据逻辑
     * @param objectId 需要查询的用户的objectId
     * @param onAttendanceDataCallback 相应的数据回调对象
     */
    @Override
    public void getAttendanceData(String objectId, final OnAttendanceDataCallback onAttendanceDataCallback) {
        //开始获取数据
        onAttendanceDataCallback.onStartGetData();
        //利用BmobQuery查询表中数据
        BmobUtil.queryById(objectId, new BmobQueryListener<AttendanceDataBean>() {
            @Override
            public void onSuccess(AttendanceDataBean data) {
                onAttendanceDataCallback.onGetSuccess(data);
            }

            @Override
            public void onFailed(String e) {
                onAttendanceDataCallback.onGetFailed(e);
            }
        });
    }


    @Override
    public void getHistoryData(String objectId, final OnHistoryDataCallback onHistoryDataCallback) {
        onHistoryDataCallback.onStartGetData();
        BmobUtil.queryById(objectId, new BmobQueryListener<HistoryDataBean>() {
            @Override
            public void onSuccess(HistoryDataBean data) {
                onHistoryDataCallback.onGetSuccess(data);
            }

            @Override
            public void onFailed(String e) {
                onHistoryDataCallback.onGetFailed(e);
            }
        });
    }

    @Override
    public void getBuffData(String objectId, final OnBuffDataCallback onBuffDataCallback) {
        onBuffDataCallback.onStartGetData();

        BmobUtil.queryById(objectId, new BmobQueryListener<BuffDataBean>() {
            @Override
            public void onSuccess(BuffDataBean data) {
                onBuffDataCallback.onGetSuccess(data);
            }

            @Override
            public void onFailed(String e) {
                onBuffDataCallback.onGetFailed(e);
            }
        });
    }

    private static class Holder {
        static VariousDataLogic INSTANCE = new VariousDataLogic();
    }

    public static VariousDataLogic getInstance(){
        return Holder.INSTANCE;
    }

    private VariousDataLogic(){
    }
}
