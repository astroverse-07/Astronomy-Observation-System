package backend.database;

import backend.model.ObservationSession;
import java.sql.*;

public class SessionDAO {

    public static void save(ObservationSession s) {
        String sql = "INSERT INTO observation_sessions (session_id, observer_id, telescope_id, object_name, date, start_hour, duration_minutes, notes, status, fail_reason) VALUES (?, ?, ?, ?, ?, 0, 0, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, s.getSessionId());
            stmt.setInt(2, s.getObserver().getObserverID());
            stmt.setInt(3, s.getTelescope().getTelescopeID());
            stmt.setString(4, s.getTarget().getName());
            stmt.setString(5, s.getStatus());
            stmt.setString(6, "");
            stmt.setString(7, s.getStatus());
            stmt.setString(8, s.getFailReason() == null ? "" : s.getFailReason());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[DB ERROR] Session transaction processing error: " + e.getMessage());
        }
    }
}
