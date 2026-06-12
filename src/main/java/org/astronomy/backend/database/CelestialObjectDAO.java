package org.astronomy.backend.database;

import org.astronomy.backend.exception.InvalidDataException;
import org.astronomy.backend.model.*;
import java.sql.*;
import java.util.ArrayList;

public class CelestialObjectDAO {

    public static void save(CelestialObject obj) {
        String sql = "INSERT INTO celestial_objects (name, light_years, magnitude, right_ascension, declination, constellation, object_type, "
                + "spectral_class, is_nebula, number_of_moons, has_rings, galaxy_type, estimated_stars) VALUES (?, ?, ?, 0, 0, ?, ?, ?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE light_years=?, magnitude=?, constellation=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, obj.getName());
            stmt.setInt(2, obj.getLightYears());
            stmt.setDouble(3, obj.getMagnitude());
            stmt.setString(4, obj.getConstellation());
            stmt.setString(5, obj.getType());

            stmt.setNull(6, Types.VARCHAR);
            stmt.setNull(7, Types.BOOLEAN);
            stmt.setNull(8, Types.INTEGER);
            stmt.setNull(9, Types.BOOLEAN);
            stmt.setNull(10, Types.VARCHAR);
            stmt.setNull(11, Types.INTEGER);

            if (obj instanceof Star) {
                stmt.setString(6, "G");
                stmt.setBoolean(7, false);
            } else if (obj instanceof Planet) {
                stmt.setInt(8, 0);
                stmt.setBoolean(9, false);
            } else if (obj instanceof Galaxy) {
                stmt.setString(10, "Spiral");
                stmt.setInt(11, 100);
            }

            stmt.setInt(12, obj.getLightYears());
            stmt.setDouble(13, obj.getMagnitude());
            stmt.setString(14, obj.getConstellation());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[DB ERROR] Could not save Celestial Object: " + e.getMessage());
        }
    }

    public static ArrayList<CelestialObject> loadAll() {
        ArrayList<CelestialObject> list = new ArrayList<>();
        String sql = "SELECT * FROM celestial_objects";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CelestialObject obj = mapRow(rs);
                if (obj != null) list.add(obj);
            }
        } catch (SQLException e) {
            System.out.println("[DB ERROR] Objects loading issue: " + e.getMessage());
        }
        return list;
    }

    public static CelestialObject loadByName(String name) {
        String sql = "SELECT * FROM celestial_objects WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.out.println("[DB ERROR] CelestialObject loadByName: " + e.getMessage());
        }
        return null;
    }

    private static CelestialObject mapRow(ResultSet rs) {
        try {
            String type = rs.getString("object_type");
            String name = rs.getString("name");
            int ly = rs.getInt("light_years");
            double mag = rs.getDouble("magnitude");
            int ra = rs.getInt("right_ascension");
            int dec = rs.getInt("declination");
            String con = rs.getString("constellation");

            if ("Star".equalsIgnoreCase(type)) {
                String spectral = rs.getString("spectral_class");
                boolean isNebula = rs.getBoolean("is_nebula");
                return new Star(name, ly, mag, ra, dec, con, isNebula, spectral != null ? spectral : "G");
            } else if ("Planet".equalsIgnoreCase(type)) {
                int moons = rs.getInt("number_of_moons");
                boolean hasRings = rs.getBoolean("has_rings");
                return new Planet(name, ly, mag, ra, dec, con, moons, hasRings);
            } else if ("Galaxy".equalsIgnoreCase(type)) {
                String galaxyType = rs.getString("galaxy_type");
                int stars = rs.getInt("estimated_stars");
                return new Galaxy(name, ly, mag, ra, dec, con, galaxyType != null ? galaxyType : "Spiral", stars);
            }
        } catch (SQLException | InvalidDataException e) {
            System.out.println("[DB ERROR] Row mapping issue: " + e.getMessage());
        }
        return null;
    }
}