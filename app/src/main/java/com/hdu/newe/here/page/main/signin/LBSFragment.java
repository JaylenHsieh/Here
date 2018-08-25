package com.hdu.newe.here.page.main.signin;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.profile.bean.ClassDataBean;
import com.hdu.newe.here.biz.signin.bean.SignInDataBean;
import com.hdu.newe.here.biz.user.entity.UserBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

import static android.content.Context.SENSOR_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LBSFragment extends Fragment implements SensorEventListener {

    @BindView(R.id.tv_class_teacher2)
    TextView tvClassTeacher2;
    @BindView(R.id.tv_class_name2)
    TextView tvClassName2;
    @BindView(R.id.tv_class_place2)
    TextView tvClassPlace2;
    @BindView(R.id.tv_class_time2)
    TextView tvClassTime2;
    @BindView(R.id.tv_student_number2)
    TextView tvStudentNumber2;
    Unbinder unbinder;
    @BindView(R.id.btn_check2)
    TextView btnCheck2;
    @BindView(R.id.btn_check3)
    TextView btnCheck3;
    @BindView(R.id.imgview_check3)
    ImageView imgviewCheck3;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.btn_check_cancel)
    TextView btnCheckCancel;
    @BindView(R.id.btn_check_refresh)
    TextView btnCheckRefresh;
    @BindView(R.id.group1_signin_icon)
    ConstraintLayout group1SigninIcon;
    @BindView(R.id.group2_subject_message)
    ConstraintLayout group2SubjectMessage;
    @BindView(R.id.group3_signin_check_ispresent)
    ConstraintLayout group3SigninCheckIspresent;
    @BindView(R.id.group4_checking)
    ConstraintLayout group4Checking;
    @BindView(R.id.tv_message_title)
    TextView tvMessageTitle;
    @BindView(R.id.btn_signin_change)
    TextView btnSigninChange;
    @BindView(R.id.btn_signin_check)
    TextView btnSigninCheck;
    @BindView(R.id.btn_signin_finish)
    TextView btnSigninFinish;
    @BindView(R.id.btn_signin_cancel)
    TextView btnSigninCancel;
    @BindView(R.id.btn_signin_absent_list)
    TextView btnSigninAbsentList;
    @BindView(R.id.btn_signin_refresh)
    TextView btnSigninRefresh;
    @BindView(R.id.tv_signin_all_people)
    TextView tvSigninAllPeople;
    @BindView(R.id.tv_signin_attend_people)
    TextView tvSigninAttendPeople;
    @BindView(R.id.tv_signin_attendance)
    TextView tvSigninAttendance;
    @BindView(R.id.tv_signin_absent_people)
    TextView tvSigninAbsentPeople;
    @BindView(R.id.tv_signin_leave_people)
    TextView tvSigninLeavePeople;

    /**
     * 定位相关参数
     */
    public LocationClient mLocClient;
    public MyLocationListener myListener = new MyLocationListener();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;

    private static final int ACCURACY_CIRCLE_FILL_COLOR = 0xAAFFFF88;
    private static final int ACCURACY_CIRCLE_STROKE_COLOR = 0xAA00FF00;

    private SensorManager mSensorManager;

    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;

    /**
     * 是否首次定位
     */
    boolean isFirstLoc = true;

    private MyLocationData locData;
    private float direction;


    private ImageView imageViewNavigation;
    private ImageView imageView;
    public MapView mMapView;
    public BaiduMap mBaiduMap;

    private Calendar calendar;
    private ProgressDialog progressDialog;

    private String userObjId;
    private String userName;
    private boolean isTeacher;
    private List<String> subjectList;

    private CountDownTime mTime;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lbs, container, false);

        //初始化控件
        initWidget(view);

        //绑定监听
        bindListener();

        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    /**
     * 初始化控件
     *
     * @param view 膨胀出的视图
     */
    private void initWidget(View view) {

        imageViewNavigation = view.findViewById(R.id.imageView_location);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("正在跳转...");
        progressDialog.setTitle("请稍等！");

        //获取传感器管理服务
        mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;

        // 地图初始化
        mMapView = view.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(getActivity().getApplicationContext());
        mLocClient.registerLocationListener(myListener);

        //初始化
        initLocation();
        mLocClient.start();
        mLocClient.requestLocation();

        imageView = view.findViewById(R.id.Image_sign_on);

        calendar = Calendar.getInstance();

    }

    /**
     * 各种监听事件的绑定
     */
    private void bindListener() {
        //导航按键的监听
        imageViewNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (mCurrentMode) {
                    //定位普通态
                    case NORMAL:
                        imageViewNavigation.setImageResource(R.drawable.ic_navigation1);
                        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                                mCurrentMode, true, mCurrentMarker));
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.overlook(0);
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                        break;
                    //定位罗盘态
                    case COMPASS:
                        imageViewNavigation.setImageResource(R.drawable.ic_location);
                        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                                mCurrentMode, true, mCurrentMarker));
                        MapStatus.Builder builder1 = new MapStatus.Builder();
                        builder1.overlook(0);
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
                        break;
                    //定位跟随态
                    case FOLLOWING:
                        imageViewNavigation.setImageResource(R.drawable.ic_navigation2);
                        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                                mCurrentMode, true, mCurrentMarker));
                        break;
                    default:
                        break;
                }
            }
        });

        //签到按键的监听
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                SharedPreferences preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                userObjId = preferences.getString("objId", null);
                isTeacher = preferences.getBoolean("isTeacher", false);
                //分教师和学生的情况
                if (isTeacher) {
                    //教师
                    //检查当前是否有未完成的考勤
                    haveUnfinishedChecking();
                } else {
                    //学生
                    haveLessonNow();
                }
            }
        });

    }

    /**
     * 询问用户是否创建/加入新教学班
     */
    private void newSubject() {
        if (isTeacher) {
            //教师加载教师的布局
            tvClassTeacher2.setText("您当前没有教学班正在上课，是否需要创建一个新教学班？");
            tvClassName2.setVisibility(View.INVISIBLE);
            tvClassPlace2.setVisibility(View.INVISIBLE);
            tvClassTime2.setVisibility(View.INVISIBLE);
            tvStudentNumber2.setVisibility(View.INVISIBLE);
            btnCheck2.setText("创建");
            btnCheck2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //跳转到创建新课程
                    Intent intent = new Intent(getActivity(), NewSubjectActivity.class);
                    startActivity(intent);
                    hideAndShowGroup(2,1);
                }
            });
        } else {
            //学生加载学生的布局
            tvClassTeacher2.setText("您当前没有教学班正在上课，是否需要加入一个新教学班？");
            tvClassName2.setVisibility(View.INVISIBLE);
            tvClassPlace2.setVisibility(View.INVISIBLE);
            tvClassTime2.setVisibility(View.INVISIBLE);
            tvStudentNumber2.setVisibility(View.INVISIBLE);
            btnCheck2.setText("加入");
            btnCheck2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO 跳转到加入新课程

                    hideAndShowGroup(2,1);
                }
            });
        }
        //取消按钮的监听
        btnCheck3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideAndShowGroup(2,1);
            }
        });
        progressDialog.dismiss();
    }

    /**
     * 加载当前教学班信息
     *
     * @param subjectCode 当前教学班的subjectCode
     */
    private void loadSubjectMsg(String subjectCode) {
        //TODO 从ClassDataBean加载当前教学班的信息
        BmobQuery<ClassDataBean> query = new BmobQuery<>();
        query.addWhereEqualTo("subjectCode", subjectCode);
        query.findObjects(new FindListener<ClassDataBean>() {
            @Override
            public void done(List<ClassDataBean> list, BmobException e) {
                if (e == null) {
                    ClassDataBean classDataBean = list.get(0);
                    BmobQuery<UserBean> query1 = new BmobQuery<>();
                    query1.getObject(classDataBean.getTeacherId(), new QueryListener<UserBean>() {
                        @Override
                        public void done(UserBean userBean, BmobException e) {
                            if (e == null) {
                                tvClassTeacher2.setText("任课教师：" + userBean.getUserName());
                            } else {
                                Toast.makeText(getActivity(), "error123\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    tvClassName2.setText("课程名称：" + classDataBean.getSubjectName());
                    String placeCode = classDataBean.getPlaceCode();
                    int building = Integer.valueOf(placeCode.substring(0, 2));
                    int classroom = Integer.valueOf(placeCode.substring(2));
                    tvClassPlace2.setText("上课地点：" + building + "教" + classroom);
                    tvClassTime2.setText("上课时间：" + classDataBean.changeToDescription(classDataBean.getSubjectCode().substring(3)));
                    tvStudentNumber2.setText("课程人数：" + classDataBean.getClassMember().size() + "人");
                    if (isTeacher) {
                        //教师加载教师的监听
                        btnCheck2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //TODO 教师开始考勤的逻辑
                            }
                        });
                    } else {
                        //学生加载学生的监听
                        btnCheck2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //TODO 学生开始签到的逻辑
                            }
                        });
                    }
                    //取消按钮的监听
                    btnCheck3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            hideAndShowGroup(2,1);
                        }
                    });
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "error741\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 查询用户当前是否有课
     */
    private void haveLessonNow() {
        //TODO 查询用户当前是否有课 若有则将该课的subjectCode赋值给nowLessonId并返回true
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.getObject(userObjId, new QueryListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                if (e == null) {
                    List<String> subjectList = userBean.getSubjectList();
                    if (subjectList == null || subjectList.size() == 0) {
                        //用户该字段还没有数据的情况，也就是没课的情况，则询问用户是否需要创建/加入新的教学班
                        newSubject();
                    } else {
                        //用户该字段有数据，检验有没有当前时间段的课程
                        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                        //idList暂存同一工作日的课程Id
                        List<String> idList = new ArrayList<>();
                        for (int i = 0; i < subjectList.size(); i++) {
                            String subjectId = subjectList.get(i);
                            if (subjectId.length() == 11 && Integer.valueOf(subjectId.substring(7, 8)) == dayOfWeek) {
                                idList.add(subjectId.substring(8, 11) + String.valueOf(i));
                            } else {
                                if (Integer.valueOf(subjectId.substring(3, 4)) == dayOfWeek) {
                                    idList.add(subjectId.substring(4, 7) + String.valueOf(i));
                                }
                            }
                        }
                        ClassDataBean classDataBean = new ClassDataBean();
                        int timeCodeNow = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
                        for (int j = 0; j < idList.size(); j++) {
                            String id = idList.get(j);
                            int startTime = Integer.valueOf(classDataBean.changeToMinTime(id.substring(0, 3)).substring(0, 4));
                            int finishTime = Integer.valueOf(classDataBean.changeToMinTime(id.substring(0, 3)).substring(4, 8));
                            if (timeCodeNow >= startTime && timeCodeNow <= finishTime) {
                                //说明当前有课，并加载该课程信息
                                loadSubjectMsg(subjectList.get(Integer.valueOf(id.substring(3, 4))));
                                return;
                            }
                        }
                        //则剩下的情况就是当前没课的情况，询问用户是否需要创建/加入新的教学班
                        newSubject();
                    }
                    //TODO
                } else {
                    Toast.makeText(getActivity(), "error852\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 加载未完成考勤的教学班信息 继续考勤
     *
     * @param unfinishedCheckingId 未完成考勤的教学班的subjectCode
     */
    private void loadUnfinishedCheckingMsg(String unfinishedCheckingId) {
        //TODO 通过查询ClassDataBean中subjectCode字段与unfinishedCheckingId相等的数据并加载到Group4中
        progressDialog.dismiss();
    }

    /**
     * 检查教师用户是否存在之前未结束的考勤
     * 根据检查结果的不同做对应不同的逻辑操作
     */
    private void haveUnfinishedChecking() {
        BmobQuery<SignInDataBean> query = new BmobQuery();
        query.addWhereEqualTo("teacherId", userObjId);
        query.addWhereEqualTo("isChecking", true);
        query.findObjects(new FindListener<SignInDataBean>() {
            @Override
            public void done(List<SignInDataBean> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.size() == 0) {
                        //没有未结束的考勤 则正常查询当前是否有课
                        haveLessonNow();
                    } else {
                        //如果老师之前有未结束的考勤 则先加载未结束的那次考勤
                        //TODO
                        loadUnfinishedCheckingMsg(list.get(0).getSubjectCode());
                    }
                } else {
                    Toast.makeText(getActivity(), "error963\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        int span = 1000;
        option.setScanSpan(span);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        // 如果设置为0，则代表单次定位，即仅定位一次，默认为0
        // 如果设置非0，需设置1000ms以上才有效


        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        // 使用高精度和仅用设备两种定位模式的，参数必须设置为true

        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setLocationNotify(true);

        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);

        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);

        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);

        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);

        //可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setEnableSimulateGps(false);

        mLocClient.setLocOption(option);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_check_cancel, R.id.btn_check_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_check_cancel:
                break;
            case R.id.btn_check_refresh:
                break;
            default:
                break;
        }
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }

            //获取纬度信息
            mCurrentLat = location.getLatitude();
            //获取经度信息
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();

            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        // 退出时销毁定位
        if (mLocClient != null) {
            mLocClient.stop();
        }
        // 关闭定位图层
        if (mBaiduMap != null) {
            mBaiduMap.setMyLocationEnabled(false);
        }
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        super.onDestroy();

    }

    /**
     * 获取当前位置
     *
     * @return 返回一个包含经纬度或错误信息的Bean
     */
    private LocationBean getLocation() {

        LocationBean bean = new LocationBean();
        if (mCurrentLat == 4.9E-324 && mCurrentLon == 4.9E-324) {
            BDLocation bdLocation = new BDLocation();
            bean.setErrorCode(bdLocation.getLocType());
            return bean;
        }
        bean.setLatitude(mCurrentLat);
        bean.setLongitude(mCurrentLon);
        return bean;

    }

    /**
     * 更改签到详情页是否显示
     */
    public void hideAndShowGroup(int hideGroup, int showGroup) {
        switch (hideGroup) {
            case 1:
                group1SigninIcon.setVisibility(View.INVISIBLE);
                break;
            case 2:
                group2SubjectMessage.setVisibility(View.INVISIBLE);
                break;
            case 3:
                group3SigninCheckIspresent.setVisibility(View.INVISIBLE);
                break;
            case 4:
                group4Checking.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
        switch (showGroup) {
            case 1:
                group1SigninIcon.setVisibility(View.VISIBLE);
                break;
            case 2:
                group2SubjectMessage.setVisibility(View.VISIBLE);
                break;
            case 3:
                group3SigninCheckIspresent.setVisibility(View.VISIBLE);
                break;
            case 4:
                group4Checking.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    /**
     * 自定义倒计时器 用于倒计时刷新按钮
     */
    class CountDownTime extends CountDownTimer {

        /**
         * 构造函数
         *
         * @param millisInFuture    总计时时长（单位毫秒）
         * @param countDownInterval 计时间隔时长（单位毫秒）
         */
        public CountDownTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        /**
         * 每计时一次回调一次该方法
         */
        @Override
        public void onTick(long l) {

        }

        /**
         * 计时结束回调该方法
         */
        @Override
        public void onFinish() {

        }
    }

}

