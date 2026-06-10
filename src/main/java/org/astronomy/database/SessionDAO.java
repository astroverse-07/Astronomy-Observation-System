package org.astronomy.database;

import org.astronomy.model.CelestialObject;
import org.astronomy.model.ObservationSession;
import org.astronomy.model.Observer;
import org.astronomy.model.Telescope;

import java.sql.*;
import java.util.ArrayList;

public class SessionDAO {

    public static void save(ObservationSession s) {
        String sql = "INSERT INTO observation_sessions (session_id, observer_id, telescope_id, object_name, date, start_hour, duration_minutes, notes, status, fail_reason) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Explicit positional mappings matching schema order
            stmt.setInt(1, s.getSessionId());
            stmt.setInt(2, s.getObserver().getObserverID());
            stmt.setInt(3, s.getTelescope().getTelescopeID());
            stmt.setString(4, s.getTarget().getName());
            stmt.setString(5, s.getDate());

            // Using internal field reflections directly via getLog parsing safely
            stmt.setInt(6, getStartHourFromLog(s));
            stmt.setInt(7, getDurationFromLog(s));

            stmt.setString(8, getNotesFromLog(s));
            stmt.setString(9, s.getStatus());
            stmt.setString(10, s.getFailReason() == null ? "" : s.getFailReason());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[DB ERROR] Session transaction processing error: " + e.getMessage());
        }
    }

    private static int getStartHourFromLog(ObservationSession s) {
        try {
            String[] tokens = s.getLog().split("\\|");
            return Integer.parseInt(tokens[4].trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private static int getDurationFromLog(ObservationSession s) {
        try {
            String[] tokens = s.getLog().split("\\|");
            return Integer.parseInt(tokens[5].trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private static String getNotesFromLog(ObservationSession s) {
        try {
            String[] tokens = s.getLog().split("\\|");
            return tokens[6].trim();
        } catch (Exception e) {
            return "";
        }
    }

    public static ArrayList<ObservationSession> loadAll() {
        ArrayList<ObservationSession> list = new ArrayList<>();
        String sql = "SELECT * FROM observation_sessions";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int observerId = rs.getInt("observer_id");
                int telescopeId = rs.getInt("telescope_id");
                String objectName = rs.getString("object_name");

                Observer observer = ObserverDAO.loadById(observerId);
                Telescope telescope = TelescopeDAO.loadById(telescopeId);
                CelestialObject target = CelestialObjectDAO.loadByName(objectName);

                if (observer != null && telescope != null && target != null) {
                    ObservationSession session = new ObservationSession(
                            rs.getInt("session_id"),
                            observer,
                            telescope,
                            target,
                            rs.getString("date"),
                            rs.getInt("start_hour"),
                            rs.getInt("duration_minutes"),
                            rs.getString("notes")
                    );
                    session.setStatus(rs.getString("status"));
                    session.setFailReason(rs.getString("fail_reason"));
                    list.add(session);
                }
            }
        } catch (SQLException e) {
            System.out.println("[DB ERROR] Sessions loading issue: " + e.getMessage());
        }
        return list;
    }
}