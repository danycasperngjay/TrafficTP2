package simulator.model;
import simulator.misc.Pair;

import java.util.List;

public class SetContClassEvent extends Event {
	
	private List<Pair<String,Integer>> contClassEvent;
	
	public SetContClassEvent(int time,List<Pair<String,Integer>> cs) {
		super(time);
		if (cs == null)
			throw new IllegalArgumentException ("Cs is null");
		this.contClassEvent = cs;
	}

	@Override
	void execute(RoadMap map) {
		 for (Pair<String,Integer> x : contClassEvent) {
			 if (map.getVehicle(x.getFirst()) == null)
				 throw new IllegalArgumentException ("Couldn't Find Vehicle");
			 else
				 map.getVehicle(x.getFirst()).setContClass(x.getSecond());
		 }
	}

	public String toString(){
		return "New Contamination Class '" + _time + "'";
	}

} 
