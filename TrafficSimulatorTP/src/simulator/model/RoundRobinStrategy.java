package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy {

    List<Road> roads; //List of incoming roads
    List<List<Vehicle>> qs; //the i-th queue (List<Vehicle> is a queue) corresponds to the i-th road in the list "roads"
    int currGreen; // the index (in list "roads") of the road with a green light (value -1 if all red)
    int lastSwitchingTime; //simulation time at which the light for the road currGreen was switched from red to green (if currGreen -1 then it is the last time all switched to red)
    int currTime; // the current simulation time

    RoundRobinStrategy(int timeSlot){

    }

    @Override
    public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
        return 0;
    }
}
