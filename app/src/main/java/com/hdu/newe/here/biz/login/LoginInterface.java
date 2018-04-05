package com.hdu.newe.here.biz.login;

import com.hdu.newe.here.app.AppError;

/**
 * Created by Jaylen Hsieh on 2018/04/05.
 */
public interface LoginInterface {

    interface LoginLister {
        /**
         * 成功时，把 User 传给 onSuccess 方法，
         * @param user 用户实体类
         */
        void onSuccess(UserBean user);

        /**
         * 失败时，传入错误原因，AppError里的 toString 方法可以避免每次都打字
         * @param error 错误原因
         */
        void onFailure(AppError error);
    }

    /**
     * 获取登录状态的监听，比如获得学号和 IMEI，是否是教师，等边填入 view 中
     */
    interface GetLoginInfoLister {
        /**
         * 成功，提供用户数据
         * @param user 用户
         */
        void onSuccess(UserBean user);

        /**
         * 失败
         * @param error 原因
         */
        void onFailuer(AppError error);
    }

    void getStoredUserLoginInfo(GetLoginInfoLister lister);

    /**
     * 登录
     * @param user 用户
     * @param lister 监听
     */
    void login(UserBean user, LoginLister lister);

    /**
     * 把用户的数据储存到手机
     */
    void saveLoginInfo(UserBean user);
}
