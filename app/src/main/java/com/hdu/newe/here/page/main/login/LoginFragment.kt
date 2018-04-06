package com.hdu.newe.here.page.main.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hdu.newe.here.R
import com.hdu.newe.here.app.AppError
import com.hdu.newe.here.biz.login.UserBean
import com.hdu.newe.here.page.base.BaseFragment


class LoginFragment : BaseFragment<LoginContract.Presenter>(), LoginContract.View {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun showLoggingIndicator() {

    }

    override fun showLoginSuccess(stuNumber: String) {

    }

    override fun showLoginFailure(description: String) {

    }

    override fun showLoginInfo(user: UserBean) {

    }

    override fun showFailureMessage(error: AppError) {

    }

}
