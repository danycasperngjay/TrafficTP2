package simulator.factories;
import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

import org.json.JSONObject;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {

	RoundRobinStrategyBuilder() {
		super("round_robin_lss");
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int time = 1;
		
		if (data.has("timeslot"))
			time = data.getInt("timeslot");
		
		return new RoundRobinStrategy(time);
	}

}
