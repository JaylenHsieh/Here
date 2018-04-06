package com.hdu.newe.here.biz;

import android.content.Context;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * Created by Jaylen Hsieh on 2018/04/06.
 */
public class BaseLogic {

    private static WeakReference<Context> mContextReference;

    public static void initialize(Context context){
        mContextReference = new WeakReference<>(context);
    }

    @Nullable
    protected Context getContext() {
        return mContextReference.get();
    }
}
