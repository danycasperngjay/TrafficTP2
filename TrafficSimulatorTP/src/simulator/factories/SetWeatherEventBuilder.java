package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	SetWeatherEventBuilder() {
		super("set_weather");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		
		List<Pair<String,Weather>> weatherEvent = new ArrayList<>();
		
		JSONArray info = data.getJSONArray("info");
		for (int i = 0; i < info.length();i++)
		{
			String road = info.getJSONObject(i).getString("road");
			String s = info.getJSONObject(i).getString("weather");
			Weather weather = Weather.valueOf(s.toUpperCase());
			Pair<String,Weather> weatherPair = new Pair<String, Weather> (road,weather);
			weatherEvent.add(weatherPair);
		}
		
		return new SetWeatherEvent(time, weatherEvent);
	}

}
