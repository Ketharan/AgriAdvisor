package com.advisor.agriot.zeon.agriadvisor;

/**
 * Created by Haran on 03/08/2017.
 */

public class currentCrop {
    private String name;
    private int group;
    private String startDate;
    private String stage;
    private String notificaton;
    private int image;

    public currentCrop() {
    }

    public currentCrop(String name, int group, String startDate, String stage, String notificaton,int image) {

        this.name = name;
        this.group = group;
        this.startDate = startDate;
        this.stage = stage;
        this.notificaton = notificaton;
        this.image = image;
    }

    public currentCrop(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public String getName() {

        return name;
    }

    public int getGroup() {
        return group;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStage() {
        return stage;
    }

    public String getNotificaton() {
        return notificaton;
    }
}
