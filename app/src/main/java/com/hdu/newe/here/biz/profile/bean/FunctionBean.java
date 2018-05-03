package com.hdu.newe.here.biz.profile.bean;

/**
 * Created by Jaylen Hsieh on 2018/03/09.
 */

public class FunctionBean {
    private int imageId;
    private String name;

    public FunctionBean(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
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

}
