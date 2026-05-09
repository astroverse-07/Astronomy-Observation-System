package model;

public class Star extends CelestialObject {
    private String spectralClass;
    private boolean isNebula;

    public Star(String name, int lightYears, double magnitude, int rightAscension, int declination, String constellation, boolean isNebula, String spectralClass) {
        super(name, lightYears, magnitude, rightAscension, declination, constellation);
        this.isNebula = isNebula;
        this.spectralClass = spectralClass;
    }

    @Override
    public String getType() {
        return "Star";
    }

    @Override
    public String getDescription() {
       return  "Name: "+getName()+
               "\nConstellation: " + getConstellation()+
               "\nSpectral Class: " + spectralClass + "\n" +
                (isNebula ? "Lies within Nebula region" : "Does not lie within Nebula region");
    }

    @Override
    public String toString() {
        return getName() +
                " | Star | " +
                "Mag: " + getMagnitude() +
                " | Spectral: " + spectralClass;
    }
}
