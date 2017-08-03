package com.advisor.agriot.zeon.agriadvisor;

/**
 * Created by Haran on 02/08/2017.
 */

public class storeStructure {
    private String crop;
    private String shop;
    private String describtion;
    private Integer rating;
    private Integer image;

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public storeStructure(String crop, String shop, String describtion, Integer rating, Integer image) {

        this.crop = crop;
        this.shop = shop;
        this.describtion = describtion;
        this.rating = rating;
        this.image = image;

    }


    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }




}
