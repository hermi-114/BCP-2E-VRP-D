package algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.DroneSchedule;

public class ParetoFront {
    public List<DroneSchedule> nonDominatedSchedules;

    public ParetoFront() {
        nonDominatedSchedules = new ArrayList<>();
    }

    // try add new schedule
    // check if this new schedule got dominated by any existed schedule
    // check if this new schdule dominates any existed schedule
    public void tryAddSchedule(DroneSchedule newSchedule) {

        if(newSchedule == null) {
            return;
        }

        if(newSchedule.maxStartingTime < 0 || newSchedule.maxStartingTime >= 1e5) {
            return;
        }

        Iterator<DroneSchedule> it = nonDominatedSchedules.iterator();
        while(it.hasNext()) {
            DroneSchedule schedule = it.next();

            if(schedule.dominates(newSchedule)) {
                return;
            }

            if(newSchedule.dominates(schedule)) {
                it.remove();
            }
        }

        nonDominatedSchedules.add(newSchedule); // if new schedule survives after loop

    }

    
}
