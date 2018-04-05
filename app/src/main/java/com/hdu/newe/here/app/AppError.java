package com.hdu.newe.here.app;

/**
 * Created by Jaylen Hsieh on 2018/04/05.
 * 使用方法：AppError.IMEI_NOT_MATCH_STUDENT_NUMBER.toString
 */
public enum AppError {
    // 与用户相关的错误列表
    IMEI_NOT_MATCH_STUDENT_NUMBER(1,"用户名与IMEI不匹配"),

    // 其他错误
    NETWORK_LINK_ERROR(2,"网络连接异常");

    private int errorCode;
    private String description;

    private AppError(int errorCode, String description){
        this.errorCode = errorCode;
        this.description = description;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.errorCode + "_" + this.description;
    }
}
