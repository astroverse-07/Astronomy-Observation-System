package org.astronomy.backend.database;

import org.astronomy.backend.exception.InvalidDataException;
import org.astronomy.backend.model.Observer;
import java.sql.*;
import java.util.ArrayList;

public class ObserverDAO {

    public static void save(Observer o) {
        String sql = "INSERT INTO observers (observer_id, observer_name, experience_level, location) VALUES (?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE observer_name=?, experience_level=?, location=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, o.getObserverID());
            stmt.setString(2, o.getObserverName());
            stmt.setString(3, o.getExperienceLevel());
            stmt.setString(4, o.getLocation());
            stmt.setString(5, o.getObserverName());
            stmt.setString(6, o.getExperienceLevel());
            stmt.setString(7, o.getLocation());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[DB ERROR] Could not save Observer: " + e.getMessage());
        }
    }

    public static ArrayList<Observer> loadAll() {
        ArrayList<Observer> list = new ArrayList<>();
        String sql = "SELECT * FROM observers";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Observer(
                        rs.getInt("observer_id"),
                        rs.getString("observer_name"),
                        capitalizeFirst(rs.getString("experience_level")),
                        rs.getString("location")
                ));
            }
        } catch (SQLException | InvalidDataException e) {
            System.out.println("[DB ERROR] Observers loading issue: " + e.getMessage());
        }
        return list;
    }

    public static Observer loadById(int id) {
        String sql = "SELECT * FROM observers WHERE observer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Observer(
                        rs.getInt("observer_id"),
                        rs.getString("observer_name"),
                        capitalizeFirst(rs.getString("experience_level")),
                        rs.getString("location")
                );
            }
        } catch (SQLException | InvalidDataException e) {
            System.out.println("[DB ERROR] Observer loadById: " + e.getMessage());
        }
        return null;
    }

    private static String capitalizeFirst(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}