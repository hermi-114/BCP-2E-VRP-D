
import algorithm.Constants;
import algorithm.ParetoFront;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.DroneSchedule;
import util.DataLoader;
import util.drone_sched.*;

public class Main {
    public static void main(String[] args) {
        run();
    }

    public static void run() {

        String path = "F:/School/lab_maybe/nhom-3/bai-bao/code/bcp_2e_vrp_d/data/Solomon/";
        String type = "RC";
        int number = 201;
        
        DataLoader.load(path + type + number + ".txt");

        
        
        // ===============================================
        // ----------------- PRECOMPUTE ------------------
        // ===============================================

        Map<Integer, List<Integer>> neibourhoodMap = GenerateNeighbourhood.generateNeighbourhood();
        int customerSize = Constants.TOTAL_CUSTOMERS;

        int maxDrones = Constants.MAX_DRONES_PER_TRUCK;
        Map<Integer, Map<Integer, ParetoFront>> paretoMap = new HashMap<>();
        
        
        for(int node = 1; node < customerSize; node++) {
            Map<Integer, ParetoFront> paretos = new HashMap<>();

            List<Integer> neighbours = neibourhoodMap.getOrDefault(node, new ArrayList<>());
            List<List<Integer>> subsets = OneBasedDroneSchedule.getOneBasedSequences(neighbours); // get every subsets K in neighbourhood
            
            List<DroneSchedule> schedules = new ArrayList<>();
            for(List<Integer> subset : subsets) {
                if(subset.isEmpty())
                    continue;

                double makespan = 0;
                double maxStartingTime = 1e6;
                boolean feasible = true;

                for(int dest : subset) {

                    double deadline = DataLoader.getNode(dest).deadline;
                    double flightTime = DataLoader.dist[node][dest] * 1000 / Constants.DRONE_SPEED;
                    double timeAtCustomer = makespan + Constants.DRONE_SETUP_TIME + flightTime;
                    
                    double slack = deadline - timeAtCustomer;
                    if(slack < 0) {
                        feasible = false;
                        break;
                    }

                    maxStartingTime = Math.min(maxStartingTime, slack);

                    makespan += 2*flightTime + DataLoader.getNode(dest).droneServiceTime + Constants.DRONE_SETUP_TIME;
                }

                if (feasible && makespan > 0) {
                    List<List<Integer>> sequences = new ArrayList<>();
                    sequences.add(subset);
                    DroneSchedule sched = new DroneSchedule(sequences, makespan, maxStartingTime);

                    schedules.add(sched);
                }
                
            }
            
            ParetoFront front = new ParetoFront();
            for(var schedule : schedules) {
                front.tryAddSchedule(schedule);
            }

            paretos.put(1, front);

            for(DroneSchedule sched : schedules) {
                System.out.println(sched);
            }


            // System.out.println(paretos.get(0).nonDominatedSchedules.size());
            // System.out.println("Size: " + schedules.size());


            try {
                for(int total_drone_use = 2; total_drone_use <= maxDrones; total_drone_use++) {
                    ParetoFront paretoFront = new ParetoFront();
                    for(int numDrone_a = 1; numDrone_a <= total_drone_use/2; numDrone_a++) {
                        int numDrone_b = total_drone_use - numDrone_a;

                        ParetoFront frontA = paretos.get(numDrone_a);
                        ParetoFront frontB = paretos.get(numDrone_b);

                        if(frontA == null || frontB == null) {
                            continue;
                        }

                        for(var s1 : frontA.nonDominatedSchedules) {
                            for(var s2 : frontB.nonDominatedSchedules) {
                                DroneSchedule combined = ScheduleCombiner.combine(s1, s2);
                                if(combined != null) {
                                    paretoFront.tryAddSchedule(combined);
                                }
                            }
                        }
                    }
                    paretos.put(total_drone_use, paretoFront);
                }

                
            } catch (OutOfMemoryError e) {
                System.err.println("OUT OF MEMORY ERROR: " + e.getMessage());
            }

            paretoMap.put(node, paretos);
        }

        System.out.println("Starting print pareto front");

        for(int i = 1; i <= customerSize; i++) {
            System.out.println("Node " + i + ":");
            Map<Integer, ParetoFront> nodeParetos = paretoMap.get(i);
            if (nodeParetos != null) {
                for(int d = 1; d <= maxDrones; d++) {
                    ParetoFront pf = nodeParetos.get(d);
                    if (pf != null && !pf.nonDominatedSchedules.isEmpty()) {
                        System.out.println("  Drones: " + d + " | Optimal Schedules: " + pf.nonDominatedSchedules.size());
                        for(var schedule : pf.nonDominatedSchedules) {
                            System.out.printf("    %s -> %.2f\n", schedule.sequences, schedule.makespan);
                            // System.out.println("    " + schedule.sequences + " -> " + schedule.makespan);
                        }
                    }
                }
            }
        }

        // List<Integer> first_nb = neibourhoodMap.get(1);
        // for(int i = 0; i < first_nb.size(); i++) {
        //     System.out.print(first_nb.get(i)+1 + " ");
        // }

        // first_nb.remove(3);
        // first_nb.remove(3);

        // List<List<Integer>> subsets = OneBasedDroneSchedule.getOneBasedSequences(first_nb);
        // for(var subset : subsets) {
        //     System.out.println(subset);
        // }

        // ===============================================
        // ----------------             ------------------
        // ===============================================

    }

    
}
