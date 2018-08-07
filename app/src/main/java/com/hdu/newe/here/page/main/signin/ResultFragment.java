package com.hdu.newe.here.page.main.signin;


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
        //获取课程名和课程码
        NewSubjectActivity newSubjectActivity = (NewSubjectActivity) getActivity();
        tvSubjectCode.setText(newSubjectActivity.getSubjectCode());
        tvSubjectName.setText("“"+newSubjectActivity.getSubjectName()+"”教学班");
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
