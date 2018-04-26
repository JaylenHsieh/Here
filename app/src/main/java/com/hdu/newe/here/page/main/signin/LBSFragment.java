package com.hdu.newe.here.page.main.signin;


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

import static android.content.Context.SENSOR_SERVICE;

//import com.baidu.mapapi.SDKInitializer;
//import com.baidu.mapapi.map.TextureMapView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LBSFragment extends Fragment {

    private SignOnFragment signOnFragment;

    private boolean isDispaly = false;

    /**
     * 定位相关参数
     */
    public LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
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

    private ConstraintLayout layoutGroup1;
    private ConstraintLayout layoutGroup2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lbs, container, false);

        //初始化控件
        initWidgt(view);

        //绑定监听
        bindListener();

        return view;

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
            }
        });

    }

    /**
     * 初始化控件
     *
     * @param view 膨胀出的视图
     */
    private void initWidgt(View view) {

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

    /**
     * 初始化定位
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");
        int span = 1000;
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setScanSpan(span);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
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


    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
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

    private SensorEventListener mEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public void test() {
        mSensorManager.registerListener(
                mEventListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI
        );
    }//为系统的方向传感器注册监听器

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(mEventListener);
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


    public boolean isDispaly() {
        return isDispaly;
    }
}

