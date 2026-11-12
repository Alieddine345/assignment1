import java.awt.*;
    public class Saab95 extends cars  {

    private boolean turboOn;

    public Saab95() {
        super(2,125,0,Color.red,
                "Saab95",new Point(0,0),"Forward");
        boolean turboOn = false;
    }


    public void setTurboOn() {
        turboOn = true;
    }

    public void setTurboOff() {
        turboOn = false;
    }
    
    public double speedFactor() {
        double turbo = 1;
        if(turboOn) turbo = 1.3;
        return getEnginePower() * 0.01 * turbo;
    }

    // TODO fix this method according to assignment instructions
    public void gas(double amount) {
        incrementSpeed(amount);
    }

    // TODO fix this method according to assignment instructions
    public void brake(double amount) {
        decrementSpeed(amount);

     }

}
