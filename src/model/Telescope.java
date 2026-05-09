package model;

public class Telescope {
    private int telescopeID;
    private String model;
    private double apertureSize;
    private int maxMagnification;
    private boolean isMotorized;

    public Telescope(int telescopeID, String model, int maxMagnification, double apertureSize, boolean isMotorized) {
        this.telescopeID = telescopeID;
        this.model = model;
        this.maxMagnification = maxMagnification;
        this.apertureSize = apertureSize;
        this.isMotorized = isMotorized;
    }

    public boolean canObserve(CelestialObject object){
        if (object.getMagnitude()>6){
            return apertureSize > 100;
        }
        return true;
    }

    public int getMaxMagnification() {
        return maxMagnification;
    }

    public boolean isMotorized() {
        return isMotorized;
    }

    public void setMotorized(boolean motorized) {
        isMotorized = motorized;
    }

    public String getModel() {
        return model;
    }

    public int getTelescopeID() {
        return telescopeID;
    }

    public double getApertureSize() {
        return apertureSize;
    }

    @Override
    public String toString() {
        return model +
                " | Aperture: " + apertureSize + "mm" +
                " | Max Mag: " + maxMagnification +
                " | Motorized: " + isMotorized;
    }
}
