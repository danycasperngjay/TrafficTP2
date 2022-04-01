package simulator.model;

public abstract class NewRoadEvent extends Event {
	
	private String id, sourceJunction, destinationJunction;
	private int length, co2Limit, maxSpeed;
	private Weather weather;
	private Junction srcJunction,destJunction;


	public NewRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		
		this.id = id;
		this.sourceJunction = srcJun;
		this.destinationJunction = destJunc;
		this.length = length;
		this.co2Limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
	}
	
	@Override
	void execute(RoadMap map) {
		
		srcJunction = map.getJunction(sourceJunction);
		destJunction = map.getJunction(destinationJunction);
	
		map.addRoad(newRoad(this.id,srcJunction,destJunction,this.maxSpeed,this.co2Limit,this.length,this.weather));	
	}
	
	public abstract Road newRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather);

	public String toString(){
		return "New Road with Destination Junction '" + destJunction._id + "' and Source Junction '" + srcJunction._id + "'";
	}

}
