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
import com.hdu.newe.here.biz.variousdata.student.bean.VariousDataBean;

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

import static com.hdu.newe.here.biz.profile.bean.ClassDataBean.changeToDescription;

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
                        tvTime.setText("上课时间：" + changeToDescription(classDataBean.getSubjectCode().substring(3)));
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

    /**
     * 加入班级的方法
     * @param classDataBean 被加入班级的数据Bean
     */
    private void joinSubject(final ClassDataBean classDataBean) {

        progressDialog.show();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        final String objId = sharedPreferences.getString("objId", "错误");
        if (objId.equals("错误")) {
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "加入失败:error303\n", Toast.LENGTH_LONG).show();
            Log.i("报错303","用户本地的ObjectId有错误");
            return;
        }
        List<String> newClassMember = classDataBean.getClassMember();

        //检查该用户是否已经加入该班
        for (int a = 0; a < newClassMember.size(); a++) {
            if (objId.equals(newClassMember.get(a))){
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"您已加入该教学班，请勿重复添加！",Toast.LENGTH_LONG).show();
                return;
            }
        }

        //向该班ClassDataBean中的classMember字段添加该用户
        newClassMember.add(objId);
        classDataBean.setClassMember(newClassMember);
        classDataBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //添加成功
                    flag++;
                    if (flag == 2) {
                        //加入成功
                        Toast.makeText(getActivity(), "加入成功", Toast.LENGTH_LONG).show();
                        NewSubjectActivity newSubjectActivity = (NewSubjectActivity) getActivity();
                        newSubjectActivity.setSubjectName(classDataBean.getSubjectName());
                        addVariousData(classDataBean,objId);
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
                if (e == null) {
                    List<String> subjectList = userBean.getSubjectList();
                    if (subjectList == null) {
                        subjectList = new ArrayList<>();
                    }
                    subjectList.add(classDataBean.getSubjectCode());
                    userBean.setSubjectList(subjectList);
                    userBean.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //添加成功
                                flag++;
                                if (flag == 2) {
                                    //加入成功
                                    NewSubjectActivity newSubjectActivity = (NewSubjectActivity) getActivity();
                                    newSubjectActivity.setSubjectName(classDataBean.getSubjectName());
                                    addVariousData(classDataBean,objId);
                                    newSubjectActivity.lunchResultFragment();
                                    progressDialog.dismiss();
                                }
                            } else {
                                Toast.makeText(getActivity(), "加入失败:error444\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "加入失败:error333\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i("报错", e.getMessage());
                }
            }
        });
    }

    /**
     * 加入新课程时，将新课程的数据添加到用户VariousDataBean数据表中
     * @param classDataBean 新加入的课程班数据
     * @param objId 用户UserBean中的Id
     */
    private void addVariousData(final ClassDataBean classDataBean, String objId) {

        BmobQuery<VariousDataBean> query = new BmobQuery<>();
        query.addWhereEqualTo("userObjectId",objId);
        query.findObjects(new FindListener<VariousDataBean>() {
            @Override
            public void done(List<VariousDataBean> list, BmobException e) {
                if (e == null){
                    VariousDataBean variousDataBean = list.get(0);
                    List<String> list1 = variousDataBean.getSubjectName();
                    if (list1 != null && !list1.isEmpty()){
                        list1.add(classDataBean.getSubjectName());
                        variousDataBean.setSubjectName(list1);
                        List<Number> list2 = variousDataBean.getAttendanceRate();
                        list2.add(100);
                        variousDataBean.setAttendanceRate(list2);
                        variousDataBean.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null){
                                    Log.i("无报错","成功添加新课程数据到VariousDataBean");
                                    return;
                                }else {
                                    Toast.makeText(getActivity(),"error9654",Toast.LENGTH_LONG).show();
                                    Log.i("报错9654",e.getMessage());
                                }
                            }
                        });
                    }
                }else {
                    Toast.makeText(getActivity(),"error14512",Toast.LENGTH_LONG).show();
                    Log.i("报错14512",e.getMessage());
                }
            }
        });

    }
}
