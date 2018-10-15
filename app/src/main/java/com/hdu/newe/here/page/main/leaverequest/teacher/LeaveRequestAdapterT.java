package com.hdu.newe.here.page.main.leaverequest.teacher;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.user.entity.UserBean;
import com.hdu.newe.here.biz.variousdata.student.bean.LeaveRequestBean;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * 教师端请假列表的适配器
 *
 * @author pope
 * @date 2018/10/14
 */

public class LeaveRequestAdapterT extends RecyclerView.Adapter<LeaveRequestAdapterT.ViewHolder> {

    LeaveRequestBean leaveRequestBean;
    List<String> studentList;

    public LeaveRequestAdapterT(LeaveRequestBean leaveRequestBean) {
        this.leaveRequestBean = leaveRequestBean;
        this.studentList = leaveRequestBean.getLeaveStudents();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leave_request_check, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        String userId = studentList.get(position);
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.getObject(userId, new QueryListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                if (e == null) {
                    holder.tvName.setText(userBean.getUserName());
                    holder.tvStudentNum.setText(userBean.getUserNumber());
                    holder.tvCollege.setText(userBean.getUserCollege());
                    holder.tvMajor.setText(userBean.getUserMajor());
                    holder.tvInstructor.setText(userBean.getUserInstructor());
                } else {
                    Log.i("报错", e.getMessage());
                }
            }
        });
        holder.tvReason.setText(leaveRequestBean.getLeaveRequestReason().get(position));
        holder.tvStartTime.setText(leaveRequestBean.getLeaveStartTime().get(position));
        holder.tvFinishTime.setText(leaveRequestBean.getLeaveFinishTime().get(position));
        String state = leaveRequestBean.getLeaveRequestState().get(position);
        holder.tvState.setText(state);
        switch (state) {
            case "批准":
                holder.imageAgree.setVisibility(View.VISIBLE);
                break;
            case "驳回":
                holder.imageDisagree.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvStudentNum;
        TextView tvCollege;
        TextView tvMajor;
        TextView tvInstructor;
        TextView tvReason;
        TextView tvStartTime;
        TextView tvFinishTime;
        TextView tvState;
        ImageView imageAgree;
        ImageView imageDisagree;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.mTvUserName);
            tvStudentNum = itemView.findViewById(R.id.mTvUserNumber);
            tvCollege = itemView.findViewById(R.id.mTvStuFaculty);
            tvMajor = itemView.findViewById(R.id.mTvStuSpeciality);
            tvInstructor = itemView.findViewById(R.id.mTvStuInstructor);
            tvReason = itemView.findViewById(R.id.tvRequestContent);
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvFinishTime = itemView.findViewById(R.id.tvFinishTime);
            tvState = itemView.findViewById(R.id.leaveRequestState);
            imageAgree = itemView.findViewById(R.id.imgAccept);
            imageDisagree = itemView.findViewById(R.id.imgRefuse);
        }
    }
}
