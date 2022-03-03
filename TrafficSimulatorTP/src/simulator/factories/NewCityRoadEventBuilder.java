package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends Builder<Event> {

	NewCityRoadEventBuilder() {
		super("new_city_road");
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
		
		return new NewCityRoadEvent(time,id,srcJun,destJunc,length,co2Limit,maxSpeed, weather);
	}

}
