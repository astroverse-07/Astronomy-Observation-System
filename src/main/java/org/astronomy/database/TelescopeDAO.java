package org.astronomy.database;

import org.astronomy.exception.InvalidDataException;
import org.astronomy.model.Telescope;
import java.sql.*;
import java.util.ArrayList;

public class TelescopeDAO {

    public static void save(Telescope t) {
        String sql = "INSERT INTO telescopes (telescope_id, model, max_magnification, aperture_size, motorized) VALUES (?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE model=?, max_magnification=?, aperture_size=?, motorized=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, t.getTelescopeID());
            stmt.setString(2, t.getModel());
            stmt.setInt(3, t.getMaxMagnification());
            stmt.setDouble(4, t.getApertureSize());
            stmt.setBoolean(5, t.isMotorized());
            stmt.setString(6, t.getModel());
            stmt.setInt(7, t.getMaxMagnification());
            stmt.setDouble(8, t.getApertureSize());
            stmt.setBoolean(9, t.isMotorized());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[DB ERROR] Could not save Telescope: " + e.getMessage());
        }
    }

    public static ArrayList<Telescope> loadAll() {
        ArrayList<Telescope> list = new ArrayList<>();
        String sql = "SELECT * FROM telescopes";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Telescope(
                        rs.getInt("telescope_id"),
                        rs.getString("model"),
                        rs.getInt("max_magnification"),
                        rs.getDouble("aperture_size"),
                        rs.getBoolean("motorized")
                ));
            }
        } catch (SQLException | InvalidDataException e) {
            System.out.println("[DB ERROR] Telescopes loading issue: " + e.getMessage());
        }
        return list;
    }

    public static Telescope loadById(int id) {
        String sql = "SELECT * FROM telescopes WHERE telescope_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Telescope(
                        rs.getInt("telescope_id"),
                        rs.getString("model"),
                        rs.getInt("max_magnification"),
                        rs.getDouble("aperture_size"),
                        rs.getBoolean("motorized")
                );
            }
        } catch (SQLException | InvalidDataException e) {
            System.out.println("[DB ERROR] Telescope loadById: " + e.getMessage());
        }
        return null;
    }
}