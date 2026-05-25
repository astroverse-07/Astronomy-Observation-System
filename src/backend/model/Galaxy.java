package backend.model;

import backend.exception.InvalidDataException;

public class Galaxy extends CelestialObject{
    private String galaxyType;
    private int estimatedStars;

    public Galaxy(String name, int lightYears, double magnitude, int rightAscension, int declination, String constellation, String galaxyType, int estimatedStars) throws InvalidDataException {
        super(name, lightYears, magnitude, rightAscension, declination, constellation);
        this.galaxyType = galaxyType;
        this.estimatedStars = estimatedStars;
    }


    @Override
    public String getType() {
        return "Galaxy";
    }

    @Override
    public String getDescription() {
        return "Name: "+getName()+
                "\nType: "+galaxyType+
                "\nEstimated Stars: "+estimatedStars+" billions";
    }

    @Override
    public String toString() {
        return getName() +
                " | Galaxy (" + galaxyType + ")" +
                " | Stars: " + estimatedStars;
    }
}
