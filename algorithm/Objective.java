package algorithm;

import java.util.List;
import model.DroneSchedule;
import model.Node;
import model.Solution;
import util.DataLoader;

public class Objective {

    public static double masterObjectiveFunction(Solution sol) {
        List<Integer> truckJourney = sol.truckJourney;
        List<List<DroneSchedule>> droneJourney = sol.droneJourney;
        
        double totalTime = 0;
        int size = truckJourney.size();
        for(int i = 0; i < size - 1; i++) {
            int customer = truckJourney.get(i);
            int nextCustomer = truckJourney.get(i+1);

            totalTime += DataLoader.dist[customer][nextCustomer]; // travel time

            if(customer == 0) { // depot
                continue;
            }

            List<DroneSchedule> schedules = droneJourney.get(customer);
            List<Node> customerProperties = DataLoader.nodes;

            double timeTruckServing = customerProperties.get(customer).truckServiceTime;
            double timeDroneServing = 0;

            for(var schedule : schedules) {
                if(schedule.makespan > timeDroneServing) {
                    timeDroneServing = schedule.makespan;
                }
            }

            totalTime += Math.max(timeTruckServing, timeDroneServing); // serving time
        }

        return totalTime;
    }
}