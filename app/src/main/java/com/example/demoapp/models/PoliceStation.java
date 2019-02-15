package com.example.demoapp.models;

public class PoliceStation {

    public String name;
    public String address;
    public String area;
    public double lat,lng;
    public String mobile;


    public PoliceStation(String name, String address, String area, String mobile, double lat, double lng) {
        this.name = name;
        this.address = address;
        this.area = area;
        this.lat = lat;
        this.lng = lng;
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return  name;
    }
}
