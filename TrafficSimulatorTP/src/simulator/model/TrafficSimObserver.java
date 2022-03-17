package simulator.model;

import java.util.List;

public interface TrafficSimObserver {

    public  void onAdvanceEnd(RoadMap roadMap, List<Event> events, int time);

}
