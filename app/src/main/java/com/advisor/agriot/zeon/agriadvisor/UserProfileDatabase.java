package com.advisor.agriot.zeon.agriadvisor;

/**
 * Created by Thumilan on 6/22/2017.
 */

public class UserProfileDatabase {

    public String firstname;
    public String lastname;
    public String phonenumber;
    public String streetaddress;
    public String optionalstreetaddress;
    public String city;
    public String province;

    public UserProfileDatabase() {
    }

    public UserProfileDatabase(String firstname, String lastname, String phonenumber, String streetaddress, String optionalstreetaddress, String city, String province) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.streetaddress = streetaddress;
        this.optionalstreetaddress = optionalstreetaddress;
        this.city = city;
        this.province = province;
    }
}
