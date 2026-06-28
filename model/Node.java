package model;

public class Node {
    public int id;
    public double x;
    public double y;
    public double demand;
    // public double tw_a;
    public double deadline;
    public double truckServiceTime;
    public double droneServiceTime;
    public boolean isDroneAllowed;

    public Node(int id, double x, double y, double demand, double  deadline, double truckServiceTime, double droneServiceTime, boolean isDroneAllowed) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.demand = demand;
        // this.tw_a = tw_a;
        this.deadline = deadline;
        this.isDroneAllowed = false;
        this.truckServiceTime = truckServiceTime;
        this.droneServiceTime = droneServiceTime;
        this.isDroneAllowed = isDroneAllowed;
    }

    public String getInfo() {
        return String.format("Node[id=%d, coor=(%.1f;%.1f), demand=%.1f, deadline=%.2f, truckServiceTime=%.0f, droneServiceTime=%.0f, isDroneAllowed=%b]",
        id,
        x,
        y,
        demand,
        deadline,
        truckServiceTime,
        droneServiceTime,
        isDroneAllowed);
    }

    @Override
    public String toString() {
        return getInfo();
    }

}


