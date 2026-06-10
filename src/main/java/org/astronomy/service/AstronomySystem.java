package org.astronomy.service;

import org.astronomy.filehandler.ObservationLog;
import org.astronomy.database.*; // All DAO classes import karne ke liye
import org.astronomy.model.CelestialObject;
import org.astronomy.model.ObservationSession;
import org.astronomy.model.Observer;
import org.astronomy.model.Telescope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AstronomySystem {

    private ArrayList<Observer> observers = new ArrayList<>();
    private ArrayList<Telescope> telescopes = new ArrayList<>();
    private ArrayList<CelestialObject> celestialObjects = new ArrayList<>();
    private ArrayList<ObservationSession> sessions = new ArrayList<>();

    public AstronomySystem() {
        System.out.println("--- System Synchronization Sequence ---");

        try {
            System.out.println("Synchronizing runtime structures from MySQL instance...");

            ArrayList<Observer> dbObservers = ObserverDAO.loadAll();
            ArrayList<Telescope> dbTelescopes = TelescopeDAO.loadAll();
            ArrayList<CelestialObject> dbObjects = CelestialObjectDAO.loadAll();

            if (dbObservers != null) this.observers = dbObservers;
            if (dbTelescopes != null) this.telescopes = dbTelescopes;
            if (dbObjects != null) this.celestialObjects = dbObjects;
            ArrayList<ObservationSession> dbSessions = SessionDAO.loadAll();
            if (dbSessions != null) this.sessions = dbSessions;

            System.out.println("Database records mapping check: Success.");
        } catch (Exception e) {
            System.out.println("[WARNING] DB loading skipped or failed, fallback to empty list layout: " + e.getMessage());
        }

        System.out.println("Loading previous log trace files...");
        ArrayList<String> logs = ObservationLog.loadAllSessions();
        if (logs == null || logs.isEmpty()) {
            System.out.println("No previous local logs found.");
        } else {
            System.out.println("Loaded " + logs.size() + " previous session log(s).");
        }
        System.out.println("----------------------------------------");
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
        ObserverDAO.save(observer);
    }

    public void addTelescope(Telescope telescope) {
        telescopes.add(telescope);
        TelescopeDAO.save(telescope);
    }

    public void addCelestialObject(CelestialObject object) {
        celestialObjects.add(object);
        CelestialObjectDAO.save(object);
    }

    public ObservationSession scheduleSession(Observer observer, Telescope telescope, CelestialObject target, String date, int startHour, int durationMinutes, String notes, int sessionId) {
        ObservationSession session = new ObservationSession(sessionId, observer, telescope, target, date, startHour, durationMinutes, notes);
        session.conduct();

        ObservationLog.saveSession(session);
        SessionDAO.save(session);
        sessions.add(session);

        return session;
    }

    public ArrayList<CelestialObject> getCelestialObjects() { return celestialObjects; }
    public ArrayList<Observer> getObservers() { return observers; }
    public ArrayList<ObservationSession> getSessions() { return sessions; }
    public ArrayList<Telescope> getTelescopes() { return telescopes; }
}