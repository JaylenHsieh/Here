package com.hdu.newe.here.page.main.sign_on;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdu.newe.here.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignOnFragment extends DialogFragment {


    public SignOnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TextView textView = new TextView(getActivity());
        View view = inflater.inflate(R.layout.fragment_sign_on_dialog,container,false);
        return view;
    }

}
