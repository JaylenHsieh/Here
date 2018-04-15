package com.hdu.newe.here.page.main.variousdata;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.variousdata.bean.VariousDataBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author pope
 */
public class BuffDataFragment extends Fragment {

    @BindView(R.id.tv_buff_identifier)
    TextView tvBuffIdentifier;
    @BindView(R.id.tv_buff_score)
    TextView tvBuffScore;
    Unbinder unbinder;

    public static BuffDataFragment newInstance() {
        BuffDataFragment fragment = new BuffDataFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buff_data, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 加载标识数据的方法
     * @param buffType 标识类型
     * @param allAttendanceRate 总体出勤率
     */
    public void loadBuffData(int buffType,Number allAttendanceRate) {
        switch (buffType) {
            case VariousDataBean.BUFF_TYPE_STUDY_HARD:
                tvBuffIdentifier.setText(R.string.buff_type_study_hard);
                break;
            case VariousDataBean.BUFF_TYPE_IRRESOLUTION:
                tvBuffIdentifier.setText(R.string.buff_type_irresolution);
                break;
            case VariousDataBean.BUFF_TYPE_SKIP_CLASS:
                tvBuffIdentifier.setText(R.string.buff_type_skip_class);
                break;
            default:
                tvBuffIdentifier.setText("ERROR");
                break;
        }
        tvBuffScore.setText(allAttendanceRate.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
