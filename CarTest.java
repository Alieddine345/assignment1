
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

        // ---------- Task 4: sanity checks ----------
        testGasRejectsInvalidAmount();
        testBrakeRejectsInvalidAmount();
        testGasAcceptsBoundaries();
        testBrakeAcceptsBoundaries();
        testInvalidAmountLeavesSpeedUnchanged();
        testSpeedNeverAboveEnginePower();
        testSpeedNeverBelowZero();
        testGasNeverDecreasesSpeed();
        testBrakeNeverIncreasesSpeed();
    }

    private static void reportResult(String testName, boolean success) {
        System.out.println((success ? "SUCCESS: " : "FAILURE: ") + testName);
    }

    private static boolean gasThrows(Volvo240 car, double amount) {
        try {
            car.gas(amount);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    private static boolean brakeThrows(Volvo240 car, double amount) {
        try {
            car.brake(amount);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    // gas() only accepts values in [0,1]
    private static void testGasRejectsInvalidAmount() {
        Volvo240 car = new Volvo240();
        car.startEngine();
        reportResult("gas() rejects amounts outside [0,1]",
                gasThrows(car, 1.5) && gasThrows(car, -0.1));
    }

    // brake() only accepts values in [0,1]
    private static void testBrakeRejectsInvalidAmount() {
        Volvo240 car = new Volvo240();
        car.startEngine();
        car.gas(0.5);
        reportResult("brake() rejects amounts outside [0,1]",
                brakeThrows(car, 1.5) && brakeThrows(car, -0.1));
    }

    private static void testGasAcceptsBoundaries() {
        Volvo240 car = new Volvo240();
        car.startEngine();
        reportResult("gas() accepts 0 and 1",
                !gasThrows(car, 0) && !gasThrows(car, 1));
    }

    private static void testBrakeAcceptsBoundaries() {
        Volvo240 car = new Volvo240();
        car.startEngine();
        car.gas(1.0);
        reportResult("brake() accepts 0 and 1",
                !brakeThrows(car, 0) && !brakeThrows(car, 1));
    }

    // A rejected call must not change the speed
    private static void testInvalidAmountLeavesSpeedUnchanged() {
        Volvo240 car = new Volvo240();
        car.startEngine();
        car.gas(0.5);
        double before = car.getCurrentSpeed();
        gasThrows(car, 5);
        brakeThrows(car, -5);
        reportResult("Rejected gas()/brake() does not change speed",
                car.getCurrentSpeed() == before);
    }

    // currentSpeed <= enginePower
    private static void testSpeedNeverAboveEnginePower() {
        Saab95 car = new Saab95();
        car.setTurboOn();
        car.startEngine();
        boolean ok = true;
        for (int i = 0; i < 1000; i++) {
            car.gas(1.0);
            if (car.getCurrentSpeed() > car.getEnginePower()) ok = false;
        }
        reportResult("Speed never exceeds enginePower", ok);
    }

    // currentSpeed >= 0
    private static void testSpeedNeverBelowZero() {
        Volvo240 car = new Volvo240();
        car.startEngine();
        car.gas(0.1);
        boolean ok = true;
        for (int i = 0; i < 1000; i++) {
            car.brake(1.0);
            if (car.getCurrentSpeed() < 0) ok = false;
        }
        reportResult("Speed never below 0", ok);
    }

    // gas() cannot decrease speed
    private static void testGasNeverDecreasesSpeed() {
        Volvo240 car = new Volvo240();
        car.startEngine();
        boolean ok = true;
        for (double a = 0; a <= 1.0; a += 0.25) {
            double before = car.getCurrentSpeed();
            car.gas(a);
            if (car.getCurrentSpeed() < before) ok = false;
        }
        reportResult("gas() never decreases speed", ok);
    }

    // brake() cannot increase speed
    private static void testBrakeNeverIncreasesSpeed() {
        Volvo240 car = new Volvo240();
        car.startEngine();
        car.gas(1.0);
        boolean ok = true;
        for (double a = 0; a <= 1.0; a += 0.25) {
            double before = car.getCurrentSpeed();
            car.brake(a);
            if (car.getCurrentSpeed() > before) ok = false;
        }
        reportResult("brake() never increases speed", ok);
    }
}