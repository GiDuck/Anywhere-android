package org.androidtown.anywhere.any_newVO;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by gdtbg on 2017-07-18.
 */

public class EventVO implements Serializable {

    private int event_num;
    private int event_cate;
    private String event_title;
    private String event_content;
    private String event_url;
    private Date event_date;
    private Date event_start;
    private Date event_end;

    public int getEvent_num() {
        return event_num;
    }

    public void setEvent_num(int event_num) {
        this.event_num = event_num;
    }

    public int getEvent_cate() {
        return event_cate;
    }

    public void setEvent_cate(int event_cate) {
        this.event_cate = event_cate;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_content() {
        return event_content;
    }

    public void setEvent_content(String event_content) {
        this.event_content = event_content;
    }

    public String getEvent_url() {
        return event_url;
    }

    public void setEvent_url(String event_url) {
        this.event_url = event_url;
    }

    public Date getEvent_date() {
        return event_date;
    }

    public void setEvent_date(Date event_date) {
        this.event_date = event_date;
    }

    public Date getEvent_start() {
        return event_start;
    }

    public void setEvent_start(Date event_start) {
        this.event_start = event_start;
    }

    public Date getEvent_end() {
        return event_end;
    }

    public void setEvent_end(Date event_end) {
        this.event_end = event_end;
    }
}
