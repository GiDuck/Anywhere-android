package org.androidtown.anywhere.any_VO.before_VO;

import java.util.Date;

/**
 * Created by user on 2017-07-17.
 */

public class ReservationInfoVO {

    private int reservationNumber;
    private int storeNumber;
    private Date salePlan;
    private int startTime;
    private int endTime;
    private String customerNickname;
    private String cusetomerPhoneNumber;
    private int reservedPeopleNumber;
    private String requestment;
    private int reserationStatement;
    private Date storeReservationUpdateDate;
    private Date reservedDate;

    public int getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public int getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(int storeNumber) {
        this.storeNumber = storeNumber;
    }

    public Date getSalePlan() {
        return salePlan;
    }

    public void setSalePlan(Date salePlan) {
        this.salePlan = salePlan;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getCustomerNickname() {
        return customerNickname;
    }

    public void setCustomerNickname(String customerNickname) {
        this.customerNickname = customerNickname;
    }

    public String getCusetomerPhoneNumber() {
        return cusetomerPhoneNumber;
    }

    public void setCusetomerPhoneNumber(String cusetomerPhoneNumber) {
        this.cusetomerPhoneNumber = cusetomerPhoneNumber;
    }

    public int getReservedPeopleNumber() {
        return reservedPeopleNumber;
    }

    public void setReservedPeopleNumber(int reservedPeopleNumber) {
        this.reservedPeopleNumber = reservedPeopleNumber;
    }

    public String getRequestment() {
        return requestment;
    }

    public void setRequestment(String requestment) {
        this.requestment = requestment;
    }

    public int getReserationStatement() {
        return reserationStatement;
    }

    public void setReserationStatement(int reserationStatement) {
        this.reserationStatement = reserationStatement;
    }

    public Date getStoreReservationUpdateDate() {
        return storeReservationUpdateDate;
    }

    public void setStoreReservationUpdateDate(Date storeReservationUpdateDate) {
        this.storeReservationUpdateDate = storeReservationUpdateDate;
    }

    public Date getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(Date reservedDate) {
        this.reservedDate = reservedDate;
    }

}
