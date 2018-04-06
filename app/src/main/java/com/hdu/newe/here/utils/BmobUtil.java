package com.hdu.newe.here.utils;

import com.hdu.newe.here.page.base.BmobQueryListener;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Bmob工具类
 * @author pope
 * @date 2018/4/6
 */

public class BmobUtil {

    /**
     * Bmob简易查询
     * @param objectId 需要查询的objectId
     * @param listener Bmob查询监听器
     * @param <T> 需要获取的目标数据类型
     */
    public static <T> void queryById(String objectId, final BmobQueryListener<T> listener) {
        BmobQuery<T> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<T>() {
            @Override
            public void done(T t, BmobException e) {
                if (e == null) {
                    listener.onSuccess(t);
                } else {
                    listener.onFailed(e.getMessage());
                }
            }
        });
    }
}
