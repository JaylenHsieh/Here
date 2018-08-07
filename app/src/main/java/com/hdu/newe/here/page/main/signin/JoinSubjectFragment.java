package com.hdu.newe.here.page.main.signin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdu.newe.here.R;

/**
 * A simple {@link Fragment} subclass.
 * @author pope
 */
public class JoinSubjectFragment extends Fragment {

    public static JoinSubjectFragment newInstance() {
        JoinSubjectFragment fragment = new JoinSubjectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_join_subject, container, false);
    }

}
