package org.androidtown.anywhere.any_newVO;

/**
 * Created by gdtbg on 2017-07-18.
 */

import java.io.Serializable;
import java.util.Date;
public class NoticeVO implements Serializable {

    private int notice_num;
    private String notice_cate;
    private String notice_title;
    private String notice_content;
    private String notice_url;
    private Date notice_date;

    public int getNotice_num() {
        return notice_num;
    }

    public void setNotice_num(int notice_num) {
        this.notice_num = notice_num;
    }

    public String getNotice_cate() {
        return notice_cate;
    }

    public void setNotice_cate(String notice_cate) {
        this.notice_cate = notice_cate;
    }

    public String getNotice_title() {
        return notice_title;
    }

    public void setNotice_title(String notice_title) {
        this.notice_title = notice_title;
    }

    public String getNotice_content() {
        return notice_content;
    }

    public void setNotice_content(String notice_content) {
        this.notice_content = notice_content;
    }

    public String getNotice_url() {
        return notice_url;
    }

    public void setNotice_url(String notice_url) {
        this.notice_url = notice_url;
    }

    public Date getNotice_date() {
        return notice_date;
    }

    public void setNotice_date(Date notice_date) {
        this.notice_date = notice_date;
    }
}
