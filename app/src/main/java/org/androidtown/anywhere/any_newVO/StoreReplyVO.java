package org.androidtown.anywhere.any_newVO;

/**
 * Created by gdtbg on 2017-07-18.
 */

import java.io.Serializable;
import java.util.Date;

public class StoreReplyVO implements Serializable {


    private int store_num;
    private int reply_ref;
    private int reply_lev;
    private int reply_seq;
    private String reply_nick;
    private String reply_content;
    private Double reply_star;
    private Date reply_date;
    private int reply_starcount;
    private Double reply_staravg;


    public int getReply_starcount() {
        return reply_starcount;
    }

    public void setReply_starcount(int reply_starcount) {
        this.reply_starcount = reply_starcount;
    }

    public Double getReply_staravg() {
        return reply_staravg;
    }

    public void setReply_staravg(Double reply_staravg) {
        this.reply_staravg = reply_staravg;
    }

    public int getStore_num() {
        return store_num;
    }

    public void setStore_num(int store_num) {
        this.store_num = store_num;
    }

    public int getReply_ref() {
        return reply_ref;
    }

    public void setReply_ref(int reply_ref) {
        this.reply_ref = reply_ref;
    }

    public int getReply_lev() {
        return reply_lev;
    }

    public void setReply_lev(int reply_lev) {
        this.reply_lev = reply_lev;
    }

    public int getReply_seq() {
        return reply_seq;
    }

    public void setReply_seq(int reply_seq) {
        this.reply_seq = reply_seq;
    }

    public String getReply_nick() {
        return reply_nick;
    }

    public void setReply_nick(String reply_nick) {
        this.reply_nick = reply_nick;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public Double getReply_star() {
        return reply_star;
    }

    public void setReply_star(Double reply_star) {
        this.reply_star = reply_star;
    }

    public Date getReply_date() {
        return reply_date;
    }

    public void setReply_date(Date reply_date) {
        this.reply_date = reply_date;
    }
}
