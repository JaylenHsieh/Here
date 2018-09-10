package com.hdu.newe.here.page.main.signin;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.signin.bean.SignInDataBean;
import com.hdu.newe.here.biz.user.entity.UserBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author pope
 */
public class CheckMsgListFragment extends Fragment {

    @BindView(R.id.recyclerview_check_msg_list)
    RecyclerView recyclerviewCheckMsgList;
    Unbinder unbinder;
    @BindView(R.id.tv_msg_name)
    TextView tvMsgName;
    @BindView(R.id.imgview_msg_avatar)
    ImageView imgviewMsgAvatar;
    @BindView(R.id.tv_msg_state)
    TextView tvMsgState;
    @BindView(R.id.tv_msg_num)
    TextView tvMsgNum;
    @BindView(R.id.tv_msg_class)
    TextView tvMsgClass;
    @BindView(R.id.tv_msg_major)
    TextView tvMsgMajor;
    @BindView(R.id.tv_msg_college)
    TextView tvMsgCollege;
    @BindView(R.id.btn_msg_left)
    TextView btnMsgLeft;
    @BindView(R.id.btn_msg_right)
    TextView btnMsgRight;
    @BindView(R.id.group_msg)
    ConstraintLayout groupMsg;

    private int viewCode;
    private String objectId;
    private List<String> dataList;
    private List<Boolean> isAppearList;
    private SignInDataBean signInDataBean;
    private ProgressDialog progressDialog;
    private UserBean userBean;
    private Boolean isAppear;

