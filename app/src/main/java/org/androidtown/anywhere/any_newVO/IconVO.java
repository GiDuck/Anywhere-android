package org.androidtown.anywhere.any_newVO;

import java.io.Serializable;

/**
 * Created by gdtbg on 2017-07-18.
 */

public class IconVO implements Serializable {

    private int facility_num;
    private String facility_icon;

    public int getFacility_num() {
        return facility_num;
    }

    public void setFacility_num(int facility_num) {
        this.facility_num = facility_num;
    }

    public String getFacility_icon() {
        return facility_icon;
    }

    public void setFacility_icon(String facility_icon) {
        this.facility_icon = facility_icon;
    }

}
