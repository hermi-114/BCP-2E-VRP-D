package util;

import algorithm.Constants;
import java.io.BufferedReader;
import java.io.File;
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

    public static boolean isDroneEligibleCustomer(int customerId) {
        return getNode(customerId).demand <= Constants.DRONE_MAX_PAYLOAD + 1e9;
    }

    public static void load(String path) {
        nodes = new ArrayList<>();
        nodeMap = new HashMap<>();


        instanceName = new File(path).getName();

        int counter = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(instanceName));
    
            String line;
    
            while((line=br.readLine()) != null) {

                counter++;

                String[] attrs = line.split("\\s+");
                int id = Integer.parseInt(attrs[0]);
                double xCoor = Double.parseDouble(attrs[1]);
                double yCoor = Double.parseDouble(attrs[2]);
                double demand = Double.parseDouble(attrs[3]);
                double readyTime = Double.parseDouble(attrs[4]);
                double deadline = Double.parseDouble(attrs[5]);
                double truckServiceTime = Double.parseDouble(attrs[6]);
                double droneServiceTime = Math.floor(truckServiceTime / 2);
                boolean isDroneAllowed = true;

                Node node = new Node(id, xCoor, yCoor, demand, deadline, truckServiceTime, droneServiceTime, isDroneAllowed);

                nodes.add(node);
                nodeMap.put(id, node);
            }

            br.close();
            
        } catch(FileNotFoundException e) {
            System.err.println("FILE NOT FOUND: " + '"' + instanceName + '"');
        } catch (Exception e) {
            System.err.println("LOAD DATA ERROR" + e.getMessage());
        }



        // ====== EVALUATE DISTANCE MATRIX ======

        nodeNum = counter;

        dist = new double[nodeNum][nodeNum];
        for(int i = 0; i < counter; i++) {
            Node c1 = nodes.get(i);
            double x1 = c1.x;
            double y1 = c1.y;
            for(int j = i + 1; j < counter; j++) {
                Node c2 = nodes.get(j);
                double x2 = c2.x;
                double y2 = c2.y;

                dist[i][j] = dist[j][i] = Math.sqrt( Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2) );
            }
        }


    }
}
