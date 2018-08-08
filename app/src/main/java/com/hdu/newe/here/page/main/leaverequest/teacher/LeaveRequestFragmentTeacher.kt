package com.hdu.newe.here.page.main.leaverequest.teacher

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.hdu.newe.here.R
import com.hdu.newe.here.biz.variousdata.student.bean.LeaveRequestBean
import com.hdu.newe.here.page.main.leaverequest.teacher.adapter.LeaveRequestTViewBinder
import com.hdu.newe.here.page.main.profile.PersonalInfoActivity
import com.hdu.newe.here.utils.notifyItemChanged
import com.hdu.newe.here.utils.query
import kotlinx.android.synthetic.main.fragment_leave_requst_fragment_teacher.*
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register
import java.util.*

/**
 * @author Jaylen Hsieh
 * @date 2018/07/29
 */
class LeaveRequestFragmentTeacher : Fragment(){
    /** 列表数据*/
    private var leaveRequestData: List<LeaveRequestBean> = Collections.emptyList()

    /** 列表适配器 */
    private val leaveRequestTAdapter = MultiTypeAdapter().apply {
        register(LeaveRequestTViewBinder({
            Toast.makeText(context, leaveRequestData[it].userName, Toast.LENGTH_SHORT).show()
        },{
            accept,pos ->
            val leaveRequestObjId = leaveRequestData[pos].objectId
            if (accept){
                val leaveRequest = LeaveRequestBean()
                leaveRequest.leaveRequestState.add("通过")
                leaveRequest.update(leaveRequestObjId, object : UpdateListener() {
                    override fun done(e: BmobException?) {
                        if (e == null) {
                            Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "提交失败" + e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            } else {
                val leaveRequest = LeaveRequestBean()
                leaveRequest.leaveRequestState.add("未通过")
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
        }))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_leave_requst_fragment_teacher, container, false)
        val leaveRequestList = view.findViewById<RecyclerView>(R.id.leaveRequestList)
        leaveRequestList.apply {
            //setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            leaveRequestList.layoutManager = layoutManager
            adapter = leaveRequestTAdapter
        }
        fetchList()

        //fabRefresh.setOnClickListener{fetchList()}
        return view
    }

    private fun fetchList() {
        BmobQuery<LeaveRequestBean>().query{
            leaveRequestData = it
            if (it.isEmpty()){
                return@query
            }
            leaveRequestTAdapter.notifyItemChanged(leaveRequestData)
        }
    }

    private fun changeState(result: String){
        val leaveRequestObjId = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)?.getString(PersonalInfoActivity.LEAVE_REQUEST_OBJ_ID, "")
        val leaveRequest = LeaveRequestBean()
        leaveRequest.leaveRequestState.add(result)
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