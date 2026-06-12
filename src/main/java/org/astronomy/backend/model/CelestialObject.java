package org.astronomy.backend.model;

import org.astronomy.backend.exception.InvalidDataException;

public abstract class CelestialObject {
    private String name;
    private int lightYears;
    private double magnitude;

    public int getDeclination() {
        return declination;
    }

    public int getRightAscension() {
        return rightAscension;
    }

    private int rightAscension;
    private int declination;
    private String constellation;

    public CelestialObject(String name, int lightYears, double magnitude, int rightAscension, int declination, String constellation) throws InvalidDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Celestial Object name cannot be empty!");
        }
        if (lightYears <= 0) {
            throw new InvalidDataException("Light years must be a positive integer!");
        }
        this.name = name;
        this.lightYears = lightYears;
        this.magnitude = magnitude;
        this.rightAscension = rightAscension;
        this.declination = declination;
        this.constellation = constellation;
    }

    public abstract String getType();

    public abstract String getDescription();

    public String getConstellation(){
        return this.constellation;
    };

    public boolean isVisible(int hour) throws InvalidDataException {
        if (hour<0 || hour>23){
            throw new InvalidDataException("Hours must be between 0 and 23!");
        }
        return hour >= 20 || hour <= 5;
    }

    public String getName() {
        return name;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public int getLightYears() {
        return lightYears;
    }

    @Override
    public String toString() {
        return "CelestialObject{" +
                "constellation='" + constellation + '\'' +
                ", name='" + name + '\'' +
                ", lightYears=" + lightYears +
                ", magnitude=" + magnitude +
                ", rightAscension=" + rightAscension +
                ", declination=" + declination +
                '}';
    }
}
