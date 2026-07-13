package model;

import algorithm.Constants;
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
        if  (   this.makespan == other.makespan
            &&  this.maxStartingTime == other.maxStartingTime
            &&  this.sequences.size() == other.sequences.size()
        )
                return false;

        double epsilon = Constants.EPSILON;
        return (this.makespan - other.makespan <= epsilon) 
                && (this.maxStartingTime - other.maxStartingTime >= epsilon) 
                && this.sequences.size() >= other.sequences.size(); 
    }

    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("DroneSchedule [");

        sb.append("numDrones: ").append(sequences.size())
        .append(" | makespan: ").append(String.format("%7.2f", makespan))
        .append(" | maxStartingTime: ").append(String.format("%7.2f", maxStartingTime));

        sb.append("]");
        return sb.toString();
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
