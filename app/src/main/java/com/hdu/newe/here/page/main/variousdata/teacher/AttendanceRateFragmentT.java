package com.hdu.newe.here.page.main.variousdata.teacher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdu.newe.here.R;
import com.hdu.newe.here.page.main.variousdata.teacher.adapter.StudentListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author pope
 */
public class AttendanceRateFragmentT extends Fragment {


    @BindView(R.id.recyclerView_student_rate)
    RecyclerView recyclerViewStudentRate;
    Unbinder unbinder;

    public static AttendanceRateFragmentT newInstance() {
        AttendanceRateFragmentT fragment = new AttendanceRateFragmentT();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendance_rate_fragment_t, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 加载学生出勤率方法
     * @param studentNameList 学生姓名列表
     * @param studentAttendanceRateList 学生出勤率列表
     */
    public void loadAttendanceData(List<String> studentNameList, List<Number> studentAttendanceRateList) {

        List<String> studentRateList = new ArrayList<>();
        for (int i = 0;i <studentAttendanceRateList.size(); i++){
            studentRateList.add(String.valueOf(studentAttendanceRateList.get(i)));
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewStudentRate.setLayoutManager(layoutManager);
        StudentListAdapter adapter = new StudentListAdapter(studentNameList,studentRateList);
        recyclerViewStudentRate.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
