package org.androidtown.anywhere.any_newVO;

import java.util.ArrayList;

/**
 * Created by gdtbg on 2017-07-28.
 */

public class StoreListVO {

    private ArrayList<StoreVO> storeVO;
    private ArrayList<MenuVO> menuVO;
    private ArrayList<StoreReplyVO> storeReplyVO;
    private ArrayList<LikeVO> likeVO;
    private int like_count;

    public ArrayList<StoreVO> getStoreVO() {
        return storeVO;
    }

    public void setStoreVO(ArrayList<StoreVO> storeVO) {
        this.storeVO = storeVO;
    }

    public ArrayList<MenuVO> getMenuVO() {
        return menuVO;
    }

    public void setMenuVO(ArrayList<MenuVO> menuVO) {
        this.menuVO = menuVO;
    }

    public ArrayList<StoreReplyVO> getStoreReplyVO() {
        return storeReplyVO;
    }

    public void setStoreReplyVO(ArrayList<StoreReplyVO> storeReplyVO) {
        this.storeReplyVO = storeReplyVO;
    }

    public ArrayList<LikeVO> getLikeVO() {
        return likeVO;
    }

    public void setLikeVO(ArrayList<LikeVO> likeVO) {
        this.likeVO = likeVO;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }
}
