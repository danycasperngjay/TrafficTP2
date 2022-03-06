package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;



public class SetContClassEventBuilder extends Builder<Event> {

	public SetContClassEventBuilder() {
		super("set_cont_class");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
			int time = data.getInt("time");
			
			List<Pair<String,Integer>> contClass = new ArrayList<>();

			JSONArray info = data.getJSONArray("info");
			for (int i = 0; i < info.length();i++)
			{
				String vehicle = info.getJSONObject(i).getString("vehicle");
				int cclass = info.getJSONObject(i).getInt("class");
				Pair<String,Integer> pair = new Pair<String,Integer> (vehicle,cclass);
				contClass.add(pair);
			}
			return new NewSetContClassEvent(time, contClass);
	}

}
