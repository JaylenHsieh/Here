package com.hdu.newe.here.page.main.variousdata.teacher;


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
public class BuffDataFragmentT extends Fragment {


    public static BuffDataFragmentT newInstance() {
        BuffDataFragmentT fragment = new BuffDataFragmentT();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buff_data_fragment_t, container, false);
    }

}
