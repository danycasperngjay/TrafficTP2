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
	JLabel newEvents = new JLabel();
	JLabel time = new JLabel();
	
	public StatusBar(Controller ctrl) {
		this._ctrl = ctrl;
		ctrl.addObserver(this);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		init();
	}

	private void init() {
		time.setText("Time: " + _currentTime);
		this.add(time);
		this.add(Box.createHorizontalStrut(20));
		newEvents.setText("Welcome to Traffic Simulator ! :)");
		this.add(newEvents);
		
	}

	@Override
	public void onAdvanceEnd(RoadMap roadMap, List<Event> events, int time) {
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this._currentTime = time;
		this.time.setText("Time: " + _currentTime);
		for (Event e : events)
		{
			if (e.getTime()== this._currentTime)
				newEvents.setText("Last Event happened:" + e.toString());
		}
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		newEvents.setText("Event added: " + e.toString());	 
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
