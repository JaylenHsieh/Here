package com.hdu.newe.here.page.main.variousdata.student.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hdu.newe.here.R;
import com.hdu.newe.here.page.main.variousdata.student.bean.ExpandDataBean;

/**
 *
 * @author pope
 * @date 2018/4/10
 */

public class LeaveRequestChildViewHolder extends BaseViewHolder {

    private Context context;
    private View view;
    private TextView leaveRequestType;
    private TextView leaveRequestReason;
    private TextView leaveRequestState;
    private TextView leaveRequestTime;

    public LeaveRequestChildViewHolder(Context context,View itemView) {
        super(itemView);
        this.context = context;
        this.view = itemView;
    }

    public void bindView(final ExpandDataBean expandDataBean, final int position){

        leaveRequestReason = view.findViewById(R.id.tv_leaverequest_reason);
        leaveRequestState = view.findViewById(R.id.tv_leaverequest_state);
        leaveRequestTime = view.findViewById(R.id.tv_leaverequest_time);
        leaveRequestType = view.findViewById(R.id.tv_leaverequest_type);

        leaveRequestReason.setText(expandDataBean.getLeaveRequestReason().get(0));
        leaveRequestState.setText(expandDataBean.getLeaveRequestState().get(0));
        leaveRequestTime.setText(expandDataBean.getLeaveRequestTime().get(0));
        leaveRequestType.setText(expandDataBean.getLeaveRequestType().get(0));

    }
}
