package com.advisor.agriot.zeon.agriadvisor;

/**
 * Created by Thumilan on 7/21/2017.
 */

public class DeviceData {
    public String Temperature;
    public String Humidity;
    public String Light;
    public String Moisture;
    public DeviceData() {
    }

    public DeviceData(String temperature, String humidity, String light, String moisture) {
        Temperature = temperature;
        Humidity = humidity;
        Light = light;
        Moisture = moisture;
    }
}
