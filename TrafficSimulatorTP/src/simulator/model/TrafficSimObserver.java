package simulator.model;

import java.util.List;

public interface TrafficSimObserver {

    public void onAdvanceEnd(RoadMap roadMap, List<Event> events, int time);
    public void onAdvanceStart(RoadMap map, List<Event> events, int time);
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time);
	public void onReset(RoadMap map, List<Event> events, int time);
	public void onRegister(RoadMap map, List<Event> events, int time);
	public void onError(String err);

}
