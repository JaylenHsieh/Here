package com.hdu.newe.here.page.main.login

import com.hdu.newe.here.biz.login.UserBean

/**
 * Created by Jaylen Hsieh on 2018/04/05.
 * Presenter，控制 View 显示什么，能从 Model 存取什么信息，用 Presenter 来分离 View 和 Model
 */
class LoginPresenter(
        private val view: LoginContract.View
) : LoginContract.Presenter{
    override fun clickLogin(user: UserBean) {

    }

    override fun onCreate() {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onDestroy() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }
}