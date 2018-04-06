package com.hdu.newe.here.page.base;

/**
 * @author pope
 */
public interface BmobQueryListener<T> {

    /**
     * 查询成功时运行的方法
     * @param data 传入查询到的数据
     */
    void onSuccess(T data);

    /**
     * 查询失败时运行的方法
     * @param e 传入异常信息
     */
    void onFailed(String e);
}