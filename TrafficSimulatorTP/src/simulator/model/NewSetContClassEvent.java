package simulator.model;
import java.util.List;
import simulator.misc.Pair;

public class NewSetContClassEvent extends Event {
	
	private List<Pair<String,Integer>> contClassEvent;
	
	public NewSetContClassEvent(int time,List<Pair<String,Integer>> cs) {
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
				 map.getVehicle(x.getFirst()).setContaminationClass(x.getSecond());
		 }
	}
} 
