package com.example.demoapp.models;

public class Convict {

    public String caseid;
    public String name;
    public String address;
    public String details;
    public String gender;
    public String state;
    public String age = "20";

    public Convict(String caseid, String name, String address, String details, String gender, String age, String state) {
        this.caseid = caseid;
        this.name = name;
        this.address = address;
        this.details = details;
        this.gender = gender;
        this.state = state;
        this.age = age;
    }

    public Convict() {
    }
}
