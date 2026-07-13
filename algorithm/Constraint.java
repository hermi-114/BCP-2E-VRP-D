package algorithm;

import util.DataLoader;

public class Constraint {
    // Appendix A - Drone reachable neighbourhood
    // ===== Power consumption = (W + m + q)^(3/2) × sqrt( g^3 / (2 × ρ × ζ × h) ) =====
    public static boolean droneEnergy_constraint(int src, int dest, double demand) {

        if (!(DataLoader.getNode(src).isDroneAllowed) || !(DataLoader.getNode(dest).isDroneAllowed)) {
            return false;
        }

        if(demand > Constants.DRONE_MAX_PAYLOAD) {
            return false;
        }

        double distKm = DataLoader.dist[src][dest];
        double distM = distKm * 1000;

        double speedMps = Constants.DRONE_SPEED;
        double timeSeconds = distM / speedMps;   

        double W = Constants.DRONE_WEIGHT;
        // double m = Constants.DRONE_BATTERY_WEIGHT; // already included in drone weight
        double q = demand;

        
        double g = Constants.GRAVITY;
        double p = Constants.AIR_DENSITY;
        double T = Constants.SPINNING_BLADE_AREA;
        int h = Constants.DRONE_ROTOR_NUMBER;
        
        double sqrtTerm = Math.sqrt(g*g*g / (2 * p * T * h));

        double powerConsumptionGo   = Math.pow(W + q, 1.5) * sqrtTerm;
        double powerConsumptionBack = Math.pow(W + 0, 1.5) * sqrtTerm;

        double energyGo   = powerConsumptionGo   * timeSeconds;
        double energyBack = powerConsumptionBack * timeSeconds;
        double totalEnergy = energyGo + energyBack; // Joules

        return totalEnergy <= Constants.DRONE_BATTERY_CAPACITY;
    }


    // ===== IS SOLUTION FEASIBLE =====
    // check if solution has the right property
    // truckJourney: d - * - d
    // droneJourney: 
    // public static boolean isFeasible(Solution sol) {
    //     List<Integer> truckJourney = sol.truckJourney;
    //     List<List<DroneSchedule>> droneJourney = sol.droneJourney;

    //     // TODO
    //     // ....

    //     return true;
    // }

}
