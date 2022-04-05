package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	
	Controller ctrl;
	
	public ControlPanel(Controller _ctrl) {
    	super();
		_ctrl = ctrl;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(new LineBorder(Color.GREEN));
    	init();
    	
	}
	
	private void init() {
		loadEvents();
    	changeContClass();
    	changeWeather();
    	run();
    	stop();
    	ticks();
	}

	private void loadEvents() {
	    Icon icon = new ImageIcon("resources/icons/open.png");
		JButton loadB = new JButton(icon);
		this.add(loadB); 
	}
	
	private void changeContClass() {
	    Icon icon = new ImageIcon("resources/icons/co2class.png");
		JButton co2B = new JButton(icon);
		this.add(co2B);
	}

	private void changeWeather() {
	    Icon icon = new ImageIcon("resources/icons/weather.png");
		JButton weatherB = new JButton(icon);
		this.add(weatherB);
	}
	
	private void run() {
	    Icon icon = new ImageIcon("resources/icons/run.png");
		JButton runB = new JButton(icon);
		this.add(runB);
	}
	
	private void stop() {
	    Icon icon = new ImageIcon("resources/icons/stop.png");
		JButton stopB = new JButton(icon);
		this.add(stopB);
	}
	
	private void ticks() {
		 JButton load = new JButton("ticks");
		 this.add(load);
	}

	private void exit(){
		Icon icon = new ImageIcon("resources/icons/exit.png");
		JButton exitB = new JButton(icon);
		this.add(exitB);
	}
	
	@Override
	public void onAdvanceEnd(RoadMap roadMap, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
