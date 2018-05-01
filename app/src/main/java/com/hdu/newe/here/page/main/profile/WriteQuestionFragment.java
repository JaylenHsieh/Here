package com.hdu.newe.here.page.main.profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdu.newe.here.R;

/**
 * @author Jaylen Hsieh
 * @date 2018/5/1,劳动最光荣~~~
 */
public class WriteQuestionFragment extends Fragment {


    public WriteQuestionFragment() {
        // Required empty public constructor
    }

    public static WriteQuestionFragment newInstance(){
        Bundle args = new Bundle();
        WriteQuestionFragment fragment = new WriteQuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write_question, container, false);
    }

}