package com.hdu.newe.here.page.main.signin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.user.entity.UserBean;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * @author pope
 * @date 2018/8/29
 */

public class CheckMsgAdapter extends RecyclerView.Adapter<CheckMsgAdapter.ViewHolder> {

    private int viewCode = 0;
    private List<String> dataList;
    private List<Boolean> isAppearList;
    private Context mContext;

    public CheckMsgAdapter(int viewCode, List<String> dataList
            , List<Boolean> isAppearList, Context context) {
        this.viewCode = viewCode;
        this.dataList = dataList;
        this.isAppearList = isAppearList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_check_msg, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        String userId = dataList.get(position);
        final boolean isAppear = isAppearList.get(position);
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.getObject(userId, new QueryListener<UserBean>() {
            @Override
            public void done(final UserBean userBean, BmobException e) {
                if (e == null) {
                    if (isAppear){
                        holder.viewBg.setBackgroundResource(R.drawable.bg_appear);
                        holder.msgState.setText("已出勤");
                    }else {
                        holder.viewBg.setBackgroundResource(R.drawable.bg_absent);
                        holder.msgState.setText("未出勤");
                    }
                    holder.msgName.setText(userBean.getUserName());
                    holder.msgNum.setText("#"+userBean.getUserNumber());
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CheckingInformationActivity checkingInformationActivity = (CheckingInformationActivity) mContext;
                            checkingInformationActivity.hideOrShowMsg(userBean,isAppear);
                        }
                    });
                } else {
                    Toast.makeText(mContext, "error770:" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout viewBg;
        TextView msgName;
        TextView msgNum;
        TextView msgState;

        public ViewHolder(View itemView) {
            super(itemView);
            viewBg = itemView.findViewById(R.id.view_check_bg);
            msgName = itemView.findViewById(R.id.tv_check_msg_name);
            msgNum = itemView.findViewById(R.id.tv_check_student_num);
            msgState = itemView.findViewById(R.id.tv_check_msg_state);
        }
    }
}
