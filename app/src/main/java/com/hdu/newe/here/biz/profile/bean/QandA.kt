package com.hdu.newe.here.biz.profile.bean

import cn.bmob.v3.BmobObject

/**
 * @author Jaylen Hsieh
 * @date 2018/05/02
 */
data class QandA(
        val question: String,
        val answer: String
): BmobObject()