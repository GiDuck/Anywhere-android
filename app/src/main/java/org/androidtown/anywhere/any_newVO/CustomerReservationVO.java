package org.androidtown.anywhere.any_newVO;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gdtbg on 2017-07-25.
 */

public class CustomerReservationVO implements Serializable {

    private ReservationListVO reservationListVO;
    private ArrayList<SalesVO> salesVO;

    public ReservationListVO getReservationListVO() {
        return reservationListVO;
    }
    public void setReservationListVO(ReservationListVO reservationListVO) {
        this.reservationListVO = reservationListVO;
    }
    public ArrayList<SalesVO> getSalesVO() {
        return salesVO;
    }
    public void setSalesVO(ArrayList<SalesVO> salesVO) {
        this.salesVO = salesVO;
    }


}
