package util.drone_sched;

import java.util.ArrayList;
import java.util.List;

public class Subset {
    public static final int MAX_SUBSET_SIZE = 5;

    public static List<List<Integer>> precomputeSubsets(int size) {
        List<List<Integer>> subsets = new ArrayList<>();
        for(int i = 0; i < (1<<size); i++) {
            
            if (Integer.bitCount(i) > MAX_SUBSET_SIZE) {
                continue;
            }

            List<Integer> set = new ArrayList<>();

            for(int j = 0; j < size; j++) {
                if((i & (1 << j)) > 0) {
                    set.add(j);
                }
            }

            subsets.add(set);
        }

        return subsets;
    }
}
