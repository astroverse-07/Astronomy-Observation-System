package model;

public class Planet extends CelestialObject{
    private int numberOfMoons;
    private boolean hasRings;

    public Planet(String name, int lightYears, double magnitude, int rightAscension, int declination, String constellation, int numberOfMoons, boolean hasRings) {
        super(name, lightYears, magnitude, rightAscension, declination, constellation);
        this.numberOfMoons = numberOfMoons;
        this.hasRings = hasRings;
    }

    @Override
    public String getType() {
        return "Planet";
    }

    @Override
    public String getDescription() {
        return  "Name: "+getName()+
                "Constellation: "+getConstellation()+
                "\nNumber of Moons: "+numberOfMoons+ "\n"+
                (hasRings? "Has rings": "No rings System");
    }
}
