package com.hdu.newe.here.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle

/**
 * Created by Jaylen Hsieh on 2018/04/06.
 * @param args 对 bundle 的初始化的代码块（匿名函数）
 */
fun Activity.navigate(clz: Class<*>, requestCode: Int? = null, args: (Bundle.() -> Unit)? = null) {
    val intent = Intent(this, clz)

    if (args != null){
        val bundle = Bundle().apply(args)
        intent.putExtras(bundle)
    }

    if (requestCode == null) {
        startActivity(intent)
    } else {
        startActivityForResult(intent, requestCode)
    }
}
