package com.hdu.newe.here.biz.variousdata;

import android.content.Context;

import com.hdu.newe.here.bean.AttendanceDataBean;
import com.hdu.newe.here.bean.BuffDataBean;
import com.hdu.newe.here.bean.HistoryDataBean;
import com.hdu.newe.here.biz.BaseLogic;

import java.lang.ref.WeakReference;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

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
        BmobQuery<AttendanceDataBean> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<AttendanceDataBean>() {
            @Override
            public void done(AttendanceDataBean attendanceDataBean, BmobException e) {
                if (e == null){
                    //登录成功 回调中传入出勤率数据
                    onAttendanceDataCallback.onGetSuccess(attendanceDataBean);
                }else{
                    //登录失败 回调中传入错误信息
                    onAttendanceDataCallback.onGetFailed(e.toString());
                }
            }
        });
    }


    @Override
    public void getHistoryData(String objectId, final OnHistoryDataCallback onHistoryDataCallback) {
        onHistoryDataCallback.onStartGetData();
        BmobQuery<HistoryDataBean> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<HistoryDataBean>() {
            @Override
            public void done(HistoryDataBean historyDataBean, BmobException e) {
                if (e == null){
                    onHistoryDataCallback.onGetSuccess(historyDataBean);
                }else{
                    onHistoryDataCallback.onGetFailed(e.toString());
                }
            }
        });
    }

    @Override
    public void getBuffData(String objectId, final OnBuffDataCallback onBuffDataCallback) {
        onBuffDataCallback.onStartGetData();
        BmobQuery<BuffDataBean> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<BuffDataBean>() {
            @Override
            public void done(BuffDataBean buffDataBean, BmobException e) {
                if (e == null){
                    onBuffDataCallback.onGetSuccess(buffDataBean);
                }else{
                    onBuffDataCallback.onGetFailed(e.toString());
                }
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
