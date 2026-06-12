package org.astronomy.backend.model;

import org.astronomy.backend.exception.InvalidDataException;

public class Planet extends CelestialObject {
    private int numberOfMoons;
    private boolean hasRings;

    public Planet(String name, int lightYears, double magnitude, int rightAscension, int declination, String constellation, int numberOfMoons, boolean hasRings) throws InvalidDataException {
        super(name, lightYears, magnitude, rightAscension, declination, constellation);
        this.numberOfMoons = numberOfMoons;
        this.hasRings = hasRings;
    }

    // ==================== GETTER METHODS ====================
    public int getNumberOfMoons() {
        return numberOfMoons;
    }

    public boolean hasRings() {
        return hasRings;
    }

    // ==================== SETTER METHODS (if needed) ====================
    public void setNumberOfMoons(int numberOfMoons) {
        this.numberOfMoons = numberOfMoons;
    }

    public void setHasRings(boolean hasRings) {
        this.hasRings = hasRings;
    }

    @Override
    public String getType() {
        return "Planet";
    }

    @Override
    public String getDescription() {
        return "Name: " + getName() +
                "\nConstellation: " + getConstellation() +
                "\nNumber of Moons: " + numberOfMoons + "\n" +
                (hasRings ? "Has rings" : "No rings System");
    }

    @Override
    public String toString() {
        return getName() +
                " | Planet | Moons: " + numberOfMoons +
                " | Rings: " + (hasRings ? "Yes" : "No");
    }
}