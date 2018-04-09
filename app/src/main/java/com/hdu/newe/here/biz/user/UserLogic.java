package com.hdu.newe.here.biz.user;

import android.content.Context;
import android.content.SharedPreferences;

import com.hdu.newe.here.biz.user.entity.UserBean;
import com.hdu.newe.here.biz.BaseLogic;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Jaylen Hsieh on 2018/04/05.
 */
public class UserLogic extends BaseLogic implements UserInterface {



    @Override
    public void login(final String userNumber, final String imei, final boolean isTeacher, final LoginLister lister) {
        BmobQuery<UserBean> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("userNumber", userNumber);
        bmobQuery.findObjects(new FindListener<UserBean>() {
            @Override
            public void done(List<UserBean> list, BmobException e) {
                if (e != null) {
                    lister.onFailure("");
                    return;
                }

                if (list == null || list.isEmpty()) {
                    // register
                    register(userNumber, imei, isTeacher, lister);
                    return;
                }

                final UserBean userBean = new UserBean();
                userBean.setUserNumber(userNumber);
                userBean.setImei(imei);
                userBean.setIsTeacher(isTeacher);
                saveLoginInfo(userBean);

                lister.onLoginSuccess();
            }
        });
    }

    private void register(String userNumber, String imei, boolean isTeacher, final LoginLister listener) {
        final UserBean userBean = new UserBean();
        userBean.setUserNumber(userNumber);
        userBean.setImei(imei);
        userBean.setIsTeacher(isTeacher);

        userBean.save(new SaveListener<String>() {
            @Override
            public void done(String objId, BmobException e) {
                if (e != null) {
                    listener.onFailure("");
                    return;
                }

                saveLoginInfo(userBean);
                listener.onLoginSuccess();
            }
        });
    }

    private void saveLoginInfo(UserBean user) {
        SharedPreferences.Editor editor = getContext().getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("userNumber", user.getUserNumber());
        editor.putString("IMEI", user.getImei());
        editor.putBoolean("isTeacher", user.isTeacher());
        editor.apply();

    }

    @Override
    public boolean isUserLogin() {
        Context context = getContext();
        if (context == null) {
            return false;
        }

        String userNumber = context.getSharedPreferences("user", Context.MODE_PRIVATE)
                .getString("userNumber", null);

        return userNumber != null && !userNumber.isEmpty();
    }


    private static class Holder {
        static UserLogic INSTANCE = new UserLogic();
    }

    public static UserLogic getInstance() {
        return Holder.INSTANCE;
    }

    private UserLogic() {
    }


}
