package com.hdu.newe.here.page.main.variousdata.student.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hdu.newe.here.R;
import com.hdu.newe.here.page.main.variousdata.student.bean.ExpandDataBean;

/**
 * Created by pope on 2018/4/10.
 */

public class OthersChildViewHolder extends BaseViewHolder {

    private Context context;
    private View view;
    private TextView tvTitle;
    private TextView tvContent;

    public OthersChildViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.view = itemView;
    }

    public void bindView(final ExpandDataBean expandDataBean, final int position, int viewType) {

        tvTitle = view.findViewById(R.id.tv_warning_history_title);
        tvContent = view.findViewById(R.id.tv_warning_history_content);

        switch (viewType) {
            case ExpandDataBean.ITEM_CHILD_WARNING:
                tvTitle.setText(expandDataBean.getChildBean().getWarningTitle().get(0));
                tvContent.setText(expandDataBean.getChildBean().getWarningContent().get(0));
                break;
            case ExpandDataBean.ITEM_CHILD_CHANGE:
                tvTitle.setText(expandDataBean.getChildBean().getChangeHistoryTitle().get(0));
                tvContent.setText(expandDataBean.getChildBean().getChangeHistoryContent().get(0));
                break;
            default:
                break;
        }

    }
}
