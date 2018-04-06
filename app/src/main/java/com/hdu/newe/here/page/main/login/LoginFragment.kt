package com.hdu.newe.here.page.main.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hdu.newe.here.R
import com.hdu.newe.here.biz.user.entity.UserBean
import com.hdu.newe.here.page.base.BaseFragment
import com.hdu.newe.here.utils.takeString
import kotlinx.android.synthetic.main.fragment_login.*
import kotlin.properties.Delegates


class LoginFragment : BaseFragment<LoginContract.Presenter>(), LoginContract.View {

    private var user: UserBean by Delegates.notNull()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun render() {

        btnLogin?.setOnClickListener {
            mPresenter.clickLogin(edUserNumber.takeString(), tvIMEI.takeString(), checkStatus.isChecked)
        }

//        view?.findViewById<TextView>(R.id.btnLogin)?.setOnClickListener{
//            user.userNumber = view?.findViewById<EditText>(R.id.edUserNumber)?.text.toString()
//            user.imei = view?.findViewById<TextView>(R.id.TvIMEI)?.text.toString()
//            user.isTeacher = view?.findViewById<CheckBox>(R.id.)?.isChecked ?: true
//        }
    }
}
