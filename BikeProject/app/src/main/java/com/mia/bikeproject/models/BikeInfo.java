package com.mia.bikeproject.models;

import com.google.gson.JsonObject;

import java.io.Serializable;

public class BikeInfo implements Serializable {
    private int rackTotCnt;
    private String stationName;
    private int parkingBikeTotCnt;
    private int shared;
    private double lat;
    private double lnt;
    private String stationId;
    private boolean checkForeigner;

    public BikeInfo(int rackTotCnt, String stationName, int parkingBikeTotCnt, int shared, double lat, double lnt, String stationId) {
        this.rackTotCnt = rackTotCnt;
        this.stationName = stationName;
        this.parkingBikeTotCnt = parkingBikeTotCnt;
        this.shared = shared;
        this.lat = lat;
        this.lnt = lnt;
        this.stationId = stationId;
    }

    public BikeInfo(JsonObject obj) {
        this.rackTotCnt = obj.get("rackTotCnt").getAsInt();
        this.stationName = obj.get("stationName").getAsString();
        this.parkingBikeTotCnt = obj.get("parkingBikeTotCnt").getAsInt();
        this.shared = obj.get("shared").getAsInt();
        this.lat = obj.get("stationLatitude").getAsDouble();
        this.lnt = obj.get("stationLongitude").getAsDouble();
        this.stationId = obj.get("stationId").getAsString();
    }

    public int getRackTotCnt() {
        return rackTotCnt;
    }

    public void setRackTotCnt(int rackTotCnt) {
        this.rackTotCnt = rackTotCnt;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getParkingBikeTotCnt() {
        return parkingBikeTotCnt;
    }

    public void setParkingBikeTotCnt(int parkingBikeTotCnt) {
        this.parkingBikeTotCnt = parkingBikeTotCnt;
    }

    public int getShared() {
        return shared;
    }

    public void setShared(int shared) {
        this.shared = shared;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLnt() {
        return lnt;
    }

    public void setLnt(double lnt) {
        this.lnt = lnt;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
}
