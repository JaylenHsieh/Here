package com.hdu.newe.here.page.main.signin;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.profile.bean.ClassDataBean;
import com.hdu.newe.here.biz.user.entity.UserBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author pope
 */
public class JoinSubjectFragment extends Fragment {

    @BindView(R.id.editText_search_subject)
    EditText editTextSearchSubject;
    @BindView(R.id.btn_search)
    TextView tvSearch;
    @BindView(R.id.tv_teacher_name)
    TextView tvTeacherName;
    @BindView(R.id.tv_subject_name2)
    TextView tvSubjectName2;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_student_number)
    TextView tvStudentNumber;
    @BindView(R.id.cardview_group)
    CardView cardviewGroup;
    @BindView(R.id.btn_join)
    TextView btnJoin;
    Unbinder unbinder;

    private ProgressDialog progressDialog;
    private int flag = 0;

    public static JoinSubjectFragment newInstance() {
        JoinSubjectFragment fragment = new JoinSubjectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_join_subject, container, false);
        unbinder = ButterKnife.bind(this, view);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("正在加载...");

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_search)
    public void onViewClicked() {

        progressDialog.show();
        String subjectCode = editTextSearchSubject.getText().toString();
        BmobQuery<ClassDataBean> query = new BmobQuery<>();
        query.addWhereEqualTo("subjectCode", subjectCode);
        query.findObjects(new FindListener<ClassDataBean>() {
            @Override
            public void done(List<ClassDataBean> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.size() == 0) {
                        Toast.makeText(getActivity(), "查询无果，请核对课程码是否输入正确", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    } else {
                        final ClassDataBean classDataBean = list.get(0);
                        BmobQuery<UserBean> query1 = new BmobQuery<>();
                        query1.getObject(classDataBean.getTeacherId(), new QueryListener<UserBean>() {
                            @Override
                            public void done(UserBean userBean, BmobException e) {
                                if (e == null) {
                                    tvTeacherName.setText("任课教师：" + userBean.getUserName());
                                } else {
                                    Toast.makeText(getActivity(), "error111\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        tvSubjectName2.setText("课程名称：" + classDataBean.getSubjectName());
                        String placeCode = classDataBean.getPlaceCode();
                        int building = Integer.valueOf(placeCode.substring(0, 2));
                        int classroom = Integer.valueOf(placeCode.substring(2));
                        tvPlace.setText("上课地点：" + building + "教" + classroom);
                        tvTime.setText("上课时间：" + classDataBean.changeToDescription(classDataBean.getSubjectCode().substring(3)));
                        tvStudentNumber.setText("课程人数：" + classDataBean.getClassMember().size() + "人");
                        btnJoin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //加入该班级
                                joinSubject(classDataBean);
                            }
                        });
                        cardviewGroup.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), "error456\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void joinSubject(final ClassDataBean classDataBean) {

        progressDialog.show();
        //向该班ClassDataBean中的classMember字段添加该用户
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String objId = sharedPreferences.getString("objId", "错误");
        if (objId.equals("错误")){
            Toast.makeText(getActivity(),"加入失败:error303\n",Toast.LENGTH_LONG).show();
        }
        List<String> newClassMember = classDataBean.getClassMember();
        newClassMember.add(objId);
        classDataBean.setClassMember(newClassMember);
        classDataBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //添加成功
                    flag++;
                    if (flag == 2){
                        //加入成功
                        Toast.makeText(getActivity(),"加入成功",Toast.LENGTH_LONG).show();
                        NewSubjectActivity newSubjectActivity = (NewSubjectActivity) getActivity();
                        newSubjectActivity.setSubjectName(classDataBean.getSubjectName());
                        newSubjectActivity.lunchResultFragment();
                        progressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), "加入失败:error222\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        //向该用户的UserBean中的subjectList字段添加该课程的subjectCode
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.getObject(objId, new QueryListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                if (e == null){
                    List<String> subjectList = userBean.getSubjectList();
                    if (subjectList == null){
                        subjectList = new ArrayList<>();
                    }
                    subjectList.add(classDataBean.getSubjectCode());
                    userBean.setSubjectList(subjectList);
                    userBean.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                //添加成功
                                flag++;
                                if (flag == 2){
                                    //加入成功
                                    NewSubjectActivity newSubjectActivity = (NewSubjectActivity) getActivity();
                                    newSubjectActivity.setSubjectName(classDataBean.getSubjectName());
                                    newSubjectActivity.lunchResultFragment();
                                    progressDialog.dismiss();
                                }
                            }else {
                                Toast.makeText(getActivity(), "加入失败:error444\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(getActivity(),"加入失败:error333\n"+e.getMessage(),Toast.LENGTH_LONG).show();
                    Log.i("报错",e.getMessage());
                }
            }
        });
    }
}
