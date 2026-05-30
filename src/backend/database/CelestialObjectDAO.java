package backend.database;

import backend.exception.InvalidDataException;
import backend.model.*;
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

            // Default column fields values null mappings setup
            stmt.setNull(6, Types.VARCHAR);
            stmt.setNull(7, Types.BOOLEAN);
            stmt.setNull(8, Types.INTEGER);
            stmt.setNull(9, Types.BOOLEAN);
            stmt.setNull(10, Types.VARCHAR);
            stmt.setNull(11, Types.INTEGER);

            if (obj instanceof Star) {
                // Cast tracking fallback variables details mapping
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
                String type = rs.getString("object_type");
                String name = rs.getString("name");
                int ly = rs.getInt("light_years");
                double mag = rs.getDouble("magnitude");
                String con = rs.getString("constellation");

                if ("Star".equalsIgnoreCase(type)) {
                    list.add(new Star(name, ly, mag, 0, 0, con, false, "G"));
                } else if ("Planet".equalsIgnoreCase(type)) {
                    list.add(new Planet(name, ly, mag, 0, 0, con, 0, false));
                } else if ("Galaxy".equalsIgnoreCase(type)) {
                    list.add(new Galaxy(name, ly, mag, 0, 0, con, "Spiral", 100));
                }
            }
        } catch (SQLException | InvalidDataException e) {
            System.out.println("[DB ERROR] Objects loading issue: " + e.getMessage());
        }
        return list;
    }
}
