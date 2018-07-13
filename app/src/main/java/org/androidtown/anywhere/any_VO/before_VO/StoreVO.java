package org.androidtown.anywhere.any_VO.before_VO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2017-07-17.
 */

public class StoreVO implements Serializable {

    private String storeName;
    private String storeAddress;
    private String storeNumber;
    private String storeIntroduce;
    private String storeNoticeIntroduce;
    private HashMap<String, String> storeFacility;
    private String storeFacilityIntroduce;
    private HashMap<String, Integer> coffeeMenu;
    private int onePersonPrice;
    private String representImage;
    private ArrayList<String> pageImages;
    private ArrayList<String> pageInsideImages;


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

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public String getStoreIntroduce() {
        return storeIntroduce;
    }

    public void setStoreIntroduce(String storeIntroduce) {
        this.storeIntroduce = storeIntroduce;
    }

    public String getStoreNoticeIntroduce() {
        return storeNoticeIntroduce;
    }

    public void setStoreNoticeIntroduce(String storeNoticeIntroduce) {
        this.storeNoticeIntroduce = storeNoticeIntroduce;
    }

    public HashMap<String, String> getStoreFacility() {
        return storeFacility;
    }

    public void setStoreFacility(HashMap<String, String> storeFacility) {
        this.storeFacility = storeFacility;
    }

    public String getStoreFacilityIntroduce() {
        return storeFacilityIntroduce;
    }

    public void setStoreFacilityIntroduce(String storeFacilityIntroduce) {
        this.storeFacilityIntroduce = storeFacilityIntroduce;
    }

    public HashMap<String, Integer> getCoffeeMenu() {
        return coffeeMenu;
    }

    public void setCoffeeMenu(HashMap<String, Integer> coffeeMenu) {
        this.coffeeMenu = coffeeMenu;
    }

    public int getOnePersonPrice() {
        return onePersonPrice;
    }

    public void setOnePersonPrice(int onePersonPrice) {
        this.onePersonPrice = onePersonPrice;
    }

    public String getRepresentImage() {
        return representImage;
    }

    public void setRepresentImage(String representImage) {
        this.representImage = representImage;
    }

    public ArrayList<String> getPageImages() {
        return pageImages;
    }

    public void setPageImages(ArrayList<String> pageImages) {
        this.pageImages = pageImages;
    }

    public ArrayList<String> getPageInsideImages() {
        return pageInsideImages;
    }

    public void setPageInsideImages(ArrayList<String> pageInsideImages) {
        this.pageInsideImages = pageInsideImages;
    }

}
