package com.hdu.newe.here.utils

import android.support.v4.app.Fragment
import android.widget.Toast
import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener
import me.drakeet.multitype.MultiTypeAdapter

/**
 * @author Jaylen Hsieh
 * @date 2018/05/02
 */

fun MultiTypeAdapter.notifyItemChanged(refreshItems: List<*>) {
    items = refreshItems
    notifyDataSetChanged()
}


inline fun <T> BmobQuery<T>.query(
        crossinline onSuccess: (List<T>) -> Unit
) {
    findObjects(object : FindListener<T>() {
        override fun done(result: MutableList<T>?, e: BmobException?) {
            if (result != null) {
                onSuccess(result)
            }
        }
    })
}

internal inline fun <T> Fragment.bmobQueryById(
        objId: String?,
        crossinline onSuccess: (T) -> Unit
) {
    BmobQuery<T>().getObject(objId, object : QueryListener<T>() {
        override fun done(t: T, e: BmobException?) {
            if (e != null) {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
            } else {
                onSuccess(t)
            }
        }
    })
}

internal inline fun BmobObject.update(
        crossinline callback: (BmobException?) -> Unit
) {
    update(objectId, object : UpdateListener() {
        override fun done(e: BmobException?) {
            callback(e)
        }
    })
}