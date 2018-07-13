package org.androidtown.anywhere.any_newVO;

import java.io.Serializable;

/**
 * Created by gdtbg on 2017-07-18.
 */

public class MasterVO implements Serializable {


    private String master_id;
    private String master_pass1;
    private String master_pass2;


    public String getMaster_id() {
        return master_id;
    }

    public void setMaster_id(String master_id) {
        this.master_id = master_id;
    }

    public String getMaster_pass1() {
        return master_pass1;
    }

    public void setMaster_pass1(String master_pass1) {
        this.master_pass1 = master_pass1;
    }

    public String getMaster_pass2() {
        return master_pass2;
    }

    public void setMaster_pass2(String master_pass2) {
        this.master_pass2 = master_pass2;
    }
}
