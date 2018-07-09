package com.hdu.newe.here.page.main.profile;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdu.newe.here.R;
import com.hdu.newe.here.utils.GifSizeFilter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Jaylen Hsieh
 * @date 2018/5/1,劳动最光荣~~~
 */
public class WriteQuestionFragment extends Fragment {

    private static final int REQUEST_CODE_CHOOSE = 23;

    @BindView(R.id.btnSaveStory)
    TextView mBtnSaveStory;
    @BindView(R.id.btnPublishStory)
    TextView mBtnPublishStory;
    @BindView(R.id.inputStoryTitle)
    EditText mInputStoryTitle;
    @BindView(R.id.inputStoryContent)
    EditText mInputStoryContent;
    @BindView(R.id.layout_add_image)
    LinearLayout mLayoutAddImage;
    Unbinder unbinder;

    public WriteQuestionFragment() {
        // Required empty public constructor
    }

    public static WriteQuestionFragment newInstance() {
        Bundle args = new Bundle();
        WriteQuestionFragment fragment = new WriteQuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_question, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnSaveStory, R.id.btnPublishStory, R.id.layout_add_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSaveStory:
                break;
            case R.id.btnPublishStory:
                break;
            case R.id.layout_add_image:
                Matisse
                        .from(this)
                        .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF))
                        .theme(R.style.Matisse_Dracula)
                        .countable(true)
                        .maxSelectable(9)
                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        .gridExpectedSize(getResources()
                                .getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
            default:
                break;
        }
    }
}
