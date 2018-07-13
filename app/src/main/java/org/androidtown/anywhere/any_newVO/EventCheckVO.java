package org.androidtown.anywhere.any_newVO;

import java.io.Serializable;

/**
 * Created by gdtbg on 2017-07-18.
 */

public class EventCheckVO implements Serializable {

    private int event_num;
    private String check_nick;

    public int getEvent_num() {
        return event_num;
    }

    public void setEvent_num(int event_num) {
        this.event_num = event_num;
    }

    public String getCheck_nick() {
        return check_nick;
    }

    public void setCheck_nick(String check_nick) {
        this.check_nick = check_nick;
    }
}
