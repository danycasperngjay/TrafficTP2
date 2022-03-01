package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoadMap {
	
	private List<Vehicle> listVehicles;
	private List<Road> listRoads;
	private List<Junction> listJunctions;

	private Map<String, Vehicle> vehiclesMap;
	private Map<String, Road> roadsMap;
	private Map<String, Junction> junctionsMap;
	
	RoadMap() {
		this.listVehicles = new ArrayList<Vehicle>();
		this.listRoads = new ArrayList<Road>();
		this.listJunctions = new ArrayList<Junction>();
		
		this.vehiclesMap = new HashMap<String, Vehicle>();
		this.roadsMap = new HashMap<String, Road>();
		this.junctionsMap = new HashMap<String, Junction>();
		
	}

	void addVehicle(Vehicle v) {
		
		for (Vehicle x : this.listVehicles) {
			if (x.getId() == v.getId())
				throw new IllegalArgumentException ("Cannot add vehicle, it has the same ID");
		}
		
		boolean connection = false;
		for (int i = 0; i < v.itinerary.size(); i++)
		{
			for (int j = 0; j < v.itinerary.get(i + 1)._inRoads.size(); j++) {
				//         first junction         road goto new junction          equal    any of the nextjunction   road in
				if (v.itinerary.get(i)._outRoadByJunction.get(v.itinerary.get(i + 1)) == v.itinerary.get(i + 1)._inRoads.get(j) )
					connection = true;
			}
		}
		if (!connection)
			throw new IllegalArgumentException ("Itinerary non valid, roads not connected");
		
		this.listVehicles.add(v);
		//modify vehicle map
	}
	
	void addRoad(Road r) {
		for (Road x : this.listRoads) {
			if (x.getId() == r.getId())
				throw new IllegalArgumentException ("Cannot add road, it has the same ID");
		}
		
		this.listRoads.add(r);
	}
	

}
