package com.hdu.newe.here.page.main.leaverequest;

import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hdu.newe.here.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author pope
 */
public class LeaveRequestActivity2 extends Activity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mTvUserName)
    TextView mTvUserName;
    @BindView(R.id.mTvUserNumber)
    TextView mTvUserNumber;
    @BindView(R.id.mTvStuFaculty)
    TextView mTvStuFaculty;
    @BindView(R.id.mTvStuSpeciality)
    TextView mTvStuSpeciality;
    @BindView(R.id.mTvStuInstructor)
    TextView mTvStuInstructor;
    @BindView(R.id.view_account)
    ConstraintLayout viewAccount;
    @BindView(R.id.edRequestContent)
    EditText edRequestContent;
    @BindView(R.id.view_request)
    FrameLayout viewRequest;
    @BindView(R.id.tvStartTime)
    TextView tvStartTime;
    @BindView(R.id.tvFinishTime)
    TextView tvFinishTime;
    @BindView(R.id.leaveRequestState)
    TextView leaveRequestState;
    @BindView(R.id.view_time)
    ConstraintLayout viewTime;
    @BindView(R.id.view_submit)
    FrameLayout viewSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_leave_request);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.tvStartTime, R.id.tvFinishTime, R.id.view_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvStartTime:
                break;
            case R.id.tvFinishTime:
                break;
            case R.id.view_submit:
                break;
            default:
                break;
        }
    }
}