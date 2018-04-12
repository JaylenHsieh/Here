package com.hdu.newe.here.page.main.login


import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
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

    private val PERMISSION_REQUEST_READ_PHONE_STATE = 1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    arrayOf(android.Manifest.permission.READ_PHONE_STATE),
                    PERMISSION_REQUEST_READ_PHONE_STATE)
        } else {
            tvIMEI.text = "IMEI: " + getImei()
        }

        checkStatus.setOnCheckedChangeListener{ buttonView,isChecked ->
            if (isChecked == true){
                edUserNumber.hint = "工号"
                tvPrompt.text = "若没有您的工号将自动创建账号"
            }else{
                edUserNumber.hint = "学号"
                tvPrompt.text = "若没有您的学号将自动创建账号"
            }
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
        if (requestCode == PERMISSION_REQUEST_READ_PHONE_STATE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tvIMEI.setText(getImei())
            } else {
                Toast.makeText(context, "权限被拒绝，我们需要你的电话权限来获取IMEI", Toast.LENGTH_SHORT).show()
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getImei() :String{
        val telManager: TelephonyManager = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        try {
            return telManager.deviceId
        }catch (e:SecurityException){
            e.printStackTrace()
        }
        return "未获取到您的IMEI，请确保你的手机状态正常"
    }

}
