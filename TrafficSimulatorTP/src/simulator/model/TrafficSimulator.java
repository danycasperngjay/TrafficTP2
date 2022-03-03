package simulator.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {
	
	RoadMap _roadMap;
	List<Event> _events;
	int _time;

	public TrafficSimulator() {
		_roadMap = new RoadMap();
		_events = new SortedArrayList<>();
		_time = 0;
	}
	
	public void addEvent(Event e) {
		if(e.getTime() <= _time)
        	throw new IllegalArgumentException ("We cannot add events for the past!");
		_events.add(e);
		// Keep array sorted
        _events.sort((e1,e2) -> {
        	if(e1.getTime() > e2.getTime())
        		return -1;
        	else if (e1.getTime() < e2.getTime())
        		return 1;
        	else
        		return 0;
        });
	}
	
	public void advance() {
		_time++;
		
		while(_events.get(0).getTime() == _time)
			_events.remove(0).execute(_roadMap);
		
		for (Junction j : _roadMap.getJunctions())
		{
			j.advance(_time);
		}
		
		for (Road r : _roadMap.getRoads())
		{
			r.advance(_time);
		}
	}
	
	public void reset() {
		_events.clear();
		_roadMap.reset();
		_time = 0;
	}
	
	//NOT FINISHED
	public JSONObject report() {
		
		 JSONObject jo = new JSONObject();
		
		 jo.put("time", _time);
		 
		 JSONObject state = new JSONObject();
		 
		 
			JSONArray vehicles = new JSONArray();
			JSONArray roads = new JSONArray();
			JSONArray junctions = new JSONArray();
			
			for (Vehicle v  : _roadMap.getVehicles()){
				vehicles.put(v.report());
			}
			
			for (Road r  : _roadMap.getRoads()){
				roads.put(r.report());
			}
			
			for (Junction j  : _roadMap.getJunctions()){
				junctions.put(j.report());
			}
			
			state.put("junctions",junctions);
			state.put("roads",roads);
		    state.put("vehicles",vehicles);
		 
		    jo.put("state", state);
		    
		 return jo;
	}

}
