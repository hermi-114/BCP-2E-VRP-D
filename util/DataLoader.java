package util;

import algorithm.Constants;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Node;


public class DataLoader {
    
    public static List<Node> nodes;
    
    public static double[][] dist;
    public static Map<Integer, Node> nodeMap;
    public static int truckNum;
    public static int droneNum;
    public static int nodeNum;
    public static String instanceName;

    public static Node getNode(int id) {
        Node n = nodeMap.get(id);
        if (n == null) {
            throw new IllegalArgumentException("Customer not found: " + id);
        }
        return n;
    }


    public static void load(String path) {
        System.out.println("Starting read file");

        nodes = new ArrayList<>();
        nodeMap = new HashMap<>();

        int counter = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
    
            String line;
    
            while((line=br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                counter++;

                String[] attrs = line.trim().split("\\s+");

                int id = Integer.parseInt(attrs[0]);
                double xCoor = Double.parseDouble(attrs[1]);
                double yCoor = Double.parseDouble(attrs[2]);
                double demand = Double.parseDouble(attrs[3]);
                // double readyTime = Double.parseDouble(attrs[4]);
                double deadline = Double.parseDouble(attrs[5]);
                double truckServiceTime = Double.parseDouble(attrs[6]);
                double droneServiceTime = Math.floor(truckServiceTime / 2);
                boolean isDroneAllowed = true;

                Node node = new Node(id-1, xCoor, yCoor, demand, deadline, truckServiceTime, droneServiceTime, isDroneAllowed);

                nodes.add(node);
                nodeMap.put(id-1, node);

            }
            
            if(counter == 0) {
                System.err.println("counter = 0");
                return;
            }

            Constants.TOTAL_CUSTOMERS = counter - 1; // customers and 1 depot(id=0)

            br.close();
            System.out.println("Done read file");
            System.out.println();
            
        } catch(FileNotFoundException e) {
            System.err.println("FILE NOT FOUND: " + '"' + path + '"');
        } catch (Exception e) {
            System.err.println("LOAD DATA ERROR: " + e.getMessage());
        }



        // ====== EVALUATE DISTANCE MATRIX ======

        nodeNum = counter;

        dist = new double[nodeNum][nodeNum];
        for(int i = 0; i < counter; i++) {
            dist[i][i] = 1e6;
            Node c1 = nodes.get(i);
            for(int j = i + 1; j < counter; j++) {
                Node c2 = nodes.get(j);

                double dx = c1.x - c2.x;
                double dy = c1.y - c2.y;

                dist[i][j] = dist[j][i] = Math.sqrt(dx*dx + dy*dy);
            }
        }

        // for(int i = 0; i < counter; i++) {
        //     for(int j = 0; j < counter; j++) {
        //         System.out.print(dist[i][j] + " ");
        //     }
        //     System.out.println();
        // }


    }
}
