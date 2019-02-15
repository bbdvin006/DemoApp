package com.example.demoapp.models;

public class CaseReport {

    public String officer;
    public String station;
    public String address;
    public String caseDetails;
    public String evidenceDetails;
    public String ArrestDone;
    public String otherInformation = null;
    public String date;
    public String time;
    public int noOfConvicts;
    private String caseId;

    public CaseReport() {
    }

    public CaseReport(String officer, String station, String address, String caseDetails, String evidenceDetails, String arrestDone, String otherInformation, String date, String time, int noOfConvicts) {
        this.officer = officer;
        this.station = station;
        this.address = address;
        this.caseDetails = caseDetails;
        this.evidenceDetails = evidenceDetails;
        ArrestDone = arrestDone;
        this.otherInformation = otherInformation;
        this.date = date;
        this.time = time;
        this.noOfConvicts = noOfConvicts;

    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
}
