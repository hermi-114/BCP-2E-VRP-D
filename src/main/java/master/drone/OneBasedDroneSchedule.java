package src.main.java.master.drone;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import src.main.java.io.DataLoader;

public class OneBasedDroneSchedule {
    public static List<List<Integer>> getOneBasedSequences(List<Integer> sequence) {
        List<Integer> sorted = new ArrayList<>(sequence);
        int size = sequence.size();

        List<List<Integer>> subsets = Subset.precomputeSubsets(size);

        Collections.sort(sorted, Comparator.comparingDouble(c -> DataLoader.getNode(c).deadline));

        List<List<Integer>> res = new ArrayList<>();
        for(var subset : subsets) {
            List<Integer> subSequence = new ArrayList<>();
            for(var destIdx : subset) {
                subSequence.add(sorted.get(destIdx));
            }
            res.add(subSequence);
        }

        return res;
    }

}
