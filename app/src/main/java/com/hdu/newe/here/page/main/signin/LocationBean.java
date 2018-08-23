package com.hdu.newe.here.page.main.signin;

/**
 * @author pope
 * @date 2018/8/21
 */

public class LocationBean {

    double latitude;
    double longitude;
    int errorCode;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
