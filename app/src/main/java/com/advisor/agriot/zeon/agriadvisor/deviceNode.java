package com.advisor.agriot.zeon.agriadvisor;

/**
 * Created by Haran on 30/07/2017.
 */

public class deviceNode {
    public DeviceData data;
    public String lastBroadcast;
    public String nutrients;
    public String maintenance;
    public String battery;
    public String notification;
    public String deviceName;

    public deviceNode(){

    }

    public deviceNode(DeviceData data, String lastBroadcast, String nutrients, String maintenance, String battery, String notification,String deviceName) {
        this.data = data;
        this.lastBroadcast = lastBroadcast;
        this.nutrients = nutrients;
        this.maintenance = maintenance;
        this.battery = battery;
        this.notification = notification;
        this.deviceName = deviceName;
    }
}
