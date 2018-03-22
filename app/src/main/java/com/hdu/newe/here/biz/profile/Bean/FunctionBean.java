package com.hdu.newe.here.biz.profile.Bean;

/**
 * Created by Jaylen Hsieh on 2018/03/09.
 */

public class FunctionBean {
    private int imageId;
    private String name;
    private String arg;

    public FunctionBean(int imageId, String name,String arg) {
        this.imageId = imageId;
        this.name = name;
        this.arg = arg;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
