import java.awt.*;
import java.util.Objects;

public abstract class cars implements Movable {

    private int nrDoors;
    private double enginePower;
    private double currentSpeed;
    private Color color;
    private String modelName;
    private Point location;
    private String facingDirection;

    // Constructor
    public cars(int nrDoors, double enginePower, double currentSpeed,
                Color color, String modelName, Point location, String facingDirection) {
        this.nrDoors = nrDoors;
        this.enginePower = enginePower;
        this.currentSpeed = currentSpeed;
        this.color = color;
        this.modelName = modelName;
        this.location = location;
        this.facingDirection = facingDirection;
    }

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

    public void startEngine() {
        currentSpeed = 0.1;
    }

    public void stopEngine() {
        currentSpeed = 0;
    }

    public abstract double speedFactor();

    public void incrementSpeed(double amount) {
        currentSpeed = getCurrentSpeed() + speedFactor() * amount;
    }

    public void decrementSpeed(double amount) {
        currentSpeed = getCurrentSpeed() - speedFactor() * amount;
    }

    //Task2
/*
    public int getX() {
        return location.x;
    }
    public int getY() {
        return location.y;
    }


    we have location  x and y
    speed 100
    x =0 y=0
    move one step (speed)


     */

    public void move() {
    if (Objects.equals(facingDirection, "Forward"))
        location.setLocation( location.x , location.getY() + currentSpeed );
    else if (Objects.equals(facingDirection, "East"))
        location.setLocation( location.getX() + currentSpeed , location.y );
    else if (Objects.equals(facingDirection, "Back"))
        location.setLocation( location.x , location.getY() - currentSpeed );
    else if (Objects.equals(facingDirection, "West"))
        location.setLocation( location.getX() - currentSpeed , location.y );
        }

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

}
