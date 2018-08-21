package com.hdu.newe.here.page.main.signin;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
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
import com.hdu.newe.here.biz.user.entity.UserBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
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

    private boolean isDispaly = false;

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
    private int calculationTime = 0;

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

    private ConstraintLayout layoutGroup1;
    private ConstraintLayout layoutGroup2;

    private Calendar calendar;

    private String userObjId;
    private String userName;
    private boolean isTeacher;
    private List<String> subjectList;

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

        layoutGroup2 = view.findViewById(R.id.layout_group2);
        layoutGroup1 = view.findViewById(R.id.layout_group1);

        imageViewNavigation = view.findViewById(R.id.imageView_location);

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
                changeVisibility();
                getSignInMsg();
                getLocation();
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

    private LocationBean getLocation() {

        LocationBean bean = new LocationBean();
        if (mCurrentLat == 4.9E-324 && mCurrentLon == 4.9E-324) {
            BDLocation bdLocation = new BDLocation();
            bean.setErrorCode(bdLocation.getLocType());
            return bean;
        }
        bean.setmCurrentLat(mCurrentLat);
        bean.setmCurrentLon(mCurrentLon);
        return bean;

    }

    private void getSignInMsg() {
        SharedPreferences preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userObjId = preferences.getString("objId", null);
        isTeacher = preferences.getBoolean("isTeacher", false);
        if (userObjId == null || userObjId.equals("")) {
            Toast.makeText(getActivity(), "登录异常", Toast.LENGTH_SHORT);
            return;
        }

        BmobQuery<UserBean> query = new BmobQuery();
        query.getObject(userObjId, new QueryListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                if (e == null) {
                    subjectList = userBean.getSubjectList();
                    showAddSubject();
                    //TODO 判定当前上课情况 有课的情况也需判定有没有当前时间段的课
//                    if (subjectList == null || subjectList.size() == 0) {
//                        //当没有课时 显示无课的fragment
//                        showAddSubject();
//                    } else {
//                        //当有课时 加载相应课程信息
//                        loadSubjectMsg();
//                    }
                } else {
                    Toast.makeText(getActivity(), "查询异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 加载课程信息
     */
    private void loadSubjectMsg() {
        //TODO 相关逻辑未完成。由于未使用到，暂且这样
        int size = subjectList.size();
        List<String> subjectTimes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            BmobQuery<ClassDataBean> query = new BmobQuery<>();
            query.getObject(subjectList.get(i), new QueryListener<ClassDataBean>() {
                @Override
                public void done(ClassDataBean classDataBean, BmobException e) {
                    if (e == null) {
                        int day = calendar.get(Calendar.DAY_OF_WEEK);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int min = calendar.get(Calendar.MINUTE);
//                        classDataBean.changeToCode(day,hour,min);
                    }
                }
            });
        }
    }

    /**
     * 显示增加课程的Fragment
     */
    private void showAddSubject() {
        //TODO 显示增加课程的Fragment
        if (isTeacher) {
            //老师 创建课程
            createSubject();
        } else {
            //学生 加入课程
            joinSubject();
        }
    }

    /**
     * 加入课程
     */
    private void joinSubject() {
        //对界面进行更改
        tvClassTeacher2.setText(R.string.join_subject_notice);
        tvClassName2.setVisibility(View.GONE);
        tvClassPlace2.setVisibility(View.GONE);
        tvClassTime2.setVisibility(View.GONE);
        tvStudentNumber2.setVisibility(View.GONE);
        btnCheck2.setText(R.string.join_subject);
        btnCheck3.setText(R.string.cancel);
        //TODO 完成相关点击业务逻辑
    }

    /**
     * 创建课程
     */
    private void createSubject() {
        //对界面进行更改
        tvClassTeacher2.setText(R.string.create_subject_notice);
        tvClassName2.setVisibility(View.INVISIBLE);
        tvClassPlace2.setVisibility(View.INVISIBLE);
        tvClassTime2.setVisibility(View.INVISIBLE);
        tvStudentNumber2.setVisibility(View.INVISIBLE);
        btnCheck2.setText(R.string.create_subject);
        btnCheck3.setText(R.string.cancel);
        btnCheck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewSubjectActivity.class);
                startActivity(intent);
            }
        });
        btnCheck3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeVisibility();
            }
        });
    }

    /**
     * 更改签到详情页是否显示
     */
    public void changeVisibility() {
        if (isDispaly) {
            layoutGroup2.setVisibility(View.INVISIBLE);
            layoutGroup1.setVisibility(View.VISIBLE);
            isDispaly = false;
        } else {
            layoutGroup2.setVisibility(View.VISIBLE);
            layoutGroup1.setVisibility(View.INVISIBLE);
            isDispaly = true;
        }
    }


    public boolean isDisplay() {
        return isDispaly;
    }
}

