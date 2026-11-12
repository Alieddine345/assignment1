
public class CarTest {

    public static void main(String[] args) {

        // TEST 1: Engine stops correctly

        Volvo240 car = new Volvo240();

        car.startEngine();
        car.stopEngine();

        if (car.getCurrentSpeed() == 0) {
            System.out.println("SUCCESS: Engine stops correctly");
        } else {
            System.out.println("FAILURE: Engine did not stop");
        }


        // TEST 2: Saab turbo

        Saab95 slowSaab = new Saab95();
        Saab95 fastSaab = new Saab95();

        slowSaab.setTurboOff();
        fastSaab.setTurboOn();

        slowSaab.gas(0.5);
        fastSaab.gas(0.5);

        if (slowSaab.getCurrentSpeed() < fastSaab.getCurrentSpeed()) {
            System.out.println("SUCCESS: Turbo works");
        } else {
            System.out.println("FAILURE: Turbo doesn't work");
        }


        // TEST 3: Braking

        Volvo240 brakingCar = new Volvo240();

        brakingCar.startEngine();
        brakingCar.gas(0.5);

        double speedBefore = brakingCar.getCurrentSpeed();

        brakingCar.brake(0.5);

        double speedAfter = brakingCar.getCurrentSpeed();

        if (speedAfter >= 0 && speedAfter < speedBefore) {
            System.out.println("SUCCESS: Braking decreases speed");
        } else {
            System.out.println("FAILURE: Braking did not decrease speed");
        }


        // TEST 4: Moving forward

        Saab95 movingCar = new Saab95();

        movingCar.startEngine();
        movingCar.gas(1.0);

        double yBefore = movingCar.getY();

        movingCar.move();

        double yAfter = movingCar.getY();

        if (yAfter > yBefore) {
            System.out.println("SUCCESS: Car moves forward");
        } else {
            System.out.println("FAILURE: Car did not move forward");
        }


        // TEST 5: Turning right

        Saab95 rightCar = new Saab95();

        rightCar.startEngine();
        rightCar.gas(1.0);

        double xBeforeRight = rightCar.getX();
        double yBeforeRight = rightCar.getY();

        rightCar.turnRight();
        rightCar.move();

        double xAfterRight = rightCar.getX();
        double yAfterRight = rightCar.getY();

        if (xAfterRight > xBeforeRight && yAfterRight == yBeforeRight) {
            System.out.println("SUCCESS: Turning right works");
        } else {
            System.out.println("FAILURE: Turning right does not work");
        }


        // TEST 6: Turning left

        Saab95 leftCar = new Saab95();

        leftCar.startEngine();
        leftCar.gas(1.0);

        double xBeforeLeft = leftCar.getX();
        double yBeforeLeft = leftCar.getY();

        leftCar.turnLeft();
        leftCar.move();

        double xAfterLeft = leftCar.getX();
        double yAfterLeft = leftCar.getY();

        if (xAfterLeft < xBeforeLeft && yAfterLeft == yBeforeLeft) {
            System.out.println("SUCCESS: Turning left works");
        } else {
            System.out.println("FAILURE: Turning left does not work");
        }

    }
}
