package simulator.model;

import org.json.JSONObject;
import simulator.misc.SortedArrayList;

import java.util.ArrayList;
import java.util.List;

public class TrafficSimulator implements Observable<TrafficSimObserver> {
	
	RoadMap _roadMap;
	List<Event> _events;
	int _time;

	List<TrafficSimObserver> obs; // observers

	public TrafficSimulator() {
		_roadMap = new RoadMap();
		_events = new SortedArrayList<>();
		_time = 0;
		obs = new ArrayList<TrafficSimObserver>();
	}

	public void addEvent(Event e) {
		if(e.getTime() <= _time) {
			
			for(TrafficSimObserver o: obs) {
				o.onError("We cannot add events for the past ! !");
			}
			throw new IllegalArgumentException ("We cannot add events for the past!");
		}
		
			_events.add(e);
			
			for(TrafficSimObserver o: obs) {
			o.onEventAdded(_roadMap, _events, e, _time);
			}
			
// Keep array sorted
//			for (Event o : _events) {
//				if (e.compareTo(o) == -1)
//					_events.add(_events.indexOf(o),e);
//			}
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
		try {
		for (TrafficSimObserver o: obs)
		{
			o.onAdvanceStart(_roadMap, _events, _time);
		}
		
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
		catch(Exception e) {
			for(TrafficSimObserver o : obs){
				o.onError(e.getMessage());
			}
		}
	}
	
	public void reset() {
		_events.clear();
		_roadMap.reset();
		_time = 0;
		for(TrafficSimObserver o: obs) {
		o.onReset(_roadMap, _events, _time);
		}
	}
	
	public JSONObject report() {
		
		 JSONObject jo = new JSONObject();
		
		 jo.put("time", _time);
		 
		 jo.put("state", _roadMap.report());
		    
		 return jo;
	}

	
	@Override
	public void addObserver(TrafficSimObserver o){
		obs.add(o);
		for(TrafficSimObserver ob: obs) {
		ob.onRegister(_roadMap, _events, _time); 
		}
	}
	
	@Override
	public void removeObserver(TrafficSimObserver o) {
		obs.remove(o);		
	}
}
