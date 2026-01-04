package com.skywatch.aircraft.model;

import com.skywatch.aircraft.enums.AircraftStatus;

import java.util.Objects;

public class AircraftData {
    private String aircraftId;
    private double altitude;
    private double speed;
    private double fuelLevel;
    private long timestamp;
    private AircraftStatus status;

    public AircraftData() {
    }

    public AircraftData(String aircraftId, double altitude, double speed, double fuelLevel, long timestamp, AircraftStatus status) {
        this.aircraftId = aircraftId;
        this.altitude = altitude;
        this.speed = speed;
        this.fuelLevel = fuelLevel;
        this.timestamp = timestamp;
        this.status = status;
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

    public AircraftStatus getStatus() {
        return status;
    }

    public void setStatus(AircraftStatus status) {
        this.status = status;
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
