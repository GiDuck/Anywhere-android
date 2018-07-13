package org.androidtown.anywhere.any_newVO;

/**
 * Created by gdtbg on 2017-07-18.
 */

import java.io.Serializable;
import java.util.Date;
public class PartnerVO implements Serializable {


    private String partner_nick;
    private String partner_name;
    private String partner_num;
    private String partner_phone;
    private String partner_email;
    private String partner_request;
    private Date partner_date;
    private int partner_state;
    private String partner_url;
    private int partner_read;

    public String getPartner_nick() {
        return partner_nick;
    }

    public void setPartner_nick(String partner_nick) {
        this.partner_nick = partner_nick;
    }

    public String getPartner_name() {
        return partner_name;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }

    public String getPartner_num() {
        return partner_num;
    }

    public void setPartner_num(String partner_num) {
        this.partner_num = partner_num;
    }

    public String getPartner_phone() {
        return partner_phone;
    }

    public void setPartner_phone(String partner_phone) {
        this.partner_phone = partner_phone;
    }

    public String getPartner_email() {
        return partner_email;
    }

    public void setPartner_email(String partner_email) {
        this.partner_email = partner_email;
    }

    public String getPartner_request() {
        return partner_request;
    }

    public void setPartner_request(String partner_request) {
        this.partner_request = partner_request;
    }

    public Date getPartner_date() {
        return partner_date;
    }

    public void setPartner_date(Date partner_date) {
        this.partner_date = partner_date;
    }

    public int getPartner_state() {
        return partner_state;
    }

    public void setPartner_state(int partner_state) {
        this.partner_state = partner_state;
    }

    public String getPartner_url() {
        return partner_url;
    }

    public void setPartner_url(String partner_url) {
        this.partner_url = partner_url;
    }

    public int getPartner_read() {
        return partner_read;
    }

    public void setPartner_read(int partner_read) {
        this.partner_read = partner_read;
    }
}
