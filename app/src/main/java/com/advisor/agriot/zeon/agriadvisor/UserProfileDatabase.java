package com.advisor.agriot.zeon.agriadvisor;

/**
 * Created by Thumilan on 6/22/2017.
 */

public class UserProfileDatabase {

    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String streetAddress;
    public String streetAddress2;
    public String city;
    public String province;
    public double latitude;
    public double longtitude;
    public String agroZone;
    public String buyId;

    public UserProfileDatabase() {
    }

    public UserProfileDatabase(String firstName, String lastName, String phoneNumber, String streetAddress, String streetAddress2, String city, String province, double latitude, double longtitude, String agroZone, String buyId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.streetAddress = streetAddress;
        this.streetAddress2 = streetAddress2;
        this.city = city;
        this.province = province;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.agroZone = agroZone;
        this.buyId = buyId;
    }
}
