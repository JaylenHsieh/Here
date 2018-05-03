package com.hdu.newe.here.page.main.variousdata.teacher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.signin.bean.SignInDataBean;
import com.hdu.newe.here.page.main.variousdata.teacher.adapter.StudentListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 用于展示所带教学班的数据的碎片
 * A simple {@link Fragment} subclass.
 *
 * @author pope
 */
public class VariousDataOfSubjectFragmentT extends Fragment {


    @BindView(R.id.tv_attendance_subject_membernum)
    TextView tvAttendanceSubjectMembernum;
    @BindView(R.id.tv_attendance_subject_attendance)
    TextView tvAttendanceSubjectAttendance;
    @BindView(R.id.tv_attendance_subject_leavenum)
    TextView tvAttendanceSubjectLeavenum;
    @BindView(R.id.tv_attendance_subject_absentnum)
    TextView tvAttendanceSubjectAbsentnum;
    @BindView(R.id.recyclerView_absent_member)
    RecyclerView recyclerViewAbsentMember;
    @BindView(R.id.recyclerView_leaverequest_member)
    RecyclerView recyclerViewLeaverequestMember;
    Unbinder unbinder;

    public static VariousDataOfSubjectFragmentT newInstance() {
        VariousDataOfSubjectFragmentT fragment = new VariousDataOfSubjectFragmentT();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_various_data_of_subject_fragment_t, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 加载显示考勤数据
     *
     * @param signInDataBean 被显示的数据bean
     */
    public void loadAttendanceData(SignInDataBean signInDataBean) {
        tvAttendanceSubjectMembernum.setText(signInDataBean.getStudentList().size());
        tvAttendanceSubjectAttendance.setText(signInDataBean.getStudentList().size()
                - signInDataBean.getAbsentStudentList().size()
                - signInDataBean.getLeaveRequestStudentList().size());
        tvAttendanceSubjectLeavenum.setText(signInDataBean.getLeaveRequestStudentList().size());
        tvAttendanceSubjectAbsentnum.setText(signInDataBean.getAbsentStudentList().size());

    }

    /**
     * 加载学生列表数据方法
     * @param studentNameList 学生姓名列表
     * @param studentNumList 学生学号列表
     * @param pos 加载位置
     */
    public void loadListData(List<String> studentNameList, List<String> studentNumList, int pos) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewAbsentMember.setLayoutManager(layoutManager);
        recyclerViewLeaverequestMember.setLayoutManager(layoutManager);
        StudentListAdapter adapter = new StudentListAdapter(studentNameList, studentNumList);
        switch (pos) {
            case SignInDataBean.LIST_TYPE_ABSENT:
                recyclerViewAbsentMember.setAdapter(adapter);
                break;
            case SignInDataBean.LIST_TYPE_LEAVEREQUEST:
                recyclerViewLeaverequestMember.setAdapter(adapter);
                break;
            default:
                break;
        }

    }

}
