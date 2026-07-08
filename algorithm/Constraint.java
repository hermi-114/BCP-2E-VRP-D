package algorithm;

import util.DataLoader;

public class Constraint {
    // Appendix A - Drone reachable neighbourhood
    // ===== Power consumption = (W + m + q)^(3/2) × sqrt( g^3 / (2 × ρ × ζ × h) ) =====
    public static boolean droneEnergy_constraint(int src, int dest, double demand) {

        if (!(DataLoader.getNode(src).isDroneAllowed) || !(DataLoader.getNode(dest).isDroneAllowed)) {
            return false;
        }

        if(demand/4 > Constants.DRONE_MAX_PAYLOAD) {
            return false;
        }

        double distance = DataLoader.dist[src][dest] * 1000;

        double W = Constants.DRONE_WEIGHT;
        double m = Constants.DRONE_BATTERY_WEIGHT;
        double q = demand;

        
        double g = Constants.GRAVITY;
        double p = Constants.AIR_DENSITY;
        double T = Constants.SPINNING_BLADE_AREA;
        int h = Constants.DRONE_ROTOR_NUMBER;
        
        double behind = Math.sqrt(g*g*g / (2 * p * T * h));

        double powerConsumptionGo   = Math.pow(W + m + q, 1.5) * behind;
        double powerConsumptionBack = Math.pow(W + m + 0, 1.5) * behind;

        double time = distance / Constants.DRONE_SPEED;

        double energyGoJoules   = powerConsumptionGo   * time;
        double energyBackJoules = powerConsumptionBack * time;
        double totalEnergyJoules = energyGoJoules + energyBackJoules;

        // Convert Joules to Watt-hours (1 Wh = 3600 Joules)

        // Final Battery Check
        return totalEnergyJoules <= Constants.DRONE_BATTERY_CAPACITY;
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
