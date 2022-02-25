package simulator.model;

import java.util.List;
import java.util.Map;

public class Junction {

    List<Road> _inRoads;
    List<List<Vehicle>> _queues; // the i-th queue is of the i-th road in _inRoads
    Map<Road, List<Vehicle>> _queueByRoad; // this is just for fast searching of queues
    Map<Junction, Road> _outRoadByJunction; // to tell how to go to a junction
    int _greenLightIndex;   // the index of the road (in _inRoads) that has green light (-1 all red)
    int _lastSwitchingTime; // the last time we switched the green light from one road to another
    LightSwitchingStrategy _lss;
    DequeuingStrategy _dqs;

    public void addIncomingRoad(Road r) {
        if (r.getDest() == this) {
            this._inRoads.add(r);
            this._queues.add(r.getVehicles());
            this._queueByRoad.put(r, r.getVehicles());
        }
    }

    public void addOutgoingRoad(Road r){
        if(r.getSrc() == this){
            this._outRoadByJunction.put(this, r);
        }
    }

    //DONT KNOW
    public void enter(Vehicle v){
    }

    

}
