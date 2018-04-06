package com.hdu.newe.here.page.main.variousdata;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdu.newe.here.R;
import com.hdu.newe.here.bean.AttendanceDataBean;
import com.hdu.newe.here.bean.BuffDataBean;
import com.hdu.newe.here.bean.HistoryDataBean;
import com.hdu.newe.here.page.base.BaseFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * @author pope
 */
public class HistoryDataFragment extends BaseFragment<VariousDataContract.Presenter> implements VariousDataContract.View {


    public HistoryDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_data, container, false);
    }

    @Override
    public void loadAttendanceData(List<AttendanceDataBean> attendanceDataBeanList) {

    }

    @Override
    public void loadHistoryData(List<HistoryDataBean> historyDataBeanList) {

    }

    @Override
    public void loadBuffHistoryData(List<BuffDataBean> buffDataBeanList) {

    }
}
