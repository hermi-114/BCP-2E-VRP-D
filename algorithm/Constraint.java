package algorithm;

import util.DataLoader;

public class Constraint {
    // Appendix A - Drone reachable neighbourhood
    // ===== Power consumption = (W + m + q)^(3/2) × sqrt( g^3 / (2 × ρ × ζ × h) ) =====
    public static boolean droneEnergy(int src, int dest, double demand) {

        if(demand >= Constants.DRONE_MAX_PAYLOAD) {
            return false;
        }

        double distance = DataLoader.dist[src][dest];

        double W = Constants.DRONE_WEIGHT;
        double m = Constants.DRONE_BATTERY_WEIGHT;
        double q = demand;

        
        double g = Constants.GRAVITY;
        double p = Constants.AIR_DENSITY;
        double T = Constants.SPINNING_BLADE_AREA;
        int h = Constants.DRONE_ROTOR_NUMBER;
        
        double behind = Math.sqrt(g*g*g / (2 * p * T * h));

        double batteryCapacity = Constants.DRONE_BATTERY_CAPACITY;

        double powerConsumptionGo   = Math.pow(W + m + q, 1.5) * behind;
        double powerConsumptionBack = Math.pow(W + m + 0, 1.5) * behind;

        return distance * (powerConsumptionGo + powerConsumptionBack) <= batteryCapacity;
    }
}
