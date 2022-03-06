package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends Builder<Event> {

	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		String srcJun = data.getString("src");
		String destJunc = data.getString("dest");
		int length = data.getInt("length");
		int co2Limit = data.getInt("co2limit");
		int maxSpeed = data.getInt("maxspeed");
		String s = data.getString("weather");
		Weather weather = Weather.valueOf(s.toUpperCase());
		
		return new NewInterCityRoadEvent(time,id,srcJun,destJunc,length,co2Limit,maxSpeed, weather);
	}

}
