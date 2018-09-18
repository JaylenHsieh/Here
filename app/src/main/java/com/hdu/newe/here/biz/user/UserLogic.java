package com.hdu.newe.here.biz.user;

import android.content.Context;
import android.content.SharedPreferences;

import com.hdu.newe.here.biz.user.entity.UserBean;
import com.hdu.newe.here.biz.BaseLogic;
import com.hdu.newe.here.page.main.profile.PersonalInfoActivity;
import com.hdu.newe.here.utils.db;

import java.util.Calendar;
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
    public void login(final String userNumber, final String imei, final boolean isTeacher, final boolean isInstructor, final LoginListener listener) {
        BmobQuery<UserBean> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("userNumber", userNumber);
        bmobQuery.findObjects(new FindListener<UserBean>() {
            @Override
            public void done(List<UserBean> list, BmobException e) {
                if (e != null) {
                    listener.onFailure(e.getMessage());
                    return;
                }

                // 校验学号是否被注册过
                if (list == null || list.isEmpty()) {

                    BmobQuery<UserBean> query = new BmobQuery<>();
                    query.addWhereEqualTo("imei", imei);
                    query.findObjects(new FindListener<UserBean>() {
                        @Override
                        public void done(List<UserBean> list, BmobException e) {
                            if (e != null) {
                                listener.onFailure(e.getMessage());
                                return;
                            }
                            // 校验IMEI是否被注册过
                            if (list == null || list.isEmpty()) {
                                // register
                                register(userNumber, imei, isTeacher, isInstructor, listener);
                                return;
                            } else {
                                listener.onFailure("该IMEI已绑定其他学号");
                            }
                        }
                    });


                } else {
                    UserBean userBean = list.get(0);
                    if (imei.equals(userBean.getImei())) {
                        saveLoginInfo(userBean,userBean.getObjectId());
                        listener.onLoginSuccess();
                    } else {
                        listener.onFailure("学号与IMEI不匹配");
                    }
                }
            }
        });
    }

    private void register(String userNumber, String imei, boolean isTeacher, boolean isInstructor, final LoginListener listener) {
        final UserBean userBean = new UserBean();
        userBean.setUserNumber(userNumber);
        userBean.setImei(imei);
        Calendar calendar = Calendar.getInstance();
        userBean.setImeiTimeLimit(calendar.getTimeInMillis());
        userBean.setIsTeacher(isTeacher);
        userBean.setIsInstructor(isInstructor);

        userBean.save(new SaveListener<String>() {
            @Override
            public void done(String objId, BmobException e) {
                if (e != null) {
                    listener.onFailure(e.getMessage());
                    return;
                }

                saveLoginInfo(userBean, objId);
                listener.onLoginSuccess();
            }
        });
    }

    private void saveLoginInfo(UserBean user, String objId) {
        SharedPreferences.Editor editor = getContext().getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("objId", objId);
        editor.putString("userName",user.getUserName());
        editor.putString("userNumber", user.getUserNumber());
        editor.putString("userClass",user.getUserClass());
        editor.putString("userClassNum",user.getUserClassNum());
        editor.putString("userMajor",user.getUserMajor());
        editor.putString("userCollege",user.getUserCollege());
        editor.putString("userInstructor",user.getUserInstructor());
        editor.putString("IMEI", user.getImei());
        editor.putBoolean("isTeacher", user.isTeacher());
        editor.putBoolean(db.IS_INSTRUCTOR,user.isInstructor());
        // 把leaveRequestOIbjId 放到本地
        editor.putString(PersonalInfoActivity.LEAVE_REQUEST_OBJ_ID,user.getLeaveRequestObjId());
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
