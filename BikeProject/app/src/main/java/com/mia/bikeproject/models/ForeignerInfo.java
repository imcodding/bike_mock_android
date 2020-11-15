package com.mia.bikeproject.models;

import com.google.gson.JsonObject;

public class ForeignerInfo {
    private String rentDate;
    private String rentId;
    private String rentNm;
    private int rentCnt; // 대여건수
    private int rtnCnt; // 반납건수

    public ForeignerInfo(JsonObject obj) {
        this.rentDate = obj.get("RENT_DT").getAsString();
        this.rentId = obj.get("RENT_ID").getAsString();
        this.rentNm = obj.get("RENT_NM").getAsString();
        this.rentCnt = obj.get("RENT_CNT").getAsInt();
        this.rtnCnt = obj.get("RTN_CNT").getAsInt();
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public String getRentId() {
        return rentId;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public String getRentNm() {
        return rentNm;
    }

    public void setRentNm(String rentNm) {
        this.rentNm = rentNm;
    }

    public int getRentCnt() {
        return rentCnt;
    }

    public void setRentCnt(int rentCnt) {
        this.rentCnt = rentCnt;
    }

    public int getRtnCnt() {
        return rtnCnt;
    }

    public void setRtnCnt(int rtnCnt) {
        this.rtnCnt = rtnCnt;
    }
}
