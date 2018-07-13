package org.androidtown.anywhere.any_newVO;

/**
 * Created by gdtbg on 2017-07-18.
 */

import java.io.Serializable;
import java.sql.Date;


public class QaVO implements Serializable {


    private int qa_num;
    private String qa_cate;
    private String qa_title;
    private String qa_content;
    private Date qa_date;
    private String qa_nick;
    private int qa_check;


    public int getQa_num() {
        return qa_num;
    }

    public void setQa_num(int qa_num) {
        this.qa_num = qa_num;
    }

    public String getQa_cate() {
        return qa_cate;
    }

    public void setQa_cate(String qa_cate) {
        this.qa_cate = qa_cate;
    }

    public String getQa_title() {
        return qa_title;
    }

    public void setQa_title(String qa_title) {
        this.qa_title = qa_title;
    }

    public String getQa_content() {
        return qa_content;
    }

    public void setQa_content(String qa_content) {
        this.qa_content = qa_content;
    }

    public Date getQa_date() {
        return qa_date;
    }

    public void setQa_date(Date qa_date) {
        this.qa_date = qa_date;
    }

    public String getQa_nick() {
        return qa_nick;
    }

    public void setQa_nick(String qa_nick) {
        this.qa_nick = qa_nick;
    }

    public int getQa_check() {
        return qa_check;
    }

    public void setQa_check(int qa_check) {
        this.qa_check = qa_check;
    }
}
