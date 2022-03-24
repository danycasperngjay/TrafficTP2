package simulator.view;

import java.awt.Color;
import java.awt.FlowLayout;

import java.util.List;


import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import javax.swing.border.LineBorder;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

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
		
		//Icon icon = new ImageIcon("open.png");
		// Icon icon = new ImageIcon("open.png");
	    Icon icon = new ImageIcon("open.png");

		 JButton load = new JButton(icon);
		 load.setBounds(40,80,200,50);
		 load.setVisible(true);
		 this.add(load);
		 

		// load.setIcon(new ImageIcon(ControlPanel.class.getResource("/resources/icons/open.png")));
		 
	}
	
	private void changeContClass() {
		
		 JButton load = new JButton("Cont");
		 this.add(load);
	}

	private void changeWeather() {
		
		 JButton load = new JButton("weather");
		 this.add(load);
	}
	
	private void run() {
		
		 JButton load = new JButton("run");
		 this.add(load);
	}
	
	private void stop() {
		
		 JButton load = new JButton("stop");
		 this.add(load);
	}
	
	private void ticks() {
		
		 JButton load = new JButton("ticks");
		 this.add(load);
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
