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
    public void tryAddSchedule(DroneSchedule newschedule) {

        if(newschedule.maxStartingTime < 0) {
            return;
        }

        Iterator<DroneSchedule> it = nonDominatedSchedules.iterator();
        while(it.hasNext()) {
            DroneSchedule schedule = it.next();

            if(schedule.dominates(newschedule) || schedule.equals(newschedule)) {
                return;
            }

            if(newschedule.dominates(schedule)) {
                it.remove();
            }
        }

        // if new schedule survive after loop -> add to list
        nonDominatedSchedules.add(newschedule);
    }

    
}
