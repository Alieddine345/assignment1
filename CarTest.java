/**
 * Test suite for the car assignment.
 *
 *   PART 1: Helpers
 *   PART 2: Task 1 tests (Saab95 / Volvo240 / Car basics)
 *   PART 3: Task 2 tests (Movable: move, turnLeft, turnRight)
 *   PART 4: Task 4 tests (sanity checks on gas() and brake())
 *   PART 5: main (runs everything)
 *
 * Each test checks the behaviour we EXPECT from the specification,
 * not whatever the code happens to do.
 */
public class CarTest {

    // =====================================================================
    // PART 1: HELPERS
    // =====================================================================

    private static final double EPS = 1e-9;
    private static int failures = 0;

    private static void reportResult(String testName, boolean success) {
        if (!success) failures++;
        System.out.println((success ? "SUCCESS: " : "FAILURE: ") + testName);
    }

    /** Compare doubles with a small tolerance instead of ==. */
    private static boolean approx(double a, double b) {
        return Math.abs(a - b) < EPS;
    }

    /** True if car.gas(amount) throws IllegalArgumentException. */
    private static boolean gasThrows(Car car, double amount) {
        try {
            car.gas(amount);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    /** True if car.brake(amount) throws IllegalArgumentException. */
    private static boolean brakeThrows(Car car, double amount) {
        try {
            car.brake(amount);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    // =====================================================================
    // PART 2: TASK 1 TESTS (car basics)
    // =====================================================================

    /** A Saab95 with turbo gains more speed than one without. */
    private static void testTurbo() {
        Saab95 slowSaab = new Saab95();
        Saab95 fastSaab = new Saab95();
        slowSaab.setTurboOff();
        fastSaab.setTurboOn();
        slowSaab.gas(0.5);
        fastSaab.gas(0.5);
        reportResult("Turbo yields more speed",
                slowSaab.getCurrentSpeed() < fastSaab.getCurrentSpeed());
    }

    /** stopEngine() sets the speed to 0. */
    private static void testStopEngine() {
        Volvo240 car = new Volvo240();
        car.startEngine();
        car.stopEngine();
        reportResult("Engine stops correctly", car.getCurrentSpeed() == 0);
    }

    /** brake() lowers the speed but never below 0. */
    private static void testBrakingDecreasesSpeed() {
        Volvo240 car = new Volvo240();
        car.startEngine();
        car.gas(0.5);
        double before = car.getCurrentSpeed();
        car.brake(0.5);
        double after = car.getCurrentSpeed();
        reportResult("Braking decreases speed", after >= 0 && after < before);
    }

    // =====================================================================
    // PART 3: TASK 2 TESTS (Movable)
    // =====================================================================

    /** A new car faces "Forward", so move() increases y by the speed. */
    private static void testMoveForward() {
        Saab95 car = new Saab95();
        car.startEngine();
        car.gas(1.0);
        double speed = car.getCurrentSpeed();
        car.move();
        reportResult("Car moves forward by its speed",
                approx(car.getY(), speed) && approx(car.getX(), 0));
    }

    /** turnRight() then move() increases x. */
    private static void testTurnRight() {
        Saab95 car = new Saab95();
        car.startEngine();
        car.gas(1.0);
        double speed = car.getCurrentSpeed();
        car.turnRight();
        car.move();
        reportResult("Turning right moves car east",
                approx(car.getX(), speed) && approx(car.getY(), 0));
    }

    /** turnLeft() then move() decreases x. */
    private static void testTurnLeft() {
        Saab95 car = new Saab95();
        car.startEngine();
        car.gas(1.0);
        double speed = car.getCurrentSpeed();
        car.turnLeft();
        car.move();
        reportResult("Turning left moves car west",
                approx(car.getX(), -speed) && approx(car.getY(), 0));
    }

    /** Two right turns make the car face "Back", so y decreases. */
    private static void testMoveBack() {
        Volvo240 car = new Volvo240();
        car.startEngine();
        car.gas(1.0);
        double speed = car.getCurrentSpeed();
        car.turnRight();
        car.turnRight();
        car.move();
        reportResult("Facing back moves car down in y",
                approx(car.getY(), -speed) && approx(car.getX(), 0));
    }

    /** Four turns in the same direction give the original direction. */
    private static void testFourTurnsReturnToStart() {
        Volvo240 car = new Volvo240();
        String start = car.getFacingDirection();
        for (int i = 0; i < 4; i++) car.turnRight();
        boolean right = car.getFacingDirection().equals(start);
        for (int i = 0; i < 4; i++) car.turnLeft();
        reportResult("Four turns in the same direction give the original direction",
                right && car.getFacingDirection().equals(start));
    }

    /** A left turn followed by a right turn cancels out. */
    private static void testLeftThenRightCancels() {
        Volvo240 car = new Volvo240();
        car.turnLeft();
        car.turnRight();
        reportResult("turnLeft then turnRight gives the original direction",
                car.getFacingDirection().equals("Forward"));
    }

    /** A car with speed 0 stays where it is. */
    private static void testZeroSpeedDoesNotMove() {
        Volvo240 car = new Volvo240();
        car.move();
        reportResult("Car with speed 0 does not move",
                car.getX() == 0 && car.getY() == 0);
    }

    // =====================================================================
    // PART 4: TASK 4 TESTS (sanity checks on gas() and brake())
    // =====================================================================

    // ---- Requirement 1: gas() and brake() only accept values in [0,1] ----

    private static void testGasRejectsInvalidAmount() {
        Volvo240 car = new Volvo240();
        car.startEngine();
        reportResult("gas() rejects amounts outside [0,1]",
                gasThrows(car, 1.5) && gasThrows(car, -0.1));
    }

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

    /** A rejected call must not change the speed. */
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

    // ---- Requirement 2: currentSpeed is always in [0, enginePower] ----

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

    // ---- Requirement 3: gas() cannot decrease speed ----

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

    // ---- Requirement 4: brake() cannot increase speed ----

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

    // =====================================================================
    // PART 5: MAIN (runs all tests)
    // =====================================================================

    public static void main(String[] args) {

        System.out.println("--- Task 1: car basics ---");
        testTurbo();
        testStopEngine();
        testBrakingDecreasesSpeed();

        System.out.println("--- Task 2: Movable ---");
        testMoveForward();
        testTurnRight();
        testTurnLeft();
        testMoveBack();
        testFourTurnsReturnToStart();
        testLeftThenRightCancels();
        testZeroSpeedDoesNotMove();

        System.out.println("--- Task 4: sanity checks ---");
        testGasRejectsInvalidAmount();
        testBrakeRejectsInvalidAmount();
        testGasAcceptsBoundaries();
        testBrakeAcceptsBoundaries();
        testInvalidAmountLeavesSpeedUnchanged();
        testSpeedNeverAboveEnginePower();
        testSpeedNeverBelowZero();
        testGasNeverDecreasesSpeed();
        testBrakeNeverIncreasesSpeed();

        System.out.println(failures == 0
                ? "\nAll tests passed."
                : "\n" + failures + " test(s) FAILED.");
    }
}