package com.hdu.newe.here.biz.variousdata.student;

import android.content.Context;
import android.content.SharedPreferences;

import com.hdu.newe.here.biz.BaseLogic;
import com.hdu.newe.here.biz.variousdata.student.bean.LeaveRequestBean;
import com.hdu.newe.here.biz.variousdata.student.bean.VariousDataBean;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Moael层业务逻辑 继承Model层接口并实现其中的方法
 *
 * @author pope
 * @date 2018/4/6
 */

public class VariousDataLogic extends BaseLogic implements VariousDataInterface {

    private static VariousDataLogic INSTANCE;
    private String objectId;
    private String leaveObjId;

    /**
     * 获取数据的Model层数据逻辑
     *
     * @param onVariousDataCallback 相应的数据回调对象
     */
    @Override
    public void getAttendanceData(final OnVariousDataCallback onVariousDataCallback) {
        if (objectId == null || leaveObjId == null) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
            objectId = sharedPreferences.getString("objId", null);
            leaveObjId = sharedPreferences.getString("userLeaveObjId", null);
        }
        onVariousDataCallback.onStartGetData();
        BmobQuery<VariousDataBean> query = new BmobQuery<>();
        query.addWhereEqualTo("userObjectId", objectId);
        query.findObjects(new FindListener<VariousDataBean>() {
            @Override
            public void done(List<VariousDataBean> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.isEmpty()) {
                        onVariousDataCallback.onGetFailed("无出勤率数据");
                    } else {
                        onVariousDataCallback.onGetSuccess(list.get(0));
                    }
                } else {
                    onVariousDataCallback.onGetFailed("error471654"+e.getMessage());
                }
            }
        });

        BmobQuery<LeaveRequestBean> query1 = new BmobQuery<>();
        query1.addWhereEqualTo("userObjectId", objectId);
        query1.findObjects(new FindListener<LeaveRequestBean>() {
            @Override
            public void done(List<LeaveRequestBean> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.isEmpty()) {
                        onVariousDataCallback.onGetFailed("无请假数据");
                    } else {
                        onVariousDataCallback.onGetSuccess(list.get(0));
                    }
                } else {
                    onVariousDataCallback.onGetFailed("error913246" + e.getMessage());
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
