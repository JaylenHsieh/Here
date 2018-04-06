package com.hdu.newe.here.biz;

import android.content.Context;

import com.hdu.newe.here.biz.user.UserInterface;
import com.hdu.newe.here.biz.user.UserLogic;
import com.hdu.newe.here.biz.variousdata.VariousDataInterface;
import com.hdu.newe.here.biz.variousdata.VariousDataLogic;

import java.lang.ref.WeakReference;

/**
 *
 * @author pope
 * @date 2018/4/6
 */

public class ModelFactory {


    public static void init(Context context) {
        BaseLogic.initialize(context);
    }

    public static VariousDataInterface getVariousDataInterface(){
        return VariousDataLogic.getInstance();
    }

    public static UserInterface getUserInterface() {
        return UserLogic.getInstance();
    }

}
