package org.astronomy.model;

import org.astronomy.exception.InvalidDataException;

public class Observer {
    private int observerID;
    private String observerName;
    private String experienceLevel;
    private String location;

    public Observer(int observerID, String observerName, String experienceLevel, String location) throws InvalidDataException {
        if (observerName == null || observerName.trim().isEmpty()) {
            throw new InvalidDataException("Observer name cannot be empty!");
        }
        String exp = experienceLevel.trim();
        if (!exp.equalsIgnoreCase("Beginner") &&
                !exp.equalsIgnoreCase("Intermediate") &&
                !exp.equalsIgnoreCase("Expert")) {
            throw new InvalidDataException("Experience Level must be 'Beginner', 'Intermediate', or 'Expert'");
        }
        this.observerID = observerID;
        this.observerName = observerName;
        this.experienceLevel = exp.substring(0, 1).toUpperCase() + exp.substring(1).toLowerCase();
        this.location = location;
    }

    public String getObserverName() {
        return observerName;
    }

    public void setObserverName(String observerName) {
        this.observerName = observerName;
    }

    public int getObserverID() {
        return observerID;
    }

    public void setObserverID(int observerID) {
        this.observerID = observerID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    @Override
    public String toString() {
        return observerName + " (" + experienceLevel + ") - " + location;
    }
}
