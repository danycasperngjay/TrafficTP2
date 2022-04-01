package simulator.model;
import simulator.misc.Pair;

import java.util.List;

public class SetWeatherEvent extends Event {
	
	private List<Pair<String,Weather>> weatherEvent;
	

	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		super(time);
		if (ws == null)
			throw new IllegalArgumentException ("Ws is null");
		this.weatherEvent = ws;
	}
	
	@Override
	void execute(RoadMap map) {
		for (Pair<String, Weather> x : weatherEvent) {
			if (map.getRoad(x.getFirst()) == null)
				throw new IllegalArgumentException("Couldn't Find Road");
			else
				map.getRoad(x.getFirst()).setWeather(x.getSecond());
		}
	}

	public String toString(){
		return "New Road Map '" + _time + "'";
	}

}
