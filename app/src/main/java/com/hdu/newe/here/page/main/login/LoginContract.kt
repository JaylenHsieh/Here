package com.hdu.newe.here.page.main.login

import com.hdu.newe.here.page.base.BasePresenter
import com.hdu.newe.here.page.base.BaseView

/**
 * Created by Jaylen Hsieh on 2018/04/05.
 */
interface LoginContract {
    interface Presenter : BasePresenter {

        fun clickLogin(userNumber: String, imei: String, isTeacher: Boolean, isInstructor: Boolean)

    }

    interface View : BaseView<Presenter>{
        fun render()

        fun loginSucceed()
    }
}