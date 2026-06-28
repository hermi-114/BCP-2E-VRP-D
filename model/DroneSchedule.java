package model;

import java.util.ArrayList;
import java.util.List;

public class DroneSchedule {
    public int droneId;
    public List<Integer> customerServed;
    public int makespan;

    public DroneSchedule(int droneId) {
        this.droneId = droneId;
        this.customerServed = new ArrayList<>();
        this.makespan = 0;
    }

    public void addCustomer(int customerId, int serviceTime) {
        customerServed.add(customerId);
        makespan += serviceTime;
    }

    public List<Integer> getDroneSchedule(int id) {
        return customerServed;
    }

    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        int n = customerServed.size();
        for(int i = 0; i < n-1; i++) {
            sb.append(customerServed.get(i)).append(" - ");
        }
        sb.append(customerServed.get(n-1)).append(")");

        return sb.toString();
    }
    @Override
    public String toString() {
        return getInfo();
    }
}
