package com.graph;

import android.app.Application;

public class MyApplication extends Application {
    private String uid;
    private String sensor;

    public String getuser() {
        return uid;
    }

    public void setuser(String uid) {
        this.uid = uid;
    }

    public String getsensor() {
        return sensor;
    }

    public void setsensor(String sensor) {
        this.sensor = sensor;
    }

}
