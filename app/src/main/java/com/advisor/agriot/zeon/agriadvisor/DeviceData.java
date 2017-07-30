package com.advisor.agriot.zeon.agriadvisor;

import android.support.constraint.ConstraintLayout;

/**
 * Created by Thumilan on 7/21/2017.
 */

public class DeviceData {
    public String temperature;
    public String humidity;
    public String light;
    public String moisture;
    public String ec;

    public DeviceData() {
    }

    public DeviceData(String temperature, String humidity, String light, String moisture,String ec) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.light = light;
        this.moisture = moisture;
        this.ec = ec;


    }
}
