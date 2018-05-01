package com.hdu.newe.here.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Jaylen Hsieh on 2018/03/10.
 */

public class UIUtils {

    public static int dp2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // 透明化状态栏
    public static void transparentStatusBar(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


//    public void navigate(Activity activity, Class<?> clz, @Nullable Bundle bundle, int requestCode){
//        Intent intent = new Intent(activity, clz);
//
//        if (bundle != null){
//            intent.putExtras(bundle);
//        }
//
//        if (requestCode != -1){
//            activity.startActivityForResult(intent, requestCode);
//        } else {
//            activity.startActivity(intent);
//        }
//    }
//
//    public void navigate(Activity activity, Class<?> clz, @NonNull Bundle bundle){
//        navigate(activity, clz, bundle, -1);
//    }
//
//    public void navigate(Activity activity, Class<?> clz, int requestCode){
//        navigate(activity, clz, null, requestCode);
//    }
}
