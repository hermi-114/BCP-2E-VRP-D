package model;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<Integer> truckJourney;
    public List<List<DroneSchedule>> droneJourney;
    public boolean isFeasible;

    public Solution() {
        truckJourney = new ArrayList<>();
        droneJourney = new ArrayList<>();
        isFeasible = false;
    }


}
