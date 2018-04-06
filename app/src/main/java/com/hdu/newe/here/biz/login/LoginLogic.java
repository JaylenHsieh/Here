package com.hdu.newe.here.biz.login;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hdu.newe.here.app.App;

import cn.bmob.v3.BmobQuery;

/**
 * Created by Jaylen Hsieh on 2018/04/05.
 */
public class LoginLogic implements LoginInterface{

    @Override
    public void getStoredUserLoginInfo(GetLoginInfoLister lister) {

    }

    @Override
    public void login(UserBean user, LoginLister lister) {
        BmobQuery<UserBean> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("userNumber",user.getUserNumber());
    }

    @Override
    public void saveLoginInfo(UserBean user) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(App.getInstance()).edit();
        editor.putString("userNumber", user.getUserNumber());
        editor.putString("IMEI",user.getImei());
        editor.putBoolean("isTeacher",user.isTeacher());
        editor.apply();

        // TODO 将数据储存到数据库中
    }
}
