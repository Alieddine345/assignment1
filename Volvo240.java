import java.awt.*;
import java.awt.geom.Point2D;

public class Volvo240 extends cars {

    public final static double trimFactor = 1.25;

    public Volvo240() {
        super(4, 100, 0,
                Color.black, "Volvo240", new Point2D.Double(0,0), "Forward");
    }

    @Override
    public double speedFactor() {
        return getEnginePower() * 0.01 * trimFactor;
    }


    // TODO fix this method according to lab pm
    public void gas(double amount) {
        incrementSpeed(amount);
    }

    // TODO fix this method according to lab pm
    public void brake(double amount) {
        decrementSpeed(amount);
    }


    // task2


}
