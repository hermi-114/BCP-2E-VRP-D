package src.main.java.master;

import com.gurobi.gurobi.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class RestrictedMasterProblem {
    public RestrictedMasterProblem() throws GRBException{}

    GRBEnv env;
    GRBModel model;
    Map<Integer, GRBVar> lambdaMap; // routes
    Map<Integer, GRBConstr> stMap; // rows / constraints
    GRBConstr truckSt;
    int numCustomer;
    int kV;

    public RestrictedMasterProblem(int kV, int numCustomer, List<Route> initialRoutes) throws GRBExeption {
        this.kV = kV;
        this.numCustomer = numCustomer;

        this.lambdaMap = new HashMap<>();
        this.stMap = new HashMap<>();

        try {
            this.env = new GRBEnv(true);
            this.env.set(GRB.IntParam.LogToConsole, 0);
            this.env.start();

            this.model = new GRBModel(env);
            this.model.set(GRB.StringAttr.ModelName, "RMP_LP_Relaxation");
        } catch(GRBException e) {
            throw new RuntimeException(e);
        }


        buildInitialModel(initialRoutes);

    }

    void buildInitialModel(List<Route> initialRoutes) {
        
        // =====
        try {
            for(Route route : initialRoutes) {
                GRBVar var = model.addVar(0, 1, route.cost, GRB.CONTINUOUS, "lambda " + route.id);
                lambdaMap.put(route.id, var);

                model.update();

                for(int i = 1; i <= numCustomer; i++) {
                    GRBLinExpr expr = new GRBLinExpr();
                    for(Route r : initialRoutes) {
                        if(r.sequence.contains(i)) {
                            expr.addTerm(1, lambdaMap.get(r.id));
                        }
                    }
                    GRBConstr st = model.addConstr(expr, GRB.GREATER_EQUAL, 1, "Customers_Covering_" + i);
                    stMap.put(i, st);
                }
            }

            GRBLinExpr truckExpr = new GRBLinExpr();
            for(Route r : initialRoutes) {
                truckExpr.addTerm(1, lambdaMap.get(r.id));
            }
            this.truckSt = model.addConstr(truckExpr, GRB.LESS_EQUAL, kV, "Truck_Fleet_Limit.");

            model.update();
        } catch(GRBException e) {
            throw new RuntimeException(e);
        }

    }
}
