package com.hdu.newe.here.page.main.leavenoto;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hdu.newe.here.R;
import com.hdu.newe.here.interfaces.DataCallBack;
import com.hdu.newe.here.utils.DatePickerFragment;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaveNotoFragment extends Fragment implements DataCallBack {

    @BindView(R.id.tv_stu_name)
    TextView mTvStuName;
    @BindView(R.id.tv_stu_number)
    TextView mTvStuNumber;
    @BindView(R.id.tv_stu_faculty)
    TextView mTvStuFaculty;
    @BindView(R.id.tv_stu_speciality)
    TextView mTvStuSpeciality;
    @BindView(R.id.view_account)
    ConstraintLayout mViewAccount;
    @BindView(R.id.view_noto)
    FrameLayout mViewNoto;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.tv_finish_time)
    TextView mTvFinishTime;
    @BindView(R.id.view_time)
    ConstraintLayout mViewTime;
    @BindView(R.id.view_submit)
    FrameLayout mViewSubmit;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public LeaveNotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leave_noto, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity) getActivity()).finish();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.tv_start_time, R.id.tv_finish_time, R.id.view_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                //实例化对象
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                //调用show方法弹出对话框
                // 第一个参数为FragmentManager对象
                // 第二个为调用该方法的fragment的标签
                datePickerFragment.show(getFragmentManager(), "date_picker");
                break;
            case R.id.tv_finish_time:
                break;
            case R.id.view_submit:
                break;
            default:
        }
    }

    /**
     * 实现DataCallBack的getData方法
     */
    @Override
    public void getData(String data) {
        //data即为fragment调用该函数传回的日期时间
        mTvStartTime.setText(data);
    }
}
