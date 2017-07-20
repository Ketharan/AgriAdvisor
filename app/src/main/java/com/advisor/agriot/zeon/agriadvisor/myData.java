package com.advisor.agriot.zeon.agriadvisor;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Haran on 20/07/2017.
 */

public class myData {



    static String[] nameArray = {"Temperature", "Humidity", "Light", "Moisture"};
    static String[] versionArray = {"1.5", "1.6", "2.0-2.1", "2.2-2.2.3"};

    static Integer[] drawableArray = {R.drawable.temperature, R.drawable.humidity, R.drawable.light,
            R.drawable.moisture};

    static Integer[] id_ = {0, 1, 2, 3};

    public void setVersionArray(String[] versionArray) {
        this.versionArray = versionArray;
    }
}