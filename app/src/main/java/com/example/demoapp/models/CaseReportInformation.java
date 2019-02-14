package com.example.demoapp.models;

public class CaseReportInformation {

    String officer;
    String station;
    String address;
    String caseDetails;
    String evidenceDetails;
    String ArrestDone;
    String OtherInformation = null;
    String date;
    String time;
    int noOfConvicts;
    private PoliceStation stationObj;

    public CaseReportInformation() {
    }

    public CaseReportInformation(String officer, String station, String address, String caseDetails, String evidenceDetails, String arrestDone, String otherInformation, String date, String time, int noOfConvicts,PoliceStation stationObj) {
        this.officer = officer;
        this.station = station;
        this.address = address;
        this.caseDetails = caseDetails;
        this.evidenceDetails = evidenceDetails;
        ArrestDone = arrestDone;
        OtherInformation = otherInformation;
        this.date = date;
        this.time = time;
        this.noOfConvicts = noOfConvicts;
        this.stationObj = stationObj;
    }
}
