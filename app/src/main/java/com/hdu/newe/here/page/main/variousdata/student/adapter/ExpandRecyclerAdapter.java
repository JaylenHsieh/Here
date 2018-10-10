package com.hdu.newe.here.page.main.variousdata.student.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.variousdata.student.bean.VariousDataBean;
import com.hdu.newe.here.page.main.variousdata.student.adapter.viewholder.BaseViewHolder;
import com.hdu.newe.here.page.main.variousdata.student.adapter.viewholder.LeaveRequestChildViewHolder;
import com.hdu.newe.here.page.main.variousdata.student.adapter.viewholder.OthersChildViewHolder;
import com.hdu.newe.here.page.main.variousdata.student.adapter.viewholder.ParentViewHolder;
import com.hdu.newe.here.page.main.variousdata.student.bean.ExpandDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pope
 * @date 2018/4/10
 */

public class ExpandRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context context;
    private List<ExpandDataBean> expandDataBeans;
    private LayoutInflater inflater;
    private OnScrollListener mOnScrollListener;

    public ExpandRecyclerAdapter(Context context, List<ExpandDataBean> expandDataBeans) {
        this.context = context;
        this.expandDataBeans = expandDataBeans;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * 父类标题的点击监听
     */
    private ParentItemClickListener parentItemClickListener = new ParentItemClickListener() {

        //展开子列表
        @Override
        public void onExpandChildren(ExpandDataBean expandDataBean) {
            //确定当前点击的item位置
            int position = getCurrentPosition(expandDataBean);
            List<ExpandDataBean> childBeans = getChildBeans(expandDataBean);
            if (childBeans == null || childBeans.size() == 0) {
                return;
            }

            int count = childBeans.size();
            for (int i = 0; i < count; i++) {
                add(childBeans.get(i), position + 1);
                position++;
            }

//            //如果点击的item为最后一个
//            if (position == expandDataBeans.size() - 2 && mOnScrollListener != null) {
//                //向下滚动，使子布局能够完全展示
//                mOnScrollListener.scrollTo(position + 1);
//            }
        }

        //合上子列表
        @Override
        public void onHideChildren(ExpandDataBean expandDataBean) {
            //确定当前点击的item位置
            int position = getCurrentPosition(expandDataBean);
            List<ExpandDataBean> childBeans = getChildBeans(expandDataBean);
            if (childBeans == null || childBeans.size() == 0) {
                return;
            }

            int count = childBeans.size();
            for (int i = 0; i < count; i++) {
                remove(position + 1);
            }

//            if (mOnScrollListener != null) {
//                mOnScrollListener.scrollTo(position);
//            }
        }
    };

    /**
     * 封装子项目的数据
     *
     * @param expandDataBean
     * @return
     */
    private List<ExpandDataBean> getChildBeans(ExpandDataBean expandDataBean) {

        List<ExpandDataBean> expandDataBeans = new ArrayList<>();

        int count = -1;
        int showType = -1;

        switch (expandDataBean.getParentTitle()) {
            case "请假历史":
                count = expandDataBean.getLeaveRequestReason().size();
                showType = ExpandDataBean.ITEM_CHILD_LEAVE_REQUEST;
                break;
            case "警示历史":
                List<String> list = expandDataBean.getChildBean().getWarningContent();
                if (list == null || list.isEmpty()) {
                    count = 0;
                } else {
                    count = expandDataBean.getChildBean().getWarningContent().size();
                }
                showType = ExpandDataBean.ITEM_CHILD_WARNING;
                break;
            case "手机更换历史":
                List<String> list1 = expandDataBean.getChildBean().getNewIMEI();
                if (list1 == null || list1.isEmpty()) {
                    count = 0;
                } else {
                    count = expandDataBean.getChildBean().getNewIMEI().size();
                }
                showType = ExpandDataBean.ITEM_CHILD_CHANGE;
                break;
            default:
                break;
        }

        for (int i = 0; i < count; i++) {

            expandDataBeans.add(i, new ExpandDataBean());

            expandDataBeans.get(i).setShowType(showType);
            switch (expandDataBean.getParentTitle()) {
                case "请假历史":

                    expandDataBeans.get(i).setChildBean(new VariousDataBean());

                    List<String> leaveRequestTypeList = new ArrayList<>();
                    List<String> leaveRequestContentList = new ArrayList<>();
                    List<String> leaveRequestStateList = new ArrayList<>();
                    List<String> leaveRequestTimeList = new ArrayList<>();

                    if (expandDataBean.getLeaveRequestReason() != null && !expandDataBean.getLeaveRequestReason().isEmpty()) {
//                        leaveRequestTypeList.add(expandDataBean.getLeaveRequestType().get(i));
                        leaveRequestContentList.add(expandDataBean.getLeaveRequestReason().get(i));
                        leaveRequestStateList.add(expandDataBean.getLeaveRequestState().get(i));
                        leaveRequestTimeList.add(expandDataBean.getLeaveRequestTime().get(i));

                        expandDataBeans.get(i).setLeaveRequestType(leaveRequestTypeList);
                        expandDataBeans.get(i).setLeaveRequestReason(leaveRequestContentList);
                        expandDataBeans.get(i).setLeaveRequestState(leaveRequestStateList);
                        expandDataBeans.get(i).setLeaveRequestTime(leaveRequestTimeList);
                    }

                    break;
                case "警示历史":

                    expandDataBeans.get(i).setChildBean(new VariousDataBean());

                    List<String> warningTitleList = new ArrayList<>();
                    List<String> warningContentList = new ArrayList<>();

                    VariousDataBean variousDataBean = expandDataBean.getChildBean();
                    if (variousDataBean.getWarningTitle() != null && !variousDataBean.getWarningTitle().isEmpty()) {
                        warningTitleList.add(variousDataBean.getWarningTitle().get(i));
                        warningContentList.add(variousDataBean.getWarningContent().get(i));

                        expandDataBeans.get(i).getChildBean().setWarningTitle(warningTitleList);
                        expandDataBeans.get(i).getChildBean().setWarningContent(warningContentList);
                    }
                    break;
                case "手机更换历史":

                    expandDataBeans.get(i).setChildBean(new VariousDataBean());

                    List<String> changeHistoryOldIMEIList = new ArrayList<>();
                    List<String> changeHistoryNewIMEIList = new ArrayList<>();
                    List<String> changeHistoryOldPhoneList = new ArrayList<>();
                    List<String> changeHistoryNewPhoneList = new ArrayList<>();
                    List<String> changeHistoryTimeList = new ArrayList<>();

                    VariousDataBean variousDataBean1 = expandDataBean.getChildBean();
                    if (variousDataBean1.getNewPhone() != null && !variousDataBean1.getNewPhone().isEmpty()) {
                        changeHistoryOldIMEIList.add(variousDataBean1.getOldIMEI().get(i));
                        changeHistoryNewIMEIList.add(variousDataBean1.getNewIMEI().get(i));
                        changeHistoryOldPhoneList.add(variousDataBean1.getOldPhone().get(i));
                        changeHistoryNewPhoneList.add(variousDataBean1.getNewPhone().get(i));
                        changeHistoryTimeList.add(variousDataBean1.getChangeTime().get(i));

                        expandDataBeans.get(i).getChildBean().setOldIMEI(changeHistoryOldIMEIList);
                        expandDataBeans.get(i).getChildBean().setNewIMEI(changeHistoryNewIMEIList);
                        expandDataBeans.get(i).getChildBean().setOldPhone(changeHistoryOldPhoneList);
                        expandDataBeans.get(i).getChildBean().setNewPhone(changeHistoryNewPhoneList);
                        expandDataBeans.get(i).getChildBean().setChangeTime(changeHistoryTimeList);
                    }
                    break;
                default:
                    break;
            }
        }

        return expandDataBeans;

    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            //0
            case ExpandDataBean.ITEM_PARENT:
                view = inflater.inflate(R.layout.item_parent_history_data, parent, false);
                return new ParentViewHolder(context, view);
            //1
            case ExpandDataBean.ITEM_CHILD_LEAVE_REQUEST:
                view = inflater.inflate(R.layout.item_child_leaverequest_history, parent, false);
                return new LeaveRequestChildViewHolder(context, view);
            //2
            case ExpandDataBean.ITEM_CHILD_WARNING:
                view = inflater.inflate(R.layout.item_child_warning_change_history, parent, false);
                return new OthersChildViewHolder(context, view);
            //3
            case ExpandDataBean.ITEM_CHILD_CHANGE:
                view = inflater.inflate(R.layout.item_child_warning_change_history, parent, false);
                return new OthersChildViewHolder(context, view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            //0
            case ExpandDataBean.ITEM_PARENT:
                ParentViewHolder parentViewHolder = (ParentViewHolder) holder;
                parentViewHolder.bindView(expandDataBeans.get(position), position, parentItemClickListener);
                break;
            //1
            case ExpandDataBean.ITEM_CHILD_LEAVE_REQUEST:
                LeaveRequestChildViewHolder leaveRequestChildViewHolder = (LeaveRequestChildViewHolder) holder;
                leaveRequestChildViewHolder.bindView(expandDataBeans.get(position), position);
                break;
            //2
            case ExpandDataBean.ITEM_CHILD_WARNING:
                OthersChildViewHolder warningViewHolder = (OthersChildViewHolder) holder;
                warningViewHolder.bindView(expandDataBeans.get(position), position, getItemViewType(position));
                break;
            //3
            case ExpandDataBean.ITEM_CHILD_CHANGE:
                OthersChildViewHolder changeViewHolder = (OthersChildViewHolder) holder;
                changeViewHolder.bindView(expandDataBeans.get(position), position, getItemViewType(position));
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return expandDataBeans.get(position).getShowType();
    }

    @Override
    public int getItemCount() {
        return expandDataBeans.size();
    }

    /**
     * 在父布局下方插入一条数据
     *
     * @param bean
     * @param position
     */
    public void add(ExpandDataBean bean, int position) {
        expandDataBeans.add(position, bean);
        notifyItemInserted(position);
    }

    /**
     * 移除子布局数据
     *
     * @param position
     */
    protected void remove(int position) {
        expandDataBeans.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 确定当前点击的item位置并返回
     *
     * @return
     */
    protected int getCurrentPosition(ExpandDataBean expandDataBean) {
        int pos = 0;
        switch (expandDataBean.getParentTitle()) {
            case "请假历史":
                pos = 0;
                break;
            case "警示历史":
                ExpandDataBean expandDataBean1 = expandDataBeans.get(0);
                if (expandDataBean1.isExpand()) {
                    pos = expandDataBean1.getChildNum() + 1;
                    break;
                } else {
                    pos = 1;
                    break;
                }
            case "手机更换历史":
                ExpandDataBean expandDataBean2 = expandDataBeans.get(0);
                ExpandDataBean expandDataBean3;
                if (!expandDataBean2.isExpand()) {
                    expandDataBean3 = expandDataBeans.get(1);
                    if (!expandDataBean3.isExpand()) {
                        pos = 2;
                        break;
                    } else {
                        pos = expandDataBean3.getChildNum() + 2;
                        break;
                    }
                } else {
                    expandDataBean3 = expandDataBeans.get(expandDataBean2.getChildNum() + 1);
                    if (!expandDataBean3.isExpand()) {
                        pos = expandDataBean2.getChildNum() + 2;
                        break;
                    } else {
                        pos = expandDataBean2.getChildNum() + expandDataBean3.getChildNum() + 2;
                        break;
                    }
                }
            default:
                pos = 0;
                break;
        }

        return pos;
    }

    /**
     * 滚动监听接口
     */
    public interface OnScrollListener {
        void scrollTo(int pos);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }
}
