package com.example.track_hailing;

import android.app.Application;

/**
 * Created by 叶泽锐 on 2018/1/2.
 */

public class Myapplication extends Application {

    //声明一个变量
    public String City = "浙江";

    public String Id = "1512190127";

    public String Origin = "";

    public String Destination = "";

    public boolean Choose = true;

    public Double Lat = 0.00;
    public Double Lon = 0.00;

    public void setCity(String city) {
        City = city;
    }

    public String getCity() {
        return City;
    }

    public String getDestination() {
        return Destination;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public void setChoose(boolean choose) {
        Choose = choose;
    }

    public boolean getChoose() {
        return Choose;
    }

    public Double getLat() {
        return Lat;
    }

    public Double getLon() {
        return Lon;
    }

    public void setLon(Double lon) {
        Lon = lon;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public String getId() {
        return Id;
    }
}
