package com.hdu.newe.here.page.main.login


import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.hdu.newe.here.R
import com.hdu.newe.here.biz.user.entity.UserBean
import com.hdu.newe.here.page.base.BaseFragment
import com.hdu.newe.here.utils.takeString
import kotlinx.android.synthetic.main.fragment_login.*
import kotlin.properties.Delegates


class LoginFragment : BaseFragment<LoginContract.Presenter>(), LoginContract.View {

    private var user: UserBean by Delegates.notNull()

    private val PERMISSION_REQUEST_CALL_PHONE = 1

    //private val telManager:TelephonyManager = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onResume() {
        super.onResume()
        if (checkSelfPermission(context!!, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    PERMISSION_REQUEST_CALL_PHONE)
        } else {
            getImei()
        }
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CALL_PHONE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getImei()
            }else{
                Toast.makeText(context,"    权限被拒绝", Toast.LENGTH_SHORT).show()
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getImei(){
//        telManager.getImei()
    }

}
