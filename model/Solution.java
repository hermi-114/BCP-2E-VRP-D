package model;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static List<Integer> truckJourney;
    public static List<List<DroneSchedule>> droneJourney;

    public Solution() {
        truckJourney = new ArrayList<>();
        droneJourney = new ArrayList<>();
    }


    // ====== EXAMPLE OF A SOLUTION ======
    // 0[()] - 2[(1 - 3)] - 4[(10)(11)] - 0[()] - 6[(5)] - 8[()] - 0[()] - 9[(7)] - 0[()] - 

    public String getInfo() {
        StringBuilder sb = new StringBuilder();

        for(int i : truckJourney) {
            sb.append(i);

            List<DroneSchedule> schedules = droneJourney.get(i);
            sb.append("[");
            for(var schedule : schedules) {
                sb.append(schedule.getInfo());
            }
            sb.append("] - ");

        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
