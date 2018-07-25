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

                String title = context.getString(R.string.change_history_title)
                        + expandDataBean.getChildBean().getNewPhone().get(0);
                String content = "姓名"
                        + context.getString(R.string.change_history_content1)
                        + expandDataBean.getChildBean().getChangeTime().get(0)
                        + context.getString(R.string.change_history_content2)
                        + "学号"
                        + context.getString(R.string.change_history_content3)
                        + expandDataBean.getChildBean().getOldPhone()
                        + ":"
                        + expandDataBean.getChildBean().getOldIMEI()
                        + context.getString(R.string.change_history_content4)
                        + expandDataBean.getChildBean().getNewPhone()
                        + ":"
                        + expandDataBean.getChildBean().getNewIMEI()
                        + context.getString(R.string.change_history_content5);

                tvTitle.setText(title);
                tvContent.setText(content);

                break;
            default:
                break;
        }

    }
}
