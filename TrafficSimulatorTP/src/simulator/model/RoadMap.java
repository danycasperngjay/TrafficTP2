package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
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
		
		if (vehiclesMap.containsKey(v.getId()))
			throw new IllegalArgumentException ("Cannot add vehicle, it has the same ID");
		else {	
			for (int i = 0; i < v.getItinerary().size() - 1; i++)
			{
				if((v.getItinerary().get(i).roadTo(v.getItinerary().get(i+1)) == null) && (!roadsMap.containsValue(v.getRoad())))
					throw new IllegalArgumentException ("Roads don't connect or road doesn't exist!!");
			}
			this.listVehicles.add(v);
			this.vehiclesMap.put(v.getId(), v);
		}
	}
	
	void addRoad(Road r) {
		
		if (roadsMap.containsKey(r.getId()))
			throw new IllegalArgumentException ("Cannot add road, it has the same ID");
		
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
		this.roadsMap.put(r.getId(), r);
	}
	
	void addJunction(Junction j) {

		if (junctionsMap.containsKey(j.getId()))
			throw new IllegalArgumentException ("Cannot add junction, it has the same ID");
		else {
			this.listJunctions.add(j);
			this.junctionsMap.put(j.getId(), j); 
		}
	}
	
	
	
	public Junction getJunction(String id) {
		return junctionsMap.get(id);
	}
	
	public Road getRoad(String id) {
		return roadsMap.get(id);
	}
	
	public Vehicle getVehicle(String id) {

		return vehiclesMap.get(id);		
	}
	
	public List<Junction> getJunctions(){
		return Collections.unmodifiableList(listJunctions);
	}

	public List<Road> getRoads(){
		return Collections.unmodifiableList(listRoads);
	}
	
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(listVehicles);
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
		
		JSONArray vehicles = new JSONArray();
		JSONArray roads = new JSONArray();
		JSONArray junctions = new JSONArray();
		
		for (Vehicle v  : listVehicles){
			vehicles.put(v.report());
		}
		
		for (Road r  : listRoads){
			roads.put(r.report());
		}
		
		for (Junction j  : listJunctions){
			junctions.put(j.report());
		}
		
		jo.put("junctions", junctions);
		jo.put("roads", roads);
		jo.put("vehicles", vehicles);
		
		return jo;
	}
}
