package algorithm;

import java.util.List;
import model.DroneSchedule;
import model.Solution;
import util.DataLoader;

public class Constraint {
    // Appendix A - Drone reachable neighbourhood
    // ===== Power consumption = (W + m + q)^(3/2) × sqrt( g^3 / (2 × ρ × ζ × h) ) =====
    public static int droneEnergy_Constraint(int src, int dest, double demand) {

        if(demand >= Constants.DRONE_MAX_PAYLOAD) {
            return 1;
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

        return distance * (powerConsumptionGo + powerConsumptionBack) <= batteryCapacity
        ? 0 : 1;
    }


    // check if number of trucks <= max trucks
    public static boolean truckUsed(List<Integer> truckJourney) {
        return truckJourney.stream()
                .filter(a -> a == 0)
                .count() <= Constants.TOTAL_TRUCKS;

    }


    // check if number of drones per truck <= max drones per truck
    // check if each customer only got visitted by ONE drone 
    public static boolean droneUsed(List<List<DroneSchedule>> droneJourney) {
        int maxDronesPerTruck = Constants.MAX_DRONES_PER_TRUCK;
        
        boolean[] visitted = new boolean[Constants.TOTAL_CUSTOMERS];
        for(var schedules : droneJourney) {
            if(schedules.size() > maxDronesPerTruck)
                return false;

            for(var schedule : schedules) {
                for(int customer : schedule.customerServed) {
                    if(visitted[customer])
                        return false; // each customer should be vistted once

                    visitted[customer] = true;
                }
            }
        }

        return true;
    }


    // ===== IS SOLUTION FEASIBLE =====
    // check if solution has the right property
    // truckJourney: d - * - d
    // droneJourney: 
    public static boolean isFeasible(Solution sol) {
        List<Integer> truckJourney = sol.truckJourney;
        List<List<DroneSchedule>> droneJourney = sol.droneJourney;

        // TODO
        // ....

        return true;
    }

}
