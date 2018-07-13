package org.androidtown.anywhere.any_VO.before_VO;

/**
 * Created by user on 2017-07-17.
 */
public class SearchResultVO {

    private String photoUri;
    private String storeName;
    private String storeAddress;
    private int minimumPeople;
    private float starRank;

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
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

    public int getMinimumPeople() {
        return minimumPeople;
    }

    public void setMinimumPeople(int minimumPeople) {
        this.minimumPeople = minimumPeople;
    }

    public float getStarRank() {
        return starRank;
    }

    public void setStarRank(float starRank) {
        this.starRank = starRank;
    }
}
