package simulator.model;

import java.util.List;

public class NewVehicleEvent extends Event {
	
	private RoadMap map;
	private Vehicle v;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		for (String _id : itinerary) {
			v.itinerary.add(map.getJunction(_id));
		}
		v._id = id;
		v.maximumSpeed = maxSpeed;
		v.contaminationClass = contClass;
	}
	
	@Override
	void execute(RoadMap map) {
		map.addVehicle(v);	
	}
}
