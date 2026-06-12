package org.astronomy.backend.model;

import org.astronomy.backend.exception.InvalidDataException;

public class ObservationSession {
    private int sessionId;
    private Observer observer;
    private Telescope telescope;
    private CelestialObject target;
    private String date;
    private int startHour;
    private int durationMinutes;
    private String notes;
    private String status;
    private String failReason;

    public ObservationSession(int sessionId, Observer observer, Telescope telescope, CelestialObject target, String date, int startHour, int durationMinutes, String notes){
        this.sessionId = sessionId;
        this.observer = observer;
        this.telescope = telescope;
        this.target = target;
        this.date = date;
        this.startHour = startHour;
        this.durationMinutes = durationMinutes;
        this.notes = notes;
        this.status = "PENDING";
        this.failReason = "";
    }

    public void conduct() {
        try {
            if (!target.isVisible(startHour)) {
                status = "FAILED";
                failReason = "Target not visible at this hour";
            }
            else if (!telescope.canObserve(target)) {
                status = "FAILED";
                failReason = "Telescope aperture too weak for faint magnitude";
            }
            // SMART RULE 1: Duration check to prevent fatigue or daylight cross
            else if (durationMinutes > 480) {
                status = "FAILED";
                failReason = "Exceeds maximum session length limits (8 hours)";
            }
            // SMART RULE 2: High complexity deep-sky tracking checks
            else if (target.getType().equals("Galaxy") && !telescope.isMotorized() && observer.getExperienceLevel().equalsIgnoreCase("Beginner")) {
                status = "FAILED";
                failReason = "Beginners require a Motorized Telescope to track high-complexity Galaxy structures";
            }
            else {
                status = "SUCCESS";
                failReason = "";
            }
        } catch (InvalidDataException e) {
            status = "FAILED";
            failReason = e.getMessage();
        }
    }

    public String getStatus() {
        return status;
    }

    public String getFailReason() {
        return failReason;
    }

    public Observer getObserver() {
        return observer;
    }

    public CelestialObject getTarget() {
        return target;
    }

    public Telescope getTelescope() {
        return telescope;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getDate() { return date; }

    @Override
    public String toString() {
        return "--------Observation Session--------" +
                "\nDate: " + date +
                "\nSession Id: " + sessionId +
                "\nObserver: " + observer +
                "\nTelescope: " + telescope +
                "\nTarget: " + target +
                "\nStart Hour: " + startHour +
                "\nDuration Minutes: " + durationMinutes +
                "\nNotes: " + notes +
                "\nStatus: " + status +
                (status.equals("FAILED") ? "\nFail Reason: " + failReason : "");
    }

    public String getLog(){
        return getSessionId()+ " | " +getObserver().getObserverName()+ " | " +getTelescope().getModel()+ " | " +getTarget().getName()+ " | " +startHour+ " | " +durationMinutes+ " | " +notes+ " | " +status+ " | " +((status.equals("FAILED")) ? failReason : "");
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}