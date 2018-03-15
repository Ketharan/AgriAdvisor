package com.advisor.agriot.zeon.agriadvisor;

/**
 * Created by Haran on 27/08/2017.
 */

public class pestImage {
    private String name;
    private byte[] image;

    public pestImage(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
