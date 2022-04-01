package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent{

	public NewInterCityRoadEvent (int time,String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time,id,srcJun,destJunc,length,co2Limit,maxSpeed,weather);
	}

	@Override
	public Road newRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,Weather weather) {
		return new InterCityRoad(id,srcJunc,destJunc,maxSpeed,contLimit,length,weather);
	}

	public String toString(){
		return "New InterCity Road '" + _time +"'";
	}

}
