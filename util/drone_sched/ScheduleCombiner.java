package util.drone_sched;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.DroneSchedule;

public class ScheduleCombiner {
    public static DroneSchedule combine(DroneSchedule s1, DroneSchedule s2) {
        if (s1 == null || s2 == null) {
            return null;
        }

        // 1. DISJOINT CHECK: Ensure drones do not visit the same customers
        Set<Integer> visited = new HashSet<>();
        
        // Track all customers visited by the first schedule
        for (List<Integer> seq : s1.sequences) {
            for (int customer : seq) {
                visited.add(customer);
            }
        }
        
        // Check if the second schedule attempts to visit any of those same customers
        for (List<Integer> seq : s2.sequences) {
            for (int customer : seq) {
                if (visited.contains(customer)) {
                    return null; // Overlap detected! Invalid combination.
                }
            }
        }

        // 2. ENVELOPE MATH: Combine the physical time constraints
        double combinedMakespan = Math.max(s1.makespan, s2.makespan);
        double combinedMaxStart = Math.min(s1.maxStartingTime, s2.maxStartingTime);

        // 3. SEQUENCE MERGE: Physically append the flight paths
        List<List<Integer>> combinedSequences = new ArrayList<>();
        
        // We must perform a deep-ish copy of the sequences to prevent memory reference bugs 
        // when these lists are evaluated later in the Branch-and-Bound tree.
        for (List<Integer> seq : s1.sequences) {
            combinedSequences.add(new ArrayList<>(seq));
        }
        for (List<Integer> seq : s2.sequences) {
            combinedSequences.add(new ArrayList<>(seq));
        }

        return new DroneSchedule(combinedSequences, combinedMakespan, combinedMaxStart);
    }

    
}
