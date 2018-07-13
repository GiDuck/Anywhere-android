package org.androidtown.anywhere.any_newVO;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by gdtbg on 2017-07-18.
 */


public class ReservationListVO implements Serializable {


    private int reservation_num;
    private int store_num;
    private Date reservation_date;
    private int reservation_starttime;
    private int reservation_endtime;
    private String reservation_nick;
    private String reservation_name;
    private String reservation_phone;
    private int reservation_total;
    private String reservation_request;
    private int reservation_state;
    private Date reservation_resister_date;
    private Date reservation_request_date;
    private String reservation_type;

    public int getReservation_num() {
        return reservation_num;
    }
    public void setReservation_num(int reservation_num) {
        this.reservation_num = reservation_num;
    }
    public int getStore_num() {
        return store_num;
    }
    public void setStore_num(int store_num) {
        this.store_num = store_num;
    }
    public Date getReservation_date() {
        return reservation_date;
    }
    public void setReservation_date(Date reservation_date) {
        this.reservation_date = reservation_date;
    }
    public int getReservation_starttime() {
        return reservation_starttime;
    }
    public void setReservation_starttime(int reservation_starttime) {
        this.reservation_starttime = reservation_starttime;
    }
    public int getReservation_endtime() {
        return reservation_endtime;
    }
    public void setReservation_endtime(int reservation_endtime) {
        this.reservation_endtime = reservation_endtime;
    }
    public String getReservation_nick() {
        return reservation_nick;
    }
    public void setReservation_nick(String reservation_nick) {
        this.reservation_nick = reservation_nick;
    }
    public String getReservation_name() {
        return reservation_name;
    }
    public void setReservation_name(String reservation_name) {
        this.reservation_name = reservation_name;
    }
    public String getReservation_phone() {
        return reservation_phone;
    }
    public void setReservation_phone(String reservation_phone) {
        this.reservation_phone = reservation_phone;
    }
    public int getReservation_total() {
        return reservation_total;
    }
    public void setReservation_total(int reservation_total) {
        this.reservation_total = reservation_total;
    }
    public String getReservation_request() {
        return reservation_request;
    }
    public void setReservation_request(String reservation_request) {
        this.reservation_request = reservation_request;
    }
    public int getReservation_state() {
        return reservation_state;
    }
    public void setReservation_state(int reservation_state) {
        this.reservation_state = reservation_state;
    }
    public Date getReservation_resister_date() {
        return reservation_resister_date;
    }
    public void setReservation_resister_date(Date reservation_resister_date) {
        this.reservation_resister_date = reservation_resister_date;
    }
    public Date getReservation_request_date() {
        return reservation_request_date;
    }
    public void setReservation_request_date(Date reservation_request_date) {
        this.reservation_request_date = reservation_request_date;
    }
    public String getReservation_type() {
        return reservation_type;
    }
    public void setReservation_type(String reservation_type) {
        this.reservation_type = reservation_type;
    }




}
