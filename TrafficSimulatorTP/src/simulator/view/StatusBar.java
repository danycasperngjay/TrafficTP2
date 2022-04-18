package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatusBar extends JPanel implements TrafficSimObserver {

	int _currentTime;
	Controller _ctrl;

	public StatusBar(Controller ctrl) {
		this._currentTime = 0; //get current time;
		this._ctrl = ctrl;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		init();
	}

	private void init() {
		showCurrentTime();
	}

	public void showCurrentTime(){
		System.out.println(this._currentTime);
	}

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
		JLabel newEvents = new JLabel();
		newEvents.setText("Event added:" + e.toString());
		this.add(newEvents); 
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		
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
