package model;

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

    public ObservationSession(int durationMinutes, String date, String notes, Observer observer, int sessionId, int startHour, CelestialObject target, Telescope telescope) {
        this.durationMinutes = durationMinutes;
        this.date = date;
        this.notes = notes;
        this.observer = observer;
        this.sessionId = sessionId;
        this.startHour = startHour;
        this.target = target;
        this.telescope = telescope;
    }

    public void conduct() {
        if (!target.isVisible(startHour)){
            status = "FAILED";
            failReason = "Not visible at this hour";
        }
        else if (!telescope.canObserve(target)){
            status = "FAILED";
            failReason = "Telescope too weak";
        }
        else {
            status = "SUCCESS";
            failReason = null;
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
                ((status.equals("FAILED")) ? "\nFail Reason: " + failReason : "");
    }

    public String getLog(){
        return getSessionId()+ " | " +getObserver().getObserverName()+ " | " +getTelescope().getModel()+ " | " +getTarget().getName()+ " | " +startHour+ " | " +durationMinutes+ " | " +notes+ " | " +status+ " | " +((status.equals("FAILED")) ? failReason : "");
    }
}
