package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

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
		
		boolean srcJunctionexists = false;
		boolean destJunctionexists = false;
		for (Junction j : this.listJunctions)
		{
			if (j == r.getSrc())
				srcJunctionexists = true;
			if (j == r.getDest())
				destJunctionexists = true;
		}
		if(!srcJunctionexists)
			throw new IllegalArgumentException ("Source Junction not in List");
		if(!destJunctionexists)
			throw new IllegalArgumentException ("Destination Junction not in List");
		
		this.listRoads.add(r);
		//modify road map
	}
	
	void addJunction(Junction j) {
		for (Junction x : this.listJunctions) {
			if (x.getId() == j.getId())
				throw new IllegalArgumentException ("Cannot add junction, it has the same ID");
		}
		
		this.listJunctions.add(j);
		//modify junction map
	}
	
	
	
	public Junction getJunction(String id) {
		for (Junction x : this.listJunctions) {
			if (x.getId() == id)
				return x;
		}	
		return null;
	}
	
	public Road getRoad(String id) {
		for (Road x : this.listRoads) {
			if (x.getId() == id)
				return x;
		}	
		return null;
	}
	
	public Vehicle getVehicle(String id) {
		for (Vehicle x : this.listVehicles) {
			if (x.getId() == id)
				return x;
		}	
		return null;		
	}
	
	public List<Junction> getJunctions(){
		return listJunctions;
	}

	public List<Road> getRoads(){
		return listRoads;
	}
	
	public List<Vehicle> getVehicles(){
		return listVehicles;
	}
	
	void reset() {
		listVehicles.clear();
		listRoads.clear();
		listJunctions.clear();
		
		vehiclesMap.clear();
		roadsMap.clear();
		junctionsMap.clear();
	}
	
	
	public JSONObject report() {
		
		JSONObject jo = new JSONObject();
		
		
		return jo;
	}
}
