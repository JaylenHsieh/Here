package com.hdu.newe.here.biz;

import android.content.Context;

import com.hdu.newe.here.biz.variousdata.VariousDataInterface;
import com.hdu.newe.here.biz.variousdata.VariousDataLogic;

import java.lang.ref.WeakReference;

/**
 *
 * @author pope
 * @date 2018/4/6
 */

public class ModelFactory {

    private static WeakReference<Context> mContextReference;

    public static void init(Context context) {
        mContextReference = new WeakReference<Context>(context);
    }

    public static VariousDataInterface getVariousDataInterface(){
        return VariousDataLogic.getINSTANCE(mContextReference.get());
    }

}
