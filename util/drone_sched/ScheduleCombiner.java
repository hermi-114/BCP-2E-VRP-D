package util.drone_sched;

import algorithm.Constants;
import java.util.List;
import model.DroneSchedule;

public class ScheduleCombiner {
    public static DroneSchedule combine(DroneSchedule s1, DroneSchedule s2) {
        List<List<Integer>> seq = s1.sequences;
        seq.addAll(s2.sequences);
        
        boolean[] checkIfExist = new boolean[102];
        for(var s : seq) {
            for(var ss : s) {
                if(checkIfExist[ss]) {
                    return null;
                }
                checkIfExist[ss] = true;
            }
        }

        if(seq.size() > Constants.MAX_DRONES_PER_TRUCK) {
            return null;
        }
        
        double makespan = Math.max(s1.makespan, s2.makespan);
        double maxStartingTime = Math.min(s1.maxStartingTime, s2.maxStartingTime);

        return new DroneSchedule(seq, makespan, maxStartingTime);
    }

    
}
