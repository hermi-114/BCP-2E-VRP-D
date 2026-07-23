package src.main.java.config;

public class Constants {

    public static int TOTAL_CUSTOMERS;
    
    // ======= FLEET PARAMETER ========
    // ===== MAYBE NOT SELF CONFIGURATION - DECIDE FROM BRANCHING IN MASTER PROBLEM =====
    public static int TOTAL_TRUCKS = 3;
    public static int TOTAL_DRONES = 6;
    public static int MAX_DRONES_PER_TRUCK = 2;
    public static int DRONE_SETUP_TIME = 5; // in minute
    public static double DRONE_SPEED = 10;

    // ====== DRONE REACHABLE NEIGHBOURHOOD ======
    public static final double DRONE_WEIGHT = 2.5; // W
    public static final double DRONE_BATTERY_WEIGHT = -1.0; // m
    public static final double DRONE_MAX_PAYLOAD = 2.5; // q
    public static final double GRAVITY = 9.81; // g (N/kg)
    public static final double AIR_DENSITY = 1.204; // ρ (kg/m^3)
    public static final double SPINNING_BLADE_AREA = 0.203; // ζ (m^2)
    public static final int DRONE_ROTOR_NUMBER = 4; // rotors
    public static final double DRONE_BATTERY_CAPACITY = 150 * 3600; // B_c (Watt-hours = 3600 Joules)

    // ====== PENALTY ======
    public static double PENALTY_DEMO = 1e6 - 1;

}