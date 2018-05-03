package com.hdu.newe.here.biz;

import android.content.Context;

import com.hdu.newe.here.biz.user.UserInterface;
import com.hdu.newe.here.biz.user.UserLogic;
import com.hdu.newe.here.biz.variousdata.student.VariousDataInterface;
import com.hdu.newe.here.biz.variousdata.student.VariousDataLogic;
import com.hdu.newe.here.biz.variousdata.teacher.VariousDataInterfaceT;
import com.hdu.newe.here.biz.variousdata.teacher.VariousDataLogicT;

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

    public static VariousDataInterfaceT getVariousDataInterfaceT(){
        return VariousDataLogicT.getINSTANCE();
    }

    public static UserInterface getUserInterface() {
        return UserLogic.getInstance();
    }


}
