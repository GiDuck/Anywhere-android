package org.androidtown.anywhere.any_newVO;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gdtbg on 2017-08-02.
 */

public class CustomerReservationCheckVO implements Serializable {

    private ArrayList<ReservationListVO> reservationListVO;
    private ArrayList<StoreVO> storeVO;

    public ArrayList<ReservationListVO> getReservationListVO() {
        return reservationListVO;
    }

    public void setReservationListVO(ArrayList<ReservationListVO> reservationListVO) {
        this.reservationListVO = reservationListVO;
    }

    public ArrayList<StoreVO> getStoreVO() {
        return storeVO;
    }

    public void setStoreVO(ArrayList<StoreVO> storeVO) {
        this.storeVO = storeVO;
    }
}
