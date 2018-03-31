package com.hdu.newe.here.utils;

import android.content.Context;

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
}
