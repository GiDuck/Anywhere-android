package org.androidtown.anywhere.any_newVO;

import java.io.Serializable;

/**
 * Created by gdtbg on 2017-07-18.
 */

public class LikeVO implements Serializable {


    private int store_num;
    private String like_nick;
    private int like_count;

    public int getStore_num() {
        return store_num;
    }

    public void setStore_num(int store_num) {
        this.store_num = store_num;
    }

    public String getLike_nick() {
        return like_nick;
    }

    public void setLike_nick(String like_nick) {
        this.like_nick = like_nick;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }
}
