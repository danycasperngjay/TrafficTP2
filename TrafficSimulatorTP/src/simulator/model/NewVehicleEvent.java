package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {
	
	private Vehicle v;
	private List <Junction> iti;
	private List <String> itinerary;
	private int maxSpeed,contClass;
	private String id;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		iti = new ArrayList<>();
		this.itinerary = itinerary;	
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
	}
	
	@Override
	void execute(RoadMap map) {
		for (String _id : this.itinerary) {
			iti.add(map.getJunction(_id));
		}
		v = new Vehicle(id, maxSpeed, contClass, iti);
		
		map.addVehicle(v);
		v.moveToNextRoad();
	}
}
