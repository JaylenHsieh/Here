package com.hdu.newe.here.page.main.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.user.entity.UserBean;
import com.hdu.newe.here.page.base.BaseFragment;
import com.hdu.newe.here.page.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

import static android.content.Context.MODE_PRIVATE;

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
    LinearLayout mAboutUsNew;
    Unbinder unbinder;
    @BindView(R.id.ic_common_question_new)
    ImageView mIcCommonQuestionNew;
    @BindView(R.id.ic_about_us_new)
    ImageView mIcAboutUsNew;
    @BindView(R.id.tvUserName)
    TextView mTvUserName;
    @BindView(R.id.tvUserNumber)
    TextView mTvUserNumber;


    private Context mContext;
    private final String IS_FIRST_COMMON_QUESTION = "isFirstCommonQuestion";
    private final String IS_FIRST_ABOUT_US = "isFirstAboutUs";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        if (mContext == null) {
            mContext = getContext();
        }
        unbinder = ButterKnife.bind(this, view);
        SharedPreferences pref = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        String objectId = pref.getString("objId", "");
        BmobQuery<UserBean> user = new BmobQuery<>();
        user.getObject(objectId, new QueryListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                if (e == null) {
                    mTvUserName.setText(userBean.getUserName());
                    mTvUserNumber.setText(userBean.getUserNumber());
                } else {
                    Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                mContext.startActivity(new Intent(mContext, PersonalInfoActivity.class));
                break;
            case R.id.change_phone:
                EditPhoneNumberDialog editDialog = new EditPhoneNumberDialog();
                editDialog.show(((MainActivity) mContext).getSupportFragmentManager(), "editDialog");
                break;
            case R.id.common_question:
                isFirstClickCommonQuestion();
                Intent intentQuestion = new Intent(mContext, QuestionActivity.class);
                mContext.startActivity(intentQuestion);
                break;
            case R.id.feedback:
                FeedbackDialog feedbackDialog = new FeedbackDialog();
                feedbackDialog.show(((MainActivity) mContext).getSupportFragmentManager(), "feedbackDialog");
                break;
            case R.id.check_update:
                Toast.makeText(mContext, "介已经系船新的版本了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_us:
                isFirstClickAboutUs();
                Intent intent = new Intent(mContext, AboutUsActivity.class);
                mContext.startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void isFirstClickCommonQuestion() {
        SharedPreferences preferences = mContext.getSharedPreferences("is", MODE_PRIVATE);
        boolean isFirst = preferences.getBoolean(IS_FIRST_COMMON_QUESTION, true);
        SharedPreferences.Editor editor = preferences.edit();
        if (isFirst) {
            editor.putBoolean(IS_FIRST_COMMON_QUESTION, false).apply();
        } else {
            mIcCommonQuestionNew.setVisibility(View.INVISIBLE);
        }
    }

    private void isFirstClickAboutUs() {
        SharedPreferences preferences = mContext.getSharedPreferences("is", MODE_PRIVATE);
        boolean isFirst = preferences.getBoolean(IS_FIRST_ABOUT_US, true);
        SharedPreferences.Editor editor = preferences.edit();
        if (isFirst) {
            editor.putBoolean(IS_FIRST_ABOUT_US, false).apply();
        } else {
            mIcAboutUsNew.setVisibility(View.INVISIBLE);
        }
    }
}
