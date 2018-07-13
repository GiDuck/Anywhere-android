package org.androidtown.anywhere.any_newVO;

import java.io.Serializable;

/**
 * Created by gdtbg on 2017-07-18.
 */

public class SelectVO implements Serializable {

    private int store_num;
    private int facility_num;


    public int getStore_num() {
        return store_num;
    }

    public void setStore_num(int store_num) {
        this.store_num = store_num;
    }

    public int getFacility_num() {
        return facility_num;
    }

    public void setFacility_num(int facility_num) {
        this.facility_num = facility_num;
    }
}
