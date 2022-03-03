package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;


public class SetContClassEventBuilder extends Builder<Event> {

	SetContClassEventBuilder() {
		super("set_cont_class");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
			int time = data.getInt("time");
			String vehicle = data.getString("vehicle");
			int cclass = data.getInt("class");
			Pair<String,Integer> pair = new Pair<String,Integer> (vehicle,cclass);
			List<Pair<String,Integer>> contClass = new ArrayList<>();
			contClass.add(pair);
			return new NewSetContClassEvent(time, contClass);
	}

}
