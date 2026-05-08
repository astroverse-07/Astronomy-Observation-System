package controller;

import model.CelestialObject;
import model.ObservationSession;
import model.Observer;
import model.Telescope;

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

    public void scheduleSession(Observer observer, Telescope telescope, CelestialObject target, String date, int startHour, int durationMinutes, String notes, int sessionId){
        ObservationSession session = new ObservationSession(durationMinutes, date, notes, observer, sessionId, startHour, target, telescope);
        session.conduct();
        sessions.add(session);
    }
}
