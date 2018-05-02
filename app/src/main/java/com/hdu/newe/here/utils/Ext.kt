package com.hdu.newe.here.utils

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import me.drakeet.multitype.MultiTypeAdapter

/**
 * @author Jaylen Hsieh
 * @date 2018/05/02
 */

fun MultiTypeAdapter.notifyItemChanged(refreshItems: List<*>){
    items = refreshItems
    notifyDataSetChanged()
}
inline fun <T> BmobQuery<T>.query(
        crossinline onSuccess: (List<T>) -> Unit
){
    findObjects(object  : FindListener<T>() {
        override fun done(result: MutableList<T>?, e: BmobException?) {
            if (result != null){
                onSuccess(result)
            }
        }
    })
}