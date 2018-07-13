package org.androidtown.anywhere.any_07_customer_reservation;

/**
 * Created by user on 2017-07-04.
 */

public class CustomerReservationListData {

    //리사이클러뷰 데이터 바인딩에 필요한 데이터
    private String storeImage;
    private String storeName;
    private String storeAddress;
    private String reservationDate;
    private String reservationState;
    private String reservationCancel;
    private String reservation_num;
    private String store_num;

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationState() {
        return reservationState;
    }

    public void setReservationState(String reservationState) {
        this.reservationState = reservationState;
    }

    public String getReservationCancel() {
        return reservationCancel;
    }

    public void setReservationCancel(String reservationCancel) {
        this.reservationCancel = reservationCancel;
    }

    public String getReservation_num() {
        return reservation_num;
    }

    public void setReservation_num(String reservation_num) {
        this.reservation_num = reservation_num;
    }

    public String getStore_num() {
        return store_num;
    }

    public void setStore_num(String store_num) {
        this.store_num = store_num;
    }
}
