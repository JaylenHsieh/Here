package com.hdu.newe.here.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import java.util.*

/**
 * Created by jonnyhsia
 * 日期时间选择的回调
 * 这里用 typealias 定义别名, 以 TimeSetCallback 命名 "参数为年月日时分五个整数, 返回 Unit" 类型的方法
 */
typealias TimeSetCallback = (year: Int, month: Int, dayOfMonth: Int, hour: Int, minute: Int) -> Unit

class AdvancedTimePicker(
        context: Context,
        calendar: Calendar = Calendar.getInstance(),
        is24Hour: Boolean = true,
        private val timeSetCallback: TimeSetCallback

) {

    /** 获取 Calendar 中的时间 */
    private var year = calendar.get(Calendar.YEAR)
    private var month = calendar.get(Calendar.MONTH)
    private var dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    private var hour = calendar.get(Calendar.HOUR_OF_DAY)
    private var minute = calendar.get(Calendar.MINUTE)

    /** 内部日期选择器的回调 */
    private val innerDateSetListener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
        year = y
        month = m
        dayOfMonth = d
        timePicker.show()
    }

    /** 内部时间选择器的回调 */
    private val innerTimeSetListener = TimePickerDialog.OnTimeSetListener { _, h, m ->
        hour = h
        minute = m
        // 选择完时间后, 执行日期时间的回调
        timeSetCallback(year, month, dayOfMonth, hour, minute)
    }

    /** 日期选择器 */
    private val datePicker: DatePickerDialog =
            DatePickerDialog(context, innerDateSetListener, year, month, dayOfMonth)

    /** 时间选择器 */
    private val timePicker: TimePickerDialog =
            TimePickerDialog(context, innerTimeSetListener, hour, minute, is24Hour).apply {
                // 当点击时间选择取消按钮返回选择日期
                setOnCancelListener { datePicker.show() }
            }

    /**
     * 显示 Picker
     */
    fun show() {
        datePicker.show()
    }
}