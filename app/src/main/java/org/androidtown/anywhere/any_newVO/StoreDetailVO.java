package org.androidtown.anywhere.any_newVO;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gdtbg on 2017-07-25.
 */

public class StoreDetailVO implements Serializable {

    private StoreVO storeVO;
    private ArrayList<PictureVO> pictureVO;
    private ArrayList<StoreReplyVO> replyVO;
    private ArrayList<IconVO> iconVO;
    private ArrayList<MenuVO> menuVO;
    private String free_name;
    private int free_price;


    public StoreVO getStoreVO() {
        return storeVO;
    }
    public void setStoreVO(StoreVO storeVO) {
        this.storeVO = storeVO;
    }
    public ArrayList<PictureVO> getPictureVO() {
        return pictureVO;
    }
    public void setPictureVO(ArrayList<PictureVO> pictureVO) {
        this.pictureVO = pictureVO;
    }
    public ArrayList<StoreReplyVO> getReplyVO() {
        return replyVO;
    }
    public void setReplyVO(ArrayList<StoreReplyVO> replyVO) {
        this.replyVO = replyVO;
    }
    public ArrayList<IconVO> getIconVO() {
        return iconVO;
    }
    public void setIconVO(ArrayList<IconVO> iconVO) {
        this.iconVO = iconVO;
    }
    private LikeVO likeVO;


    public ArrayList<MenuVO> getMenuVO() {
        return menuVO;
    }

    public void setMenuVO(ArrayList<MenuVO> menuVO) {
        this.menuVO = menuVO;
    }

    public String getFree_name() {
        return free_name;
    }

    public void setFree_name(String free_name) {
        this.free_name = free_name;
    }

    public int getFree_price() {
        return free_price;
    }

    public void setFree_price(int free_price) {
        this.free_price = free_price;
    }

    public LikeVO getLikeVO() {
        return likeVO;
    }

    public void setLikeVO(LikeVO likeVO) {
        this.likeVO = likeVO;
    }
}
