package algorithm;

public class Constants {

    public static int TOTAL_CUSTOMERS;
    
    // ======= FLEET PARAMETER ========
    // ===== MAYBE NOT SELF CONFIGURATION - DECIDE FROM BRANCHING IN MASTER PROBLEM =====
    public static int TOTAL_TRUCKS = 10;
    public static int TOTAL_DRONES = 50;
    public static int MAX_DRONES_PER_TRUCK = 5;
    public static int DRONE_SETUP_TIME = 5; // (minute)
    public static double DRONE_SPEED = 10; // (m/s)

    // ====== DRONE REACHABLE NEIGHBOURHOOD ======
    public static final double DRONE_WEIGHT = 6; // W (including battery weight)
    public static final double DRONE_BATTERY_WEIGHT = 0; // m (already in weight)
    public static final double DRONE_MAX_PAYLOAD = 10; // q (kg)
    public static final double GRAVITY = 9.81; // g (N/kg)
    public static final double AIR_DENSITY = 1.204; // ρ (kg/m^3)
    public static final double SPINNING_BLADE_AREA = 0.15; // ζ (m^2)
    public static final int DRONE_ROTOR_NUMBER = 8; // rotors
    public static final double DRONE_BATTERY_CAPACITY = 400 * 3600; // B_c (Watt-hours = 3600 Joules)

    // ====== PENALTY ======
    public static double PENALTY_DEMO = 1e6 - 1;
    public static double EPSILON = 10;

}