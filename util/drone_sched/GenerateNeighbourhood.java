package util.drone_sched;
import algorithm.Constants;
import algorithm.Constraint;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.DataLoader;


public class GenerateNeighbourhood {
    public static Map<Integer, List<Integer>> generateNeighbourhood() {
        Map<Integer, List<Integer>> map = new HashMap<>();

        int cusNum = Constants.TOTAL_CUSTOMERS;
        for(int i = 1; i < cusNum + 1; i++) {
            final int f = i;
            List<Integer> neighbourhood = new ArrayList<>();

            for(int j = 1; j < cusNum + 1; j++) {
                
                if(i == j) continue;

                double demand = DataLoader.getNode(j).demand;

                if(Constraint.droneEnergy_constraint(i, j, demand)) {
                    neighbourhood.add(j);
                }
            }

            neighbourhood.sort(Comparator.comparingDouble(c -> DataLoader.dist[f][c]));
            map.put(i, neighbourhood);
        }

        // System.out.println(map);

        return map;

    }

}
