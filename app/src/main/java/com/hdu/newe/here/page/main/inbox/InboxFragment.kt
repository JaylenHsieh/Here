package com.hdu.newe.here.page.main.inbox


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import com.hdu.newe.here.R
import com.hdu.newe.here.biz.variousdata.student.bean.LeaveRequestBean
import com.hdu.newe.here.page.main.profile.PersonalInfoActivity
import kotlinx.android.synthetic.main.fragment_inbox.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class InboxFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inbox, container, false)
    }

    override fun onResume() {
        super.onResume()
        layout_homework.setOnClickListener { Toast.makeText(context, "布置作业功能开发中", Toast.LENGTH_LONG).show() }


        val leaveRequestObjId = activity?.
                getSharedPreferences("user", Context.MODE_PRIVATE)?.
                getString(PersonalInfoActivity.LEAVE_REQUEST_OBJ_ID, "")

        if(leaveRequestObjId.equals("")){
            Toast.makeText(context,"请先完善个人信息",Toast.LENGTH_LONG).show()
            var intent = Intent()
            intent.setClass(context,PersonalInfoActivity().javaClass)
            startActivity(intent)
        }

        val bmobQuery = BmobQuery<LeaveRequestBean>()
        bmobQuery.getObject(leaveRequestObjId, object : QueryListener<LeaveRequestBean>() {
            override fun done(leaveRequest: LeaveRequestBean, e: BmobException?) {
                if (e == null) {
                    if (leaveRequest.leaveRequestReason.isEmpty() || leaveRequest.leaveRequestState.isEmpty()){
                        return
                    }
                    tvRequestReason.text = leaveRequest.leaveRequestReason.last()
                    tvRequestState.text = leaveRequest.leaveRequestState.last()
                }
            }
        })
    }


}