    /**
     * CheckMsgListFragment
     *
     * @return CheckMsgListFragment
     */
    public static CheckMsgListFragment newInstance(int viewCode, String objectId) {
        CheckMsgListFragment fragment = new CheckMsgListFragment();
        Bundle args = new Bundle();
        args.putInt("viewCode", viewCode);
        //SignInDataBean的ObjectId
        args.putString("objectId", objectId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check_msg_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        initSomething();

        return view;
    }

    public void setSignInDataBean(SignInDataBean signInDataBean) {
        this.signInDataBean = signInDataBean;
    }

    private void initSomething() {

        progressDialog.setMessage("正在加载...");
        progressDialog.setTitle("请稍等");

        Bundle bundle = getArguments();
        viewCode = bundle.getInt("viewCode");
        objectId = bundle.getString("objectId");

        BmobQuery<SignInDataBean> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<SignInDataBean>() {
            @Override
            public void done(SignInDataBean signInDataBean, BmobException e) {
                if (e == null) {
                    setSignInDataBean(signInDataBean);
                } else {
                    Toast.makeText(getActivity(), "error488:" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        isAppearList = new ArrayList<>();
        dataList = new ArrayList<>();
        getData(viewCode);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerviewCheckMsgList.setLayoutManager(layoutManager);
        CheckMsgAdapter adapter = new CheckMsgAdapter(viewCode, dataList, isAppearList, getActivity());
        recyclerviewCheckMsgList.setAdapter(adapter);


    }


    private void getData(int viewCode) {
        switch (viewCode) {
            case 1:
                //缺勤名单
                dataList = signInDataBean.getAbsentStudentList();
                break;
            case 2:
                //请假名单
                dataList = signInDataBean.getLeaveRequestStudentList();
                break;
            case 3:
                //手动更改出勤设置 全部名单
                dataList = signInDataBean.getStudentList();
                break;
            case 4:
                //出勤抽查 根据buff来区别性随机抽取10位同学进行抽查
//                dataList = getSpotCheckList();
                break;
            default:
                break;
        }

        for (int k = 0;k<dataList.size();k++){
            isAppearList.add(true);
        }
        List<String> absentList = signInDataBean.getAbsentStudentList();
        for (int i = 0; i < dataList.size(); i++) {
            String a = dataList.get(i);
            for (int j = 0; j < absentList.size(); j++) {
                String b = absentList.get(j);
                if (a.equals(b)){
                    isAppearList.set(i,false);
                    break;
                }
            }
        }

    }

//    private List<String> getSpotCheckList() {
//
//        List<String> spotCheckList = new ArrayList<>();
//        final List<String> attendList = signInDataBean.getStudentList();
//        List<String> absentList = signInDataBean.getAbsentStudentList();
//        for (int i = 0; i < absentList.size(); i++) {
//            String a = absentList.get(i);
//            for (int j = 0; j < attendList.size(); j++) {
//                String b = attendList.get(j);
//                if (a.equals(b)) {
//                    attendList.remove(j);
//                    break;
//                }
//            }
//        }
//        final List<Integer> buffList = new ArrayList<>();
//        for (int o = 0; o < attendList.size(); o++) {
//            buffList.add(0);
//        }
//        final int[] count = {0};
//        for (int k = 0; k < attendList.size(); k++) {
//            BmobQuery<VariousDataBean> query = new BmobQuery<>();
//            final int finalK = k;
//            query.getObject(attendList.get(k), new QueryListener<VariousDataBean>() {
//                @Override
//                public void done(VariousDataBean variousDataBean, BmobException e) {
//                    if (e == null) {
//                        buffList.set(finalK, variousDataBean.getBuffType());
//                        count[0]++;
//                        if (count[0] == finalK) {
//                            for (int p = 0; p < buffList.size(); p++) {
//
//                            }
//                        }
//                    } else {
//                        Toast.makeText(getActivity(), "error948:" + e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
//        }
//
//    }

    public void loadMsg(UserBean userBean, boolean isAppear) {
        progressDialog.show();
        this.userBean = userBean;
        this.isAppear = isAppear;
        switch (viewCode) {
            case 1:
                btnMsgLeft.setText("取消");
                btnMsgRight.setText("变更为出勤");
                btnMsgRight.setTextColor(Color.RED);
                btnMsgRight.setBackgroundResource(R.drawable.bg_btn_notice);
                break;
            case 2:
                btnMsgRight.setVisibility(View.GONE);
                btnMsgLeft.setVisibility(View.GONE);
                break;
            case 3:
                btnMsgLeft.setText("取消");
                if (isAppear) {
                    btnMsgRight.setText("变更为缺勤");
                    btnMsgRight.setTextColor(Color.GREEN);
                    btnMsgRight.setBackgroundResource(R.drawable.bg_btn_apprar);
                } else {
                    btnMsgRight.setText("变更为出勤");
                    btnMsgRight.setTextColor(Color.RED);
                    btnMsgRight.setBackgroundResource(R.drawable.bg_btn_notice);
                }
                break;
            case 4:
                btnMsgLeft.setText("真实出勤");
                btnMsgLeft.setTextColor(Color.GREEN);
                btnMsgLeft.setBackgroundResource(R.drawable.bg_btn_apprar);
                btnMsgRight.setText("虚假出勤");
                btnMsgRight.setTextColor(Color.RED);
                btnMsgRight.setBackgroundResource(R.drawable.bg_btn_notice);
                break;
            default:
                break;
        }
        tvMsgName.setText(userBean.getUserName());
        tvMsgNum.setText("#学号#" + userBean.getUserNumber());
        tvMsgClass.setText("#班级#" + userBean.getUserClass());
        tvMsgMajor.setText("#专业#" + userBean.getUserMajor());
        tvMsgCollege.setText("#学院#" + userBean.getUserCollege());
        if (isAppear) {
            tvMsgState.setText("√已出勤");
            tvMsgState.setTextColor(Color.GREEN);
        } else {
            tvMsgState.setText("X未出勤");
            tvMsgState.setTextColor(Color.RED);
        }
        hideOrShowMsg();
    }

    private void hideOrShowMsg() {
        if (groupMsg.getVisibility() == View.INVISIBLE) {
            groupMsg.setVisibility(View.VISIBLE);
        } else {
            groupMsg.setVisibility(View.INVISIBLE);
        }
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_msg_left, R.id.btn_msg_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_msg_left:
                switch (viewCode) {
                    case 1:
                        hideOrShowMsg();
                        break;
                    case 2:
                        //TODO 根据请假数据情况进行撰写
                        break;
                    case 3:
                        hideOrShowMsg();
                        break;
                    case 4:
                        //真实出勤 如果需要，可作为信用系统的加分项
                        hideOrShowMsg();
                        break;
                    default:
                        break;
                }
                break;
            case R.id.btn_msg_right:
                progressDialog.setMessage("正在提交...");
                switch (viewCode) {
                    case 1:
                        progressDialog.show();
                        String id = userBean.getObjectId();
                        List<String> absentList = signInDataBean.getAbsentStudentList();
                        for (int i = 0; i < absentList.size(); i++) {
                            if (absentList.get(i).equals(id)) {
                                absentList.remove(i);
                            }
                        }
                        signInDataBean.setAbsentStudentList(absentList);
                        signInDataBean.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    //更改为出勤成功
                                    Toast.makeText(getActivity(), "变更成功", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getActivity(), "error056:" + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        hideOrShowMsg();
                        break;
                    case 2:
                        //TODO 根据请假数据情况进行撰写
                        break;
                    case 3:
                        progressDialog.show();
                        String id1 = userBean.getObjectId();
                        List<String> absentList1 = signInDataBean.getAbsentStudentList();
                        if (isAppear) {
                            absentList1.add(id1);
                        } else {
                            absentList1.remove(id1);
                        }
                        signInDataBean.setAbsentStudentList(absentList1);
                        signInDataBean.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    //更改成功
                                    Toast.makeText(getActivity(), "变更成功", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                } else {
                                    Toast.makeText(getActivity(), "变更失败\nerror114:" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                        hideOrShowMsg();
                        break;
                    case 4:
                        progressDialog.show();
                        String id2 = userBean.getObjectId();
                        List<String> absentList2 = signInDataBean.getAbsentStudentList();
                        absentList2.add(id2);
                        signInDataBean.setAbsentStudentList(absentList2);
                        signInDataBean.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    //更改成功
                                    Toast.makeText(getActivity(), "变更成功", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                } else {
                                    Toast.makeText(getActivity(), "变更失败\nerror117:" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                        hideOrShowMsg();
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
