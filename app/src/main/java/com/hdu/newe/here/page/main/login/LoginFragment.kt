package com.hdu.newe.here.page.main.login


import android.content.Context
import android.content.Intent
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
import com.hdu.newe.here.page.main.MainActivity
import com.hdu.newe.here.utils.takeString
import kotlinx.android.synthetic.main.fragment_login.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import kotlin.properties.Delegates


class LoginFragment : BaseFragment<LoginContract.Presenter>(),
        LoginContract.View,
        EasyPermissions.PermissionCallbacks {

    private var user: UserBean by Delegates.notNull()

    private val PERMISSION_REQUEST_READ_PHONE_STATE = 1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        requestPermission()
        return view
    }

    override fun onResume() {
        super.onResume()
        render()
        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    arrayOf(android.Manifest.permission.READ_PHONE_STATE,
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA),
                    PERMISSION_REQUEST_READ_PHONE_STATE)
        } else {
            tvIMEI.text = getImei()
        }



        checkStatus.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tvUserNumber.text = "工号:"
                checkStatusIsInstructor.visibility = View.VISIBLE
            } else {
                tvUserNumber.text = "学号:"
            }
        }

        checkIsRead.setOnCheckedChangeListener { _, isChecked ->
            btnLogin.isEnabled = isChecked
        }


        //TODO 用户须知的checkbox设为不可点击
        //TODO 用户须知点击监听 跳转至LoginNoticeFragemnt
        //TODO 创建一个public的方法用来传递用户是否完成《用户须知》的阅读 传递参数 boolean isRead
        checkIsRead.setOnClickListener {
            LoginNoticeBottomSheetFragment().show(activity!!.supportFragmentManager, "dialog")
        }
    }

    override fun render() {

        btnLogin.setOnClickListener {
            Toast.makeText(context, "请稍等...", Toast.LENGTH_LONG).show()
            mPresenter.clickLogin(edUserNumber.takeString(), tvIMEI.takeString(), checkStatus.isChecked, checkStatusIsInstructor.isChecked)
        }
    }


    private fun requestPermission() {
        val perms = arrayOf(
                // 需要申请的权限，虽然有点多，但是都是必要权限，我们不是流氓软件
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA)
        if (EasyPermissions.hasPermissions(context!!, *perms)) {
            tvIMEI?.text = getImei()
        } else {
            Toast.makeText(context, "权限被拒绝，我们需要你的电话权限来获取IMEI", Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_READ_PHONE_STATE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tvIMEI.text = getImei()
            } else {
                Toast.makeText(context, "权限被拒绝，我们需要你的电话权限来获取IMEI", Toast.LENGTH_SHORT).show()
                AppSettingsDialog.Builder(this).build().show()
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    /**
     * 获取手机 IMEI
     */
    private fun getImei(): String {
        val telManager: TelephonyManager = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        try {
            return telManager.deviceId
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
        return "未获取到您的IMEI，请确保你的手机状态正常"
    }

    override fun loginSucceed() {
        startActivity(Intent(context, MainActivity::class.java))
        activity?.finish()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(context, "权限被拒绝，我们需要你的电话权限来获取IMEI", Toast.LENGTH_SHORT).show()
        AppSettingsDialog.Builder(this).build().show()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        tvIMEI.text = getImei()
    }

    companion object {
        private const val PERMISSION_REQUEST = 111
    }
}
