package com.skywatch.aircraft.model;


import java.util.Objects;

public class AircraftData {
    private String aircraftId;
    private double altitude;
    private double speed;
    private double fuelLevel;
    private long timestamp;

    public AircraftData() {
    }

    public AircraftData(String aircraftId, double altitude, double speed, double fuelLevel, long timestamp) {
        this.aircraftId = aircraftId;
        this.altitude = altitude;
        this.speed = speed;
        this.fuelLevel = fuelLevel;
        this.timestamp = timestamp;
    }

    public String getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(String aircraftId) {
        this.aircraftId = aircraftId;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(double fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AircraftData)) return false;
        AircraftData that = (AircraftData) o;
        return timestamp == that.timestamp &&
                Objects.equals(aircraftId, that.aircraftId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aircraftId, timestamp);
    }


    @Override
    public String toString() {
        return "AircraftData{" +
                "aircraftId='" + aircraftId + '\'' +
                ", altitude=" + altitude +
                ", speed=" + speed +
                ", fuelLevel=" + fuelLevel +
                ", timestamp=" + timestamp +
                '}';
    }
}
