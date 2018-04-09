package com.hdu.newe.here.page.main.profile


import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment

import com.hdu.newe.here.R


/**
 * A simple [Fragment] subclass.
 *
 */
class FeedbackDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        val builder = AlertDialog.Builder(activity)
        val inflate = activity?.layoutInflater?.inflate(R.layout.fragment_edit_phone_number_dialog, null, false)
        return builder.setView(inflate)
                .setCancelable(true)
                .setMessage("请输入您的宝贵建议")
                .setPositiveButton("提交") { dialog, which ->
                }.setNegativeButton("取消") { dialog, which ->
                }.create()
    }


}
