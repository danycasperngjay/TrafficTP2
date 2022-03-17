package simulator.model;

import org.json.JSONObject;
import simulator.misc.SortedArrayList;

import java.util.ArrayList;
import java.util.List;

public class TrafficSimulator {
	
	RoadMap _roadMap;
	List<Event> _events;
	int _time;

	List<TrafficSimObserver> obs;

	public TrafficSimulator() {
		_roadMap = new RoadMap();
		_events = new SortedArrayList<>();
		_time = 0;
		obs = new ArrayList<TrafficSimObserver>();
	}

	public void addObserver(TrafficSimObserver o){
		obs.add(o);
	}

	public void addEvent(Event e) {
		if(e.getTime() <= _time)
        	throw new IllegalArgumentException ("We cannot add events for the past!");
		
		// Keep array sorted
//				for (Event o : _events) {
//					if (e.compareTo(o) == -1)
//						_events.add(_events.indexOf(o),e);
//				}
			_events.add(e);
//        _events.sort((e1,e2) -> {
//        if(e1.getTime() < e2.getTime())
//        		return -1;
//        	else if (e1.getTime() > e2.getTime())
//        		return 1;
//        	else
//        		return 0;
//        });
	}
	
	public void advance() {
		_time++;
		
		while(!_events.isEmpty() && _events.get(0).getTime() == _time)
			_events.remove(0).execute(_roadMap);
		
		for (Junction j : _roadMap.getJunctions())
		{
			j.advance(_time);
		}
		
		for (Road r : _roadMap.getRoads())
		{
			r.advance(_time);
		}

		for(TrafficSimObserver o : obs){
			o.onAdvanceEnd(_roadMap, _events, _time);
		}
	}
	
	public void reset() {
		_events.clear();
		_roadMap.reset();
		_time = 0;
	}
	
	public JSONObject report() {
		
		 JSONObject jo = new JSONObject();
		
		 jo.put("time", _time);
		 
		 jo.put("state", _roadMap.report());
		    
		 return jo;
	}

}
