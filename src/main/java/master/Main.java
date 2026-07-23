package src.main.java.master;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.main.java.config.Constants;
import src.main.java.io.DataLoader;
import src.main.java.master.drone.GenerateNeighbourhood;
import src.main.java.master.drone.OneBasedDroneSchedule;
import src.main.java.master.drone.ScheduleCombiner;
import src.main.java.model.DroneSchedule;
import src.main.java.model.ParetoFront;

public class Main {
    public static void main(String[] args) {

        String path = "F:/School/lab_maybe/nhom-3/bai-bao/code/bcp_2e_vrp_d/data/Solomon/";
        String fileName = "C101.txt";
        
        DataLoader.load(path + fileName);

        
        
        // ===============================================
        // ----------------- PRECOMPUTE ------------------
        // ===============================================

        Map<Integer, List<Integer>> neibourhoodMap = GenerateNeighbourhood.generateNeighbourhood();
        int customerSize = Constants.TOTAL_CUSTOMERS;

        int maxDrones = Constants.MAX_DRONES_PER_TRUCK;
        Map<Integer, List<ParetoFront>> paretoMap = new HashMap<>();
        
        
        for(int node = 1; node < customerSize; node++) {
            List<ParetoFront> paretos = new ArrayList<>();

            List<Integer> neighbours = neibourhoodMap.get(node);
            List<List<Integer>> baseCase = OneBasedDroneSchedule.getOneBasedSequences(neighbours); // get every subsets K in neighbourhood
            
            List<DroneSchedule> schedules = new ArrayList<>();
            
            for(List<Integer> subset : baseCase) {
                double makespan = 0;
                double maxStartingTime = 1e6;

                for(int dest : subset) {
                    // System.out.println(DataLoader.getNode(dest).deadline);

                    double deadline = DataLoader.getNode(dest).deadline;
                    double distance = DataLoader.dist[node][dest];
                    makespan += Constants.DRONE_SETUP_TIME + DataLoader.getNode(dest).droneServiceTime + 2 * distance;
                    maxStartingTime = Math.min(maxStartingTime, deadline - makespan + distance);

                    if(maxStartingTime < 0) {
                        break;
                    }
                }

                if(maxStartingTime < 0) {
                    continue;
                }

                List<List<Integer>> sequences = new ArrayList<>();
                sequences.add(subset);
                schedules.add(new DroneSchedule(sequences, makespan, maxStartingTime));
                
            }

            for(DroneSchedule sched : schedules) {
                System.out.println(sched);
            }

            paretos.add(new ParetoFront());
            for(var schedule : schedules) {
                paretos.get(0).tryAddSchedule(schedule);
            }

            // System.out.println(paretos.get(0).nonDominatedSchedules.size());
            // System.out.println("Size: " + schedules.size());


            try {
                for(int total_drone_use = 2; total_drone_use <= maxDrones; total_drone_use++) {
                    ParetoFront paretoFront = new ParetoFront();
                    for(int numDrone_a = 1; numDrone_a <= total_drone_use/2; numDrone_a++) {
                        int numDrone_b = total_drone_use - numDrone_a;
                        for(var s1 : paretos.get(numDrone_a - 1).nonDominatedSchedules) {
                            for(var s2 : paretos.get(numDrone_b - 1).nonDominatedSchedules) {
                                DroneSchedule combined = ScheduleCombiner.combine(s1, s2);
                                paretoFront.tryAddSchedule(combined);
                            }
                        }
                    }
                    paretos.add(paretoFront);
                }

                
            } catch (OutOfMemoryError e) {
                System.err.println("OUT OF MEMORY ERROR: " + e.getMessage());
            }

            paretoMap.put(node, paretos);
        }

        System.out.println("Starting print pareto front");

        for(int i = 1; i <= maxDrones; i++) {
            for(var pareto : paretoMap.get(i)) {
                for(var list : pareto.nonDominatedSchedules) {
                    System.out.println(list.sequences);
                }
    
            }

        }

        // ===============================================
        // ----------------             ------------------
        // ===============================================

    }

    
}
