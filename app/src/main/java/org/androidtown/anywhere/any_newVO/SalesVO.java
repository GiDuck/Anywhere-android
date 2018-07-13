package org.androidtown.anywhere.any_newVO;

/**
 * Created by gdtbg on 2017-07-18.
 */

import java.io.Serializable;
import java.util.Date;
public class SalesVO implements Serializable {


    private int store_num;
    private int reservation_num;
    private String sales_menu;
    private int sales_price;
    private Date sales_date;
    private int sales_count; //

    public int getStore_num() {
        return store_num;
    }

    public void setStore_num(int store_num) {
        this.store_num = store_num;
    }

    public int getReservation_num() {
        return reservation_num;
    }

    public void setReservation_num(int reservation_num) {
        this.reservation_num = reservation_num;
    }

    public String getSales_menu() {
        return sales_menu;
    }

    public void setSales_menu(String sales_menu) {
        this.sales_menu = sales_menu;
    }

    public int getSales_price() {
        return sales_price;
    }

    public void setSales_price(int sales_price) {
        this.sales_price = sales_price;
    }

    public Date getSales_date() {
        return sales_date;
    }

    public void setSales_date(Date sales_date) {
        this.sales_date = sales_date;
    }

    public int getSales_count() {
        return sales_count;
    }

    public void setSales_count(int sales_count) {
        this.sales_count = sales_count;
    }
}
