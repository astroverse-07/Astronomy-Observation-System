package backend.model;

public abstract class CelestialObject {
    private String name;
    private int lightYears;
    private double magnitude;
    private int rightAscension;
    private int declination;
    private String constellation;

    public CelestialObject(String name, int lightYears, double magnitude, int rightAscension, int declination, String constellation) {
        this.name = name;
        this.lightYears = lightYears;
        this.magnitude = magnitude;
        this.rightAscension = rightAscension;
        this.declination = declination;
        this.constellation = constellation;
    }

    public abstract String getType();

    public abstract String getDescription();

    public String getConstellation(){
        return this.constellation;
    };

    public boolean isVisible(int hour) {
        if (hour<0 || hour>23){
            System.out.println("Hours must be between 0-24");
        }
        return hour >= 20 || hour <= 5;
    }

    public String getName() {
        return name;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public int getLightYears() {
        return lightYears;
    }

    @Override
    public String toString() {
        return "CelestialObject{" +
                "constellation='" + constellation + '\'' +
                ", name='" + name + '\'' +
                ", lightYears=" + lightYears +
                ", magnitude=" + magnitude +
                ", rightAscension=" + rightAscension +
                ", declination=" + declination +
                '}';
    }
}
