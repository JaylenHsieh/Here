package com.hdu.newe.here.page.main.variousdata.student;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdu.newe.here.R;
import com.hdu.newe.here.page.main.variousdata.student.adapter.AttendanceRateAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author pope
 */
public class AttendanceRateFragment extends Fragment {


    @BindView(R.id.recyclerView_various_data)
    RecyclerView recyclerViewVariousData;
    Unbinder unbinder;
    @BindView(R.id.tv_attendance_rate_notice)
    TextView tvAttendanceRateNotice;

    public static AttendanceRateFragment newInstance() {
        AttendanceRateFragment fragment = new AttendanceRateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendance_rate, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    public void loadAttendanceRate(List<String> subjectList, List<Number> attendanceRateList) {

        if (subjectList == null || subjectList.isEmpty()) {
            showNotice();
            return;
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewVariousData.setLayoutManager(layoutManager);
        AttendanceRateAdapter adapter = new AttendanceRateAdapter(subjectList, attendanceRateList);
        recyclerViewVariousData.setAdapter(adapter);

    }

    /**
     * 更改提示的显示
     */
    public void showNotice() {
        if (tvAttendanceRateNotice.getVisibility() == View.INVISIBLE) {
            tvAttendanceRateNotice.setVisibility(View.VISIBLE);
        } else {
            tvAttendanceRateNotice.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
