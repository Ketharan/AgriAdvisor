package com.advisor.agriot.zeon.agriadvisor;

/**
 * Created by Haran on 23/07/2017.
 */

public class notification {
    private String notification;
    private int image;
    private int indicator;



    public notification(String notification, int image, int indicator) {
        this.notification = notification;
        this.image = image;
        this.indicator = indicator;

    }

    public int getIndicator() {
        return indicator;
    }

    public void setIndicator(int indicator) {
        this.indicator = indicator;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public notification(String notification) {
        this.notification = notification;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
