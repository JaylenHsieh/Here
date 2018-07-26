package com.hdu.newe.here.page.main.leaverequest

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.Toolbar
import android.widget.Toast
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener

import com.hdu.newe.here.R
import com.hdu.newe.here.biz.variousdata.student.bean.LeaveRequestBean
import com.hdu.newe.here.biz.user.entity.UserBean
import com.hdu.newe.here.page.main.profile.PersonalInfoActivity
import com.hdu.newe.here.utils.AdvancedTimePicker
import kotlinx.android.synthetic.main.fragment_leave_request.*

import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class LeaveRequestFragment : Fragment(), View.OnClickListener {

    var leaveRequestReasonList: MutableList<String> = mutableListOf()
    var leaveRequestTimeList: MutableList<String> = mutableListOf()
    var leaveRequestState: MutableList<String> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_leave_request, container, false)
        val mToolbar = view.findViewById<Toolbar>(R.id.toolbar)
        mToolbar.setNavigationOnClickListener() {
            activity?.finish()
        }

        return view
    }

    override fun onResume() {
        super.onResume()


        val bmobUserQuery = BmobQuery<UserBean>()
        val objectId = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)?.getString("objId", "")

        bmobUserQuery.getObject(objectId, object : QueryListener<UserBean>() {
            override fun done(user: UserBean, e: BmobException?) {
                mTvUserName.text = user.userName
                mTvUserNumber.text = user.userNumber
                mTvStuFaculty.text = user.userCollege
                mTvStuSpeciality.text = user.userMajor
                mTvStuInstructor.text = user.userInstructor
            }
        })


        val bmobQuery = BmobQuery<LeaveRequestBean>()
        val leaveRequestObjId = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)?.getString(PersonalInfoActivity.LEAVE_REQUEST_OBJ_ID, "")
        bmobQuery.getObject(leaveRequestObjId, object : QueryListener<LeaveRequestBean>() {
            override fun done(leaveRequest: LeaveRequestBean, e: BmobException?) {
                if (e == null) {
                    leaveRequestReasonList = leaveRequest.leaveRequestReason
                    leaveRequestTimeList = leaveRequest.leaveRequestTime
                } else {
                    Toast.makeText(context, "获取信息失败" + e.message, Toast.LENGTH_SHORT).show()
                }
            }
        })


        val calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = (calendar.get(Calendar.MONTH) + 1)
        var dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        var hour = calendar.get(Calendar.HOUR_OF_DAY) like "%02d"
        var minute = calendar.get(Calendar.MINUTE) like "%02d"
        val st = "${year}年${month}月${dayOfMonth}日 ${hour}:$minute"

        //默认请假时间2小时
        val timezone = TimeZone.getTimeZone("GMT+10")
        calendar.timeZone = timezone
        year = calendar.get(Calendar.YEAR)
        month = (calendar.get(Calendar.MONTH) + 1)
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        hour = calendar.get(Calendar.HOUR_OF_DAY) like "%02d"
        minute = calendar.get(Calendar.MINUTE) like "%02d"
        val ft = "${year}年${month}月${dayOfMonth}日 ${hour}:$minute"
        tvStartTime.text = st
        tvFinishTime.text = ft
        tvStartTime.setOnClickListener(this)
        tvFinishTime.setOnClickListener(this)
        view_submit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvStartTime -> {
                AdvancedTimePicker(context!!) { year, month, dayOfMonth, hour, minute ->
                    tvStartTime.text = ("${year}年${month + 1}月${dayOfMonth}日$hour:$minute")
                    tvFinishTime.text = ("${year}年${month + 1}月${dayOfMonth}日${hour + 2}:$minute")
                }.show()
            }
            R.id.tvFinishTime -> {
                AdvancedTimePicker(context!!) { year, month, dayOfMonth, hour, minute ->
                    tvFinishTime.text = ("${year}年${month + 1}月${dayOfMonth}日$hour:$minute")
                }.show()
            }
            R.id.view_submit -> {
                val leaveRequest = LeaveRequestBean()
                leaveRequest.setUserName(mTvUserName.text.toString())
                leaveRequest.setUserNumber(mTvUserNumber.text.toString())
                leaveRequest.setUserMajor(mTvStuSpeciality.text.toString())
                leaveRequest.setUserCollege(mTvStuFaculty.text.toString())
                leaveRequest.setUserInstructor(mTvStuInstructor.text.toString())
                leaveRequestReasonList.add(edRequestContent.text.toString())
                leaveRequest.leaveRequestReason = leaveRequestReasonList
                leaveRequestTimeList.add(tvStartTime.text.toString())
                leaveRequestTimeList.add(tvFinishTime.text.toString())
                leaveRequest.leaveRequestTime = leaveRequestTimeList
                val leaveRequestObjId = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)?.getString(PersonalInfoActivity.LEAVE_REQUEST_OBJ_ID, "")
                leaveRequest.update(leaveRequestObjId, object : UpdateListener() {
                    override fun done(e: BmobException?) {
                        if (e == null) {
                            Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "提交失败" + e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }


    infix fun Int.like(pattern: String): String {
        return String.format(pattern, this)
    }
}
