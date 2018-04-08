package com.hdu.newe.here.page.main.variousdata;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

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
public class BuffDataFragment extends BaseFragment<VariousDataContract.Presenter> implements VariousDataContract.View {

    private CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton button, boolean isChecked) {

        }
    };


    public BuffDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RadioGroup group = null;
        assert group!= null;

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            }
        });


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buff_data, container, false);
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
