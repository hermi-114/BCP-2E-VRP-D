package algorithm;

import java.util.List;
import model.DroneSchedule;
import model.Solution;
import util.DataLoader;

public class Objective {

    public static double masterObjectiveFunction(Solution sol) {
        List<Integer> truckJourney = sol.truckJourney;
        List<List<DroneSchedule>> droneJourney = sol.droneJourney;
        int size = truckJourney.size();
        
        double totalTime = 0; // store the local longest duration route
        double singleRouteTime = 0; // := travel time + serve time
        for(int i = 1; i < size; i++) { // skip first, always is depot
            int customer = truckJourney.get(i-1);
            int nextCustomer = truckJourney.get(i);

            singleRouteTime += DataLoader.dist[customer][nextCustomer]; // travel time

            if(customer == 0) { // depot
                continue;
            } 
            
            if(nextCustomer == 0) {
                if(singleRouteTime > totalTime) {
                    totalTime = singleRouteTime;
                }
                singleRouteTime = 0; // re-compute another route
            }

            List<DroneSchedule> schedules = droneJourney.get(nextCustomer);

            double timeTruckServing = DataLoader.getNode(nextCustomer).truckServiceTime;
            double timeDroneServing = 0;

            for(var schedule : schedules) {
                if(schedule.makespan > timeDroneServing) {
                    timeDroneServing = schedule.makespan;
                }
            }

            singleRouteTime += Math.max(timeTruckServing, timeDroneServing); // serving time
        }

        return totalTime;
    }
}