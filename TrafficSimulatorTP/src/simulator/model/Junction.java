package simulator.model;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject {

	List<Road> _inRoads;
    List<List<Vehicle>> _queues; // the i-th queue is of the i-th road in _inRoads
    Map<Road, List<Vehicle>> _queueByRoad; // this is just for fast searching of queues
    Map<Junction, Road> _outRoadByJunction; // to tell how to go to a junction
    int _greenLightIndex;   // the index of the road (in _inRoads) that has green light (-1 all red)
    int _lastSwitchingTime; // the last time we switched the green light from one road to another
    LightSwitchingStrategy _lss;
    DequeuingStrategy _dqs;
    
    Junction(String id) {
		super(id);
		
	}

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

    //Adds the vehicle to the queue of that road
    public void enter(Vehicle v){
    	Road r = v.getRoad();
    	_queueByRoad.get(r).add(v);
    }

    public Road roadTo(Junction j){
        return this._outRoadByJunction.get(j);
    }

    public void advance(int time){
        if (_greenLightIndex != -1) {
        	List<Vehicle> q = _queueByRoad.get(_inRoads.get(_greenLightIndex));
        	List<Vehicle> l = _dqs.dequeue(q);
        	
        	for (Vehicle v : l) {
   	            try {
					v.moveToNextRoad();
					q.remove(v);
				} catch (Exception e) {
					e.printStackTrace();
				} 
   		  	}
        }
        int newGreen = _lss.chooseNextGreen(_inRoads, _queues, _greenLightIndex, _lastSwitchingTime, time);
        if (newGreen != _greenLightIndex)
        {
        	_greenLightIndex = newGreen;
        	_lastSwitchingTime = time;
        }
    }

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}



}
