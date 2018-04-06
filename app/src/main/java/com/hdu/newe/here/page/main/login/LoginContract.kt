package com.hdu.newe.here.page.main.login

import com.hdu.newe.here.app.AppError
import com.hdu.newe.here.biz.login.UserBean
import com.hdu.newe.here.page.base.BasePresenter
import com.hdu.newe.here.page.base.BaseView

/**
 * Created by Jaylen Hsieh on 2018/04/05.
 */
interface LoginContract {
    interface Presenter : BasePresenter {
        fun clickLogin(user: UserBean)
    }

    interface View : BaseView<Presenter>{
        fun showLoggingIndicator()

        fun showLoginSuccess(stuNumber: String)

        fun showLoginFailure(description: String)

        fun showLoginInfo(user: UserBean)

        fun showFailureMessage(error: AppError)
    }
}