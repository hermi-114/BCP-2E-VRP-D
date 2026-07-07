package model;

import java.util.List;


public class DroneSchedule {
    public List<List<Integer>> sequences;
    public double makespan;
    public double maxStartingTime;

    
    public DroneSchedule(List<List<Integer>> sequences, double makespan, double maxStartingTime) {
        this.sequences = sequences;
        this.makespan = makespan;
        this.maxStartingTime = maxStartingTime;
    }

    public boolean dominates(DroneSchedule other) {
        boolean betterOrEqual = (this.makespan <= other.makespan) && 
                                (this.maxStartingTime >= other.maxStartingTime);
                                
        boolean strictlyBetter = (this.makespan < other.makespan) || 
                                 (this.maxStartingTime > other.maxStartingTime);
                                 
        return betterOrEqual && strictlyBetter;
    }

    public boolean equals(DroneSchedule other) {
        return  this.makespan == other.makespan &&
                this.maxStartingTime == other.maxStartingTime;
    }
}
