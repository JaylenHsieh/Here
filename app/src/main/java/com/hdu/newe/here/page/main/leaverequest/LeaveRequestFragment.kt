package com.hdu.newe.here.page.main.leaverequest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.Toolbar

import com.hdu.newe.here.R
import com.hdu.newe.here.utils.AdvancedTimePicker
import kotlinx.android.synthetic.main.fragment_leave_request.*

import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class LeaveRequestFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_leave_request, container, false)
        val mToolbar = view.findViewById<Toolbar>(R.id.toolbar)
        mToolbar.setNavigationOnClickListener(){
//            val fragmentTransaction = fragmentManager!!.beginTransaction()
//            fragmentTransaction.remove(this)
//            fragmentTransaction.commit()
            activity?.finish()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = (calendar.get(Calendar.MONTH) + 1)
        var dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        var hour = calendar.get(Calendar.HOUR_OF_DAY)like "%02d"
        var minute = calendar.get(Calendar.MINUTE) like "%02d"
        val st = "${year}年${month}月${dayOfMonth}日 ${hour}:$minute"

        //默认请假时间2小时
        val timezone = TimeZone.getTimeZone("GMT+10")
        calendar.timeZone = timezone
        year = calendar.get(Calendar.YEAR)
        month = (calendar.get(Calendar.MONTH) + 1)
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        hour = calendar.get(Calendar.HOUR_OF_DAY)like "%02d"
        minute = calendar.get(Calendar.MINUTE) like "%02d"
        val ft = "${year}年${month}月${dayOfMonth}日 ${hour}:$minute"
        startTime.text = st
        finishTime.text = ft
        startTime.setOnClickListener(this)
        finishTime.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.startTime -> {
                AdvancedTimePicker(context!!) { year, month, dayOfMonth, hour, minute ->
                    startTime.text = ("${year}年${month + 1}月${dayOfMonth}日$hour:$minute")
                    finishTime.text = ("${year}年${month + 1}月${dayOfMonth}日${hour + 2}:$minute")
                }.show()
            }
            R.id.finishTime -> {
                AdvancedTimePicker(context!!) { year, month, dayOfMonth, hour, minute ->
                    finishTime.text = ("${year}年${month + 1}月${dayOfMonth}日$hour:$minute")
                }.show()
            }
        }
    }


    infix fun Int.like(pattern: String  ):String{
        return String.format(pattern,this)
    }
}
