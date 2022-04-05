package simulator.view;

import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import java.util.List;

public class StatusBar extends JPanel implements TrafficSimObserver {

	@Override
	public void onAdvanceEnd(RoadMap roadMap, List<Event> events, int time) {
		//TODO
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		//TODO
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		//TODO
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		//TODO
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		//TODO
	}

	@Override
	public void onError(String err) {
		//TODO
	}
}
