package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent{

	public NewCityRoadEvent (int time,String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time,id,srcJun,destJunc,length,co2Limit,maxSpeed,weather);
	}

	@Override
	public Road newRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,Weather weather) {
		return new CityRoad(id,srcJunc,destJunc,maxSpeed,contLimit,length,weather);
	}
}
