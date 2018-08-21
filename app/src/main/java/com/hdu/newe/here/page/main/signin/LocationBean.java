package com.hdu.newe.here.page.main.signin;

/**
 * @author pope
 * @date 2018/8/21
 */

public class LocationBean {

    double mCurrentLat;
    double mCurrentLon;
    int errorCode;

    public double getmCurrentLat() {
        return mCurrentLat;
    }

    public void setmCurrentLat(double mCurrentLat) {
        this.mCurrentLat = mCurrentLat;
    }

    public double getmCurrentLon() {
        return mCurrentLon;
    }

    public void setmCurrentLon(double mCurrentLon) {
        this.mCurrentLon = mCurrentLon;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
