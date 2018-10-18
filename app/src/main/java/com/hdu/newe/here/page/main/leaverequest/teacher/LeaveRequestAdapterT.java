package com.hdu.newe.here.page.main.leaverequest.teacher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.user.entity.UserBean;
import com.hdu.newe.here.biz.variousdata.student.bean.LeaveRequestBean;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 教师端请假列表的适配器
 *
 * @author pope
 * @date 2018/10/14
 */

public class LeaveRequestAdapterT extends RecyclerView.Adapter<LeaveRequestAdapterT.ViewHolder> {

    LeaveRequestBean leaveRequestBean;
    List<String> studentList;
    Context context;

    public LeaveRequestAdapterT(LeaveRequestBean leaveRequestBean, Context context) {
        this.leaveRequestBean = leaveRequestBean;
        this.studentList = leaveRequestBean.getLeaveStudents();
        this.context = context;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
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
                    BmobQuery<UserBean> query1 = new BmobQuery<>();
                    query1.getObject(userBean.getInstructorId(), new QueryListener<UserBean>() {
                        @Override
                        public void done(UserBean userBean, BmobException e) {
                            if (e == null) {
                                holder.tvInstructor.setText(userBean.getUserName());
                            } else {
                                holder.tvInstructor.setText("错误");
                            }
                        }
                    });
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
            case "已批准":
                holder.imageAgree.setVisibility(View.VISIBLE);
                break;
            case "已驳回":
                holder.imageDisagree.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        holder.btnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //对学生的请假表数据进行更新
                BmobQuery<LeaveRequestBean> q1 = new BmobQuery<>();
                q1.addWhereEqualTo("userObjectId", studentList.get(position));
                q1.findObjects(new FindListener<LeaveRequestBean>() {
                    @Override
                    public void done(List<LeaveRequestBean> list, BmobException e) {
                        if (e != null) {
                            Toast.makeText(context, "error481546", Toast.LENGTH_LONG).show();
                            Log.i("报错481546", e.getMessage());
                        } else {
                            if (list == null || list.isEmpty()) {
                                Toast.makeText(context, "error462157", Toast.LENGTH_LONG).show();
                            } else {
                                LeaveRequestBean bean = list.get(0);
                                List<String> startTime = bean.getLeaveStartTime();
                                int pos;
                                String time = leaveRequestBean.getLeaveStartTime().get(position);
                                for (pos = 0; pos < startTime.size(); pos++) {
                                    if (time.equals(startTime.get(pos))){
                                        List<String> statelist = bean.getLeaveRequestState();
                                        statelist.set(pos,"已驳回");
                                        bean.setLeaveRequestState(statelist);
                                        bean.update(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e != null) {
                                                    Toast.makeText(context, "error964736", Toast.LENGTH_LONG).show();
                                                    Log.i("报错964736", e.getMessage());
                                                }
                                            }
                                        });
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
                //对原数据进行更新
                List<String> stateList = leaveRequestBean.getLeaveRequestState();
                stateList.set(position, "已驳回");
                leaveRequestBean.setLeaveRequestState(stateList);
                leaveRequestBean.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e != null) {
                            Toast.makeText(context, "error964732", Toast.LENGTH_LONG).show();
                            Log.i("报错964732", e.getMessage());
                        }
                    }
                });
                //更改盖章和状态文字的显示
                if (holder.imageAgree.getVisibility() == View.VISIBLE) {
                    holder.imageAgree.setVisibility(View.INVISIBLE);
                }
                holder.imageDisagree.setVisibility(View.VISIBLE);
                holder.tvState.setText("已驳回");
                Toast.makeText(context, "已驳回", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnPermit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //对学生的请假表数据进行更新
                BmobQuery<LeaveRequestBean> q1 = new BmobQuery<>();
                q1.addWhereEqualTo("userObjectId", studentList.get(position));
                q1.findObjects(new FindListener<LeaveRequestBean>() {
                    @Override
                    public void done(List<LeaveRequestBean> list, BmobException e) {
                        if (e != null) {
                            Toast.makeText(context, "error4815462", Toast.LENGTH_LONG).show();
                            Log.i("报错4815462", e.getMessage());
                        } else {
                            if (list == null || list.isEmpty()) {
                                Toast.makeText(context, "error4621572", Toast.LENGTH_LONG).show();
                            } else {
                                LeaveRequestBean bean = list.get(0);
                                List<String> startTime = bean.getLeaveStartTime();
                                int pos;
                                String time = leaveRequestBean.getLeaveStartTime().get(position);
                                for (pos = 0; pos < startTime.size(); pos++) {
                                    if (time.equals(startTime.get(pos))){
                                        List<String> statelist = bean.getLeaveRequestState();
                                        statelist.set(pos,"已批准");
                                        bean.setLeaveRequestState(statelist);
                                        bean.update(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e != null) {
                                                    Toast.makeText(context, "error9647362", Toast.LENGTH_LONG).show();
                                                    Log.i("报错9647362", e.getMessage());
                                                }
                                            }
                                        });
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
                //对原数据进行更新
                List<String> stateList = leaveRequestBean.getLeaveRequestState();
                stateList.set(position, "已批准");
                leaveRequestBean.setLeaveRequestState(stateList);
                leaveRequestBean.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e != null) {
                            Toast.makeText(context, "error9647322", Toast.LENGTH_LONG).show();
                            Log.i("报错9647322", e.getMessage());
                        }
                    }
                });
                //更改盖章和状态文字的显示
                if (holder.imageDisagree.getVisibility() == View.VISIBLE) {
                    holder.imageDisagree.setVisibility(View.INVISIBLE);
                }
                holder.imageAgree.setVisibility(View.VISIBLE);
                holder.tvState.setText("已批准");
                Toast.makeText(context, "已批准", Toast.LENGTH_SHORT).show();
            }
        });
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
        TextView btnRefuse;
        TextView btnPermit;
        ImageView imageAgree;
        ImageView imageDisagree;

        public ViewHolder(View itemView) {
            super(itemView);
            btnRefuse = itemView.findViewById(R.id.btnRefuse);
            btnPermit = itemView.findViewById(R.id.btnPermit);
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
