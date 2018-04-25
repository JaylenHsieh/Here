package com.hdu.newe.here.page.main.signin;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.hdu.newe.here.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignOnFragment extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //3 在此处设置 无标题 对话框背景色
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // dialog的背景色
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
//        getDialog().getWindow().setDimAmount(0.8f);//背景黑暗度


//        Window dialogWindow = getDialog().getWindow();
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.gravity = Gravity.CENTER;//改变在屏幕中的位置,如果需要改变上下左右具体的位置，比如100dp，则需要对布局设置margin
//        Display defaultDisplay = getActivity().getWindowManager().getDefaultDisplay();
//        lp.width = defaultDisplay.getWidth() - 200;  //改变宽度
//        lp.height = 300;//   改变高度
//        dialogWindow.setAttributes(lp);

        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {//可以在这拦截返回键啊home键啊事件
                dialog.dismiss();
                return false;
            }
        });
        return inflater.inflate(R.layout.fragment_sign_on_dialog,container, false);
    }

}



