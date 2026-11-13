import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * Task 1: Common superclass for Volvo240 and Saab95. Holds all shared state and
 * behaviour, so the subclasses only contain what is unique to them
 * (no code duplication). New car models can be added by extending this
 * class without modifying it (Open-Closed Principle).
 *
 * Task 2: Implements Movable (position, direction, move, turnLeft, turnRight).
 */
public abstract class Car implements Movable {

    // ---------------- Task 1: shared state ----------------
    private final int nrDoors;
    private final double enginePower;
    private double currentSpeed;
    private Color color;
    private final String modelName;

    // ---------------- Task 2: position and direction ----------------
    private final Point2D.Double location;
    private String facingDirection;

    protected Car(int nrDoors, double enginePower, Color color, String modelName) {
        this.nrDoors = nrDoors;
        this.enginePower = enginePower;
        this.currentSpeed = 0;
        this.color = color;
        this.modelName = modelName;
        this.location = new Point2D.Double(0, 0);
        this.facingDirection = "Forward";
    }

    // ---------------- Task 1: public interface ----------------
    public int getNrDoors() {
        return nrDoors;
    }

    public double getEnginePower() {
        return enginePower;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color clr) {
        color = clr;
    }

    public String getModelName() {
        return modelName;
    }

    public void startEngine() {
        currentSpeed = 0.1;
    }

    public void stopEngine() {
        currentSpeed = 0;
    }

    /** Each model decides how strongly it accelerates. Internal detail -> protected. */
    protected abstract double speedFactor();

    // Moved here from Saab95/Volvo240 (Task 1: removes the duplicated gas/brake).
    public void gas(double amount) {
        incrementSpeed(amount);
    }

    public void brake(double amount) {
        decrementSpeed(amount);
    }

    // ---------------- Task 4: sanity checks ----------------

    /** gas() and brake() only accept values in [0,1] (the negated form also rejects NaN). */
    private void checkAmount(double amount) {
        if (!(amount >= 0 && amount <= 1))
            throw new IllegalArgumentException("Amount must be in [0,1] but was " + amount);
    }

    /** Task 4: speed never exceeds enginePower, and gas() can never decrease speed. */
    protected void incrementSpeed(double amount) {
        checkAmount(amount);
        currentSpeed = Math.min(currentSpeed + speedFactor() * amount, enginePower);
    }

    /** Task 4: speed never goes below 0, and brake() can never increase speed. */
    protected void decrementSpeed(double amount) {
        checkAmount(amount);
        currentSpeed = Math.max(currentSpeed - speedFactor() * amount, 0);
    }

    // ---------------- Task 2: Movable ----------------

    @Override
    public void move() {
        if (Objects.equals(facingDirection, "Forward"))
            location.setLocation(location.x, location.getY() + currentSpeed);
        else if (Objects.equals(facingDirection, "East"))
            location.setLocation(location.getX() + currentSpeed, location.y);
        else if (Objects.equals(facingDirection, "Back"))
            location.setLocation(location.x, location.getY() - currentSpeed);
        else if (Objects.equals(facingDirection, "West"))
            location.setLocation(location.getX() - currentSpeed, location.y);
    }

    @Override
    public void turnLeft() {
        if (Objects.equals(facingDirection, "Forward"))
            facingDirection = "West";
        else if (Objects.equals(facingDirection, "East"))
            facingDirection = "Forward";
        else if (Objects.equals(facingDirection, "Back"))
            facingDirection = "East";
        else if (Objects.equals(facingDirection, "West"))
            facingDirection = "Back";
    }

    @Override
    public void turnRight() {
        if (Objects.equals(facingDirection, "Forward"))
            facingDirection = "East";
        else if (Objects.equals(facingDirection, "East"))
            facingDirection = "Back";
        else if (Objects.equals(facingDirection, "Back"))
            facingDirection = "West";
        else if (Objects.equals(facingDirection, "West"))
            facingDirection = "Forward";
    }

    public double getX() {
        return location.getX();
    }

    public double getY() {
        return location.getY();
    }

    public String getFacingDirection() {
        return facingDirection;
    }
}