package com.hdu.newe.here.page.main.variousdata.student.adapter.viewholder;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdu.newe.here.R;
import com.hdu.newe.here.page.main.variousdata.student.adapter.ParentItemClickListener;
import com.hdu.newe.here.page.main.variousdata.student.bean.ExpandDataBean;

/**
 *
 * @author pope
 * @date 2018/4/10
 */

public class ParentViewHolder extends BaseViewHolder {

    private Context context;
    private View view;
    private ConstraintLayout containerLayout;
    private TextView tvText;
    private ImageView expand;
    private View parentDashedView;

    public ParentViewHolder(Context context,View itemView) {
        super(itemView);
        this.context = context;
        this.view = itemView;
    }

    public void bindView(final ExpandDataBean expandDataBean, final int position,
                         final ParentItemClickListener ParentItemClickListener){

        containerLayout = view.findViewById(R.id.container_layout);
        tvText = view.findViewById(R.id.tv_parent_text);
        expand = view.findViewById(R.id.expend);
        parentDashedView = view.findViewById(R.id.parent_dashed_view);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) expand
                .getLayoutParams();
        expand.setLayoutParams(params);

        tvText.setText(expandDataBean.getParentTitle());

        if (expandDataBean.isExpand()){
            expand.setRotation(90);
            parentDashedView.setVisibility(View.INVISIBLE);
        }else{
            expand.setRotation(0);
            parentDashedView.setVisibility(View.VISIBLE);
        }

        //父布局OnClick监听
        containerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!expandDataBean.isExpand()){
                    ParentItemClickListener.onExpandChildren(expandDataBean);
                    parentDashedView.setVisibility(View.INVISIBLE);
                    expandDataBean.setExpand(true);
                    rotationExpandIcon(0,90);
                }else{
                    ParentItemClickListener.onHideChildren(expandDataBean);
                    parentDashedView.setVisibility(View.VISIBLE);
                    expandDataBean.setExpand(false);
                    rotationExpandIcon(90,0);
                }
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void rotationExpandIcon(float from, float to) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            /**
             * 属性动画
             */
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
            valueAnimator.setDuration(500);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    expand.setRotation((Float) valueAnimator.getAnimatedValue());
                }
            });
            valueAnimator.start();
        }
    }

}
