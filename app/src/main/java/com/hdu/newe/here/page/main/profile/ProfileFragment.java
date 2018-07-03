package com.hdu.newe.here.page.main.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hdu.newe.here.R;
import com.hdu.newe.here.page.base.BaseFragment;
import com.hdu.newe.here.page.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Jaylen Hsieh on 2017/11/19.
 */

public class ProfileFragment extends BaseFragment<ProfileContract.Presenter> implements ProfileContract.View {


    @BindView(R.id.personal_info)
    LinearLayout mPersonalInfo;
    @BindView(R.id.change_phone)
    LinearLayout mChangePhone;
    @BindView(R.id.common_question)
    LinearLayout mCommonQuestion;
    @BindView(R.id.feedback)
    LinearLayout mFeedback;
    @BindView(R.id.check_update)
    LinearLayout mCheckUpdate;
    @BindView(R.id.about_us)
    LinearLayout mAboutUs;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.personal_info, R.id.change_phone, R.id.common_question, R.id.feedback, R.id.check_update, R.id.about_us})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.personal_info:
                getContext().startActivity(new Intent(getContext(),PersonalInfoActivity.class));
                break;
            case R.id.change_phone:
                EditPhoneNumberDialog editDialog = new EditPhoneNumberDialog();
                editDialog.show(((MainActivity)getContext()).getSupportFragmentManager(),"editDialog");
                break;
            case R.id.common_question:
                Intent intentQuestion = new Intent(getContext(), QuestionActivity.class);
                getContext().startActivity(intentQuestion);
                break;
            case R.id.feedback:
                FeedbackDialog feedbackDialog = new FeedbackDialog();
                feedbackDialog.show(((MainActivity)getContext()).getSupportFragmentManager(),"feedbackDialog");
                break;
            case R.id.check_update:
                Toast.makeText(view.getContext(), "介已经系船新的版本了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_us:
                break;
            default:
                break;
        }
    }
}
