package com.hdu.newe.here.biz.user;

import com.hdu.newe.here.app.AppError;

/**
 * Created by Jaylen Hsieh on 2018/04/05.
 */
public interface UserInterface {

    interface LoginListener {

        void onStartLogin();

        void onLoginSuccess();

        /**
         * 失败时，传入错误原因，AppError里的 toString 方法可以避免每次都打字
         * @param error 错误原因
         */
        void onFailure(String error);
    }

    /**
     * 获取登录状态的监听，比如获得学号和 IMEI，是否是教师，填入 view 中
     */
    interface GetLoginInfoLister {

        void onSuccess();

        /**
         * 失败
         * @param error 原因
         */
        void onFailure(AppError error);
    }

    /**
     * 登录
     * @param lister 监听
     */
    void login(String userNumber, String imei, boolean isTeacher, LoginListener lister);


    /**
     * @return
     */
    boolean isUserLogin();
}
