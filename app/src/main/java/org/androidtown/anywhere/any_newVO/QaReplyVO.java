package org.androidtown.anywhere.any_newVO;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by gdtbg on 2017-07-18.
 */

public class QaReplyVO implements Serializable {

    private int qa_num;
    private int reply_ref;
    private int reply_seq;
    private int reply_lev;
    private String reply_content;
    private String reply_nick;
    private Date reply_date;


    public int getQa_num() {
        return qa_num;
    }

    public void setQa_num(int qa_num) {
        this.qa_num = qa_num;
    }

    public int getReply_ref() {
        return reply_ref;
    }

    public void setReply_ref(int reply_ref) {
        this.reply_ref = reply_ref;
    }

    public int getReply_seq() {
        return reply_seq;
    }

    public void setReply_seq(int reply_seq) {
        this.reply_seq = reply_seq;
    }

    public int getReply_lev() {
        return reply_lev;
    }

    public void setReply_lev(int reply_lev) {
        this.reply_lev = reply_lev;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public Date getReply_date() {
        return reply_date;
    }

    public void setReply_date(Date reply_date) {
        this.reply_date = reply_date;
    }

    public String getReply_nick() {
        return reply_nick;
    }

    public void setReply_nick(String reply_nick) {
        this.reply_nick = reply_nick;
    }
}
