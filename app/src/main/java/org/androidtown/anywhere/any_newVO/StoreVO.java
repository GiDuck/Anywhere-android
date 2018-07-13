package org.androidtown.anywhere.any_newVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gdtbg on 2017-07-18.
 */

public class StoreVO implements Serializable {

    private int store_num;
    private String store_nick;
    private String store_name;
    private String store_phone;
    private String store_addr;
    private String store_intro;
    private String store_spaceintro;
    private String store_attention;
    private Double store_latitude;
    private Double store_longitude;
    private int store_min;
    private int store_max;
    private String store_mainurl;
    private ArrayList<String> picture_url;
    private ArrayList<Integer> facility_num;
    private ArrayList<String> menu_name;
    private ArrayList<Integer> menu_price;
    private int store_starttime;
    private int store_endtime;



    public int getStore_num() {
        return store_num;
    }

    public void setStore_num(int store_num) {
        this.store_num = store_num;
    }

    public String getStore_nick() {
        return store_nick;
    }

    public void setStore_nick(String store_nick) {
        this.store_nick = store_nick;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_phone() {
        return store_phone;
    }

    public void setStore_phone(String store_phone) {
        this.store_phone = store_phone;
    }

    public String getStore_addr() {
        return store_addr;
    }

    public void setStore_addr(String store_addr) {
        this.store_addr = store_addr;
    }

    public String getStore_intro() {
        return store_intro;
    }

    public void setStore_intro(String store_intro) {
        this.store_intro = store_intro;
    }



    public String getStore_spaceintro() {
        return store_spaceintro;
    }

    public void setStore_spaceintro(String store_spaceintro) {
        this.store_spaceintro = store_spaceintro;
    }

    public String getStore_attention() {
        return store_attention;
    }

    public void setStore_attention(String store_attention) {
        this.store_attention = store_attention;
    }


    public Double getStore_latitude() {
        return store_latitude;
    }

    public void setStore_latitude(Double store_latitude) {
        this.store_latitude = store_latitude;
    }

    public Double getStore_longitude() {
        return store_longitude;
    }

    public void setStore_longitude(Double store_longitude) {
        this.store_longitude = store_longitude;
    }

    public int getStore_min() {
        return store_min;
    }

    public void setStore_min(int store_min) {
        this.store_min = store_min;
    }

    public int getStore_max() {
        return store_max;
    }

    public void setStore_max(int store_max) {
        this.store_max = store_max;
    }

    public String getStore_mainurl() {
        return store_mainurl;
    }

    public void setStore_mainurl(String store_mainurl) {
        this.store_mainurl = store_mainurl;
    }

    public List<String> getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(ArrayList<String> picture_url) {
        this.picture_url = picture_url;
    }

    public ArrayList<Integer> getFacility_num() {
        return facility_num;
    }

    public void setFacility_num(ArrayList<Integer> facility_num) {
        this.facility_num = facility_num;
    }

    public ArrayList<String> getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(ArrayList<String> menu_name) {
        this.menu_name = menu_name;
    }

    public ArrayList<Integer> getMenu_price() {
        return menu_price;
    }

    public void setMenu_price(ArrayList<Integer> menu_price) {
        this.menu_price = menu_price;
    }

    public int getStore_starttime() {
        return store_starttime;
    }

    public void setStore_starttime(int store_starttime) {
        this.store_starttime = store_starttime;
    }

    public int getStore_endtime() {
        return store_endtime;
    }

    public void setStore_endtime(int store_endtime) {
        this.store_endtime = store_endtime;
    }
}
