package com.hdu.newe.here.utils;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import com.hdu.newe.here.interfaces.DataCallBack;

import java.util.Calendar;

/**
 * Created by Jaylen Hsieh on 2018/03/20.
 * 实现OnTimeSetListener接口
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    private String time = "";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //新建日历类用于获取当前时间
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //返回TimePickerDialog对象
        //因为实现了OnTimeSetListener接口，所以第二个参数直接传入this
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    /**
     * 实现OnTimeSetListener的onTimeSet方法
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //判断activity是否是DataCallBack(这是自己定义的一个接口)的一个实例
        if(getActivity() instanceof DataCallBack){
            //将activity强转为DataCallBack
            DataCallBack dataCallBack = (DataCallBack) getActivity();
            time = time + hourOfDay + "点" + minute + "分";
            //调用activity的getData方法将数据传回activity显示
            dataCallBack.getData(time);
        }
    }

    public void setTime(String date){
        time += date;
    }

}
