package org.androidtown.anywhere.any_newVO;

import java.util.ArrayList;

/**
 * Created by gdtbg on 2017-07-26.
 */

public class LikeListVO {

    private ArrayList<StoreVO> storeVO;
    private ArrayList<LikeVO> likeVO;

    public ArrayList<StoreVO> getStoreVO() {
        return storeVO;
    }
    public void setStoreVO(ArrayList<StoreVO> storeVO) {
        this.storeVO = storeVO;
    }
    public ArrayList<LikeVO> getLikeVO() {
        return likeVO;
    }
    public void setLikeVO(ArrayList<LikeVO> likeVO) {
        this.likeVO = likeVO;
    }
}
