package backend.controller;

import backend.model.CelestialObject;
import backend.model.ObservationSession;
import backend.model.Observer;
import backend.model.Telescope;

import java.util.ArrayList;

public class AstronomySystem {

    private ArrayList<Observer> observers = new ArrayList<>();
    private ArrayList<Telescope> telescopes = new ArrayList<>();
    private ArrayList<CelestialObject> celestialObjects = new ArrayList<>();
    private ArrayList<ObservationSession> sessions = new ArrayList<>();

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void addTelescope(Telescope telescope){
        telescopes.add(telescope);
    }

    public void addCelestialObject(CelestialObject object){
        celestialObjects.add(object);
    }

    public ObservationSession scheduleSession(Observer observer, Telescope telescope, CelestialObject target, String date, int startHour, int durationMinutes, String notes, int sessionId
    ) {
        ObservationSession session = new ObservationSession(sessionId, observer, telescope, target, date, startHour, durationMinutes, notes);

        session.conduct();
        sessions.add(session);

        return session;
    }

    public ArrayList<CelestialObject> getCelestialObjects() {
        return celestialObjects;
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public ArrayList<ObservationSession> getSessions() {
        return sessions;
    }

    public ArrayList<Telescope> getTelescopes() {
        return telescopes;
    }
}
