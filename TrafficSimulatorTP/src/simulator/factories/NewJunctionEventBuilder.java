package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event>{

	Factory<LightSwitchingStrategy>lssFactory;
	Factory<DequeuingStrategy> dqsFactory;
	
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy>lssFactory, Factory<DequeuingStrategy> dqsFactory){
		super("new_junction");
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
		
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		
		LightSwitchingStrategy lsStrategy = lssFactory.createInstance(data.getJSONObject("ls_strategy"));
		DequeuingStrategy dqStrategy = dqsFactory.createInstance(data.getJSONObject("dq_strategy"));
		
		JSONArray coor = data.getJSONArray("coor");
		
		int x = coor.getInt(0);
		int y = coor.getInt(1);
		
		return new NewJunctionEvent(time,id, lsStrategy, dqStrategy, x, y);
	}

}
