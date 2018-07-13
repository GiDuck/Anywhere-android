package org.androidtown.anywhere.any_newVO;

import java.io.Serializable;

/**
 * Created by gdtbg on 2017-07-18.
 */

public class PictureVO implements Serializable {

    private int store_num;
    private String picture_url;

    public int getStore_num() {
        return store_num;
    }

    public void setStore_num(int store_num) {
        this.store_num = store_num;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }
}
