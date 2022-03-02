package simulator.model;

public class NewRoadEvent extends Event {
	
	private Road r;
	private RoadMap map;

	public NewRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		r._id = id;
		r.sourceJunction = map.getJunction(srcJun);
		r.destinationJunction = map.getJunction(destJunc);
		r.length = length;
		r.contaminationAlarmLimit = co2Limit;
		r.maximumSpeed = maxSpeed;
		r.weatherConditions = weather;

	}
	
	@Override
	void execute(RoadMap map) {
		map.addRoad(r);	
	}
}
