package model;

public class Observer {
    private int observerID;
    private String observerName;
    private String experienceLevel;
    private String location;

    public Observer(int observerID, String observerName, String location, String experienceLevel) {
        this.observerID = observerID;
        this.observerName = observerName;
        this.location = location;
        this.experienceLevel = experienceLevel;
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
        return  "Observer ID: " + observerID +
                "\nObserver Name: " + observerName +
                "\nLocation: " + location +
                "\nExperience Level: " + experienceLevel;
    }
}
