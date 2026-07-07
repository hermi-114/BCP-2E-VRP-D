
import algorithm.Constants;
import algorithm.ParetoFront;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.DroneSchedule;
import util.DataLoader;
import util.drone_sched.GenerateNeighbourhood;
import util.drone_sched.OneBasedDroneSchedule;

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
        ParetoFront[] paretoFront = new ParetoFront[maxDrones];

        for(int i = 0; i < maxDrones; i++) {
            paretoFront[i] = new ParetoFront();
        }

        for(int node = 1; node < customerSize; node++) {

            List<Integer> neighbours = neibourhoodMap.get(node);
            List<List<Integer>> baseCase = OneBasedDroneSchedule.getOneBasedSequences(neighbours); // get every subsets K in neighbourhood
            
            List<DroneSchedule> schedules = new ArrayList<>();
            
            for(List<Integer> subSequence : baseCase) {
                double makespan = 0;
                double maxStartingTime = 1e6;

                for(int dest : subSequence) {
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
                sequences.add(subSequence);
                schedules.add(new DroneSchedule(sequences, makespan, maxStartingTime));
                
            }

            

            for(var schedule : schedules) {
                paretoFront[0].tryAddSchedule(schedule);
            }



            // try {
            //     for(int i = 1; i < maxDrones; i++) {
            //     System.out.println(i);

            //     for(int j = 0; j <= i/2; j++) {
            //         ParetoFront p1 = paretoFront[j];
            //         ParetoFront p2 = paretoFront[i-j-1];

            //         List<DroneSchedule> s1s = p1.nonDominatedSchedules;
            //         List<DroneSchedule> s2s = p2.nonDominatedSchedules;

            //         for(DroneSchedule s1 : s1s) {
            //             for(var s2 : s2s) {
            //                 DroneSchedule combined = ScheduleCombiner.combine(s1, s2);
            //                 if(combined != null) {
            //                     paretoFront[i].tryAddSchedule(combined);
            //                 }
            //             }
            //         }

            //     }
            // }
            // } catch (OutOfMemoryError e) {
            //     System.err.println("OUT OF MEMORY ERROR: " + e.getMessage());
            // }
        }

        System.out.println("Starting print");

        for(var pareto : paretoFront) {
            for(var list : pareto.nonDominatedSchedules) {
                for(var l : list.sequences) {
                    System.out.print('{');
                    for(var ll : l) {
                        System.out.print(ll + ", ");
                    }
                    System.out.print("}, ");
                }
                System.out.println();
            }
            System.out.println();
        }

        // ===============================================
        // -----------------  ------------------
        // ===============================================

    }

    
}
