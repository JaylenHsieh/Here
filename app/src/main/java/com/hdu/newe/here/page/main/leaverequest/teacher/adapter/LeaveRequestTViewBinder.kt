package com.hdu.newe.here.page.main.leaverequest.teacher.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hdu.newe.here.R
import com.hdu.newe.here.biz.variousdata.student.bean.LeaveRequestBean
import me.drakeet.multitype.ItemViewBinder

/**
 * @author Jaylen Hsieh
 * @date 2018/07/29
 */
class LeaveRequestTViewBinder(
        private val tapLeaveRequestItem: (pos: Int) -> Unit,
        private val acceptOrRefuseClick: (state: Boolean, pos: Int) -> Unit
) : ItemViewBinder<LeaveRequestBean, LeaveRequestTViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_leave_request_check, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, leaveRequest: LeaveRequestBean) {
        with(leaveRequest) {
            holder.tvUsername.text = userName
            holder.tvUserNumber.text = userNumber
            holder.tvUserFaculty.text = userCollege
            holder.tvUserSpeciality.text = userMajor
            holder.tvUserInstructor.text = userInstructor
            holder.tvLeaveReason.text = leaveRequestReason[leaveRequestReason.size - 1]
            holder.tvLeaveStartTime.text = leaveRequestTime[leaveRequestTime.size - 2]
            holder.tvLeaveFinishTime.text = leaveRequestTime[leaveRequestTime.size - 1]
        }

        // item的点击事件
        holder.itemView.setOnClickListener {
            tapLeaveRequestItem(holder.adapterPosition)
        }
        // 拒绝按钮的点击事件
        holder.btnRefuse.setOnClickListener {
            acceptOrRefuseClick(false,holder.adapterPosition)
        }
        holder.btnPermit.setOnClickListener {
            acceptOrRefuseClick(true,holder.adapterPosition)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUsername: TextView = itemView.findViewById(R.id.mTvUserName)
        val tvUserNumber: TextView = itemView.findViewById(R.id.mTvUserNumber)
        val tvUserFaculty: TextView = itemView.findViewById(R.id.mTvStuFaculty)
        val tvUserSpeciality: TextView = itemView.findViewById(R.id.mTvStuSpeciality)
        val tvUserInstructor: TextView = itemView.findViewById(R.id.mTvStuInstructor)
        val tvLeaveReason: TextView = itemView.findViewById(R.id.tvRequestContent)
        val tvLeaveStartTime: TextView = itemView.findViewById(R.id.tvStartTime)
        val tvLeaveFinishTime: TextView = itemView.findViewById(R.id.tvFinishTime)
        val btnRefuse: TextView = itemView.findViewById(R.id.btnRefuse)
        val btnPermit: TextView = itemView.findViewById(R.id.btnPermit)
    }
}