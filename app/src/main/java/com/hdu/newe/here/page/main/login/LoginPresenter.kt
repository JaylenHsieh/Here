package com.hdu.newe.here.page.main.login

import com.hdu.newe.here.biz.ModelFactory
import com.hdu.newe.here.biz.user.UserInterface

/**
 * Created by Jaylen Hsieh on 2018/04/05.
 * Presenter，控制 View 显示什么，能从 Model 存取什么信息，用 Presenter 来分离 View 和 Model
 */
class LoginPresenter(
        private val view: LoginContract.View
) : LoginContract.Presenter {

    init {
        view.bindPresenter(this)
    }

    private val loginInterface = ModelFactory.getUserInterface();

    override fun onCreate() {
    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun clickLogin(userNumber: String, imei: String, isTeacher: Boolean) {

        //TODO 判合法性

        loginInterface.login(userNumber, imei, isTeacher, object : UserInterface.LoginListener {
            override fun onStartLogin() {

            }

            override fun onLoginSuccess() {
                view.loginSucceed()
            }

            override fun onFailure(error: String) {
                view.showMessage(error)
            }
        })
    }

    override fun onDestroy() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }
}