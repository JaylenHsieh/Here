package com.hdu.newe.here.page.main.signin;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdu.newe.here.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author pope
 */
public class ResultFragment extends Fragment {


    @BindView(R.id.tv_result_title)
    TextView tvResultTitle;
    @BindView(R.id.tv_subject_code)
    TextView tvSubjectCode;
    Unbinder unbinder;
    @BindView(R.id.tv_subject_name)
    TextView tvSubjectName;

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        unbinder = ButterKnife.bind(this, view);

        //初始化视图
        initView();

        return view;
    }

    /**
     * 初始化视图方法
     */
    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("正在加载...");
        progressDialog.show();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean isTeacher = sharedPreferences.getBoolean("isTeacher", false);
        //判断如果是教师则加载教师的布局，否则加载学生的布局
        if (isTeacher) {
            loadTeacherView();
        } else {
            loadStudentView();
        }
    }

    /**
     * 教师初始化视图方法
     */
    private void loadTeacherView() {
        //获取课程名和课程码
        NewSubjectActivity newSubjectActivity = (NewSubjectActivity) getActivity();
        tvSubjectCode.setText(newSubjectActivity.getSubjectCode());
        tvSubjectName.setText("“" + newSubjectActivity.getSubjectName() + "”教学班");
        progressDialog.dismiss();
    }

    /**
     * 学生初始化视图方法
     */
    private void loadStudentView() {
        //获取课程名
        NewSubjectActivity newSubjectActivity = (NewSubjectActivity) getActivity();
        tvSubjectName.setText("“" + newSubjectActivity.getSubjectName() + "”教学班");
        tvSubjectCode.setVisibility(View.INVISIBLE);
        tvResultTitle.setText("加入\n成功");
        tvResultTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvResultTitle.setTextSize(48);
        progressDialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_finish)
    public void onViewClicked() {
        getActivity().finish();
    }
}
