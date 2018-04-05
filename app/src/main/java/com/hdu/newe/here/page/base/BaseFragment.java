package com.hdu.newe.here.page.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by Jaylen Hsieh on 2017/11/19.
 */

public class BaseFragment extends Fragment {

    public void showMessage(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
