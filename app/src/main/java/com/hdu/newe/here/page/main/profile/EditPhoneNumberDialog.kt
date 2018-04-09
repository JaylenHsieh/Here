package com.hdu.newe.here.page.main.profile

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.ViewGroup
import com.hdu.newe.here.R
import com.hdu.newe.here.utils.UIUtils

/**
 * Created by Jaylen Hsieh on 2018/03/26.
 */
class EditPhoneNumberDialog: DialogFragment(){
    private fun EditPhoneNumberFragment(){
        //Empty constructor
    }


    override fun onCreateDialog(savedInstanceState: Bundle?):AlertDialog {
        val builder = AlertDialog.Builder(activity)
        val inflate = activity?.layoutInflater?.inflate(R.layout.fragment_edit_phone_number_dialog, null, false)
        return builder.setView(inflate)
                .setCancelable(true)
                .setMessage("申请更换手机")
                .setPositiveButton("提交") { dialog, which ->
                }.setNegativeButton("取消") { dialog, which ->
                }.create()
    }

    override fun onResume() {
        super.onResume()
        dialog.setTitle("更换手机")
    }
}