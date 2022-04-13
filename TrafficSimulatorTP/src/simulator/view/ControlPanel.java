package simulator.view;


import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
		exit();
	}

	private void loadEvents() {
	    Icon icon = new ImageIcon("resources/icons/open.png");
		JButton loadB = new JButton(icon);

		//FIX
		loadB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser eventsFile = new JFileChooser();

				if (e.getSource() == loadB) {
					//change null to parent component
					int loadDialog = eventsFile.showOpenDialog(null);
					if (loadDialog == JFileChooser.APPROVE_OPTION) {
						File fileSelected = eventsFile.getSelectedFile();
						ctrl.reset();
						//put input that reads file selected (GOOGLE) : needs to load events into the simulator
						//ctrl.loadEvents(fileSelected);
					} else {
						JOptionPane.showMessageDialog(null, "File does not exist.");
					}
				}
			}
		});
		this.add(loadB);
	}

	//fix
	private void changeContClass() {
	    Icon icon = new ImageIcon("resources/icons/co2class.png");
		JButton co2B = new JButton(icon);

		co2B.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String[] vehiclePossibilities = new String[ctrl.getSimulator().getRoadMap().getVehicles().size()];
				for(int i = 0; i < ctrl.getSimulator().getRoadMap().getVehicles().size(); i++){
					vehiclePossibilities[i] = "v" + Integer.toString(i+1);
				}

				String v = (String) JOptionPane.showInputDialog(null, "Vehicle",
						"Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now.\n"
						, JOptionPane.INFORMATION_MESSAGE, null, vehiclePossibilities, vehiclePossibilities[0]);

				int[] contClassPossibilities = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

				//String c = (String) JOptionPane.showInputDialog(null, );

				//change to choose number button
				JTextField ticks = new JTextField(4);
				ticks.setPreferredSize(new Dimension(100, 35));



				Object[] fields = {
					"Vehicle:", v,
						"CO2 Class:", contClassPossibilities,
						"Ticks:", ticks
				};

				//fix options : c bizarre
				//JOptionPane.showConfirmDialog(null, fields, "Schedule an event " +
				//		"to change the CO2 class of a vehicle after a given number of simulation ticks from now.\n",
				//		JOptionPane.OK_CANCEL_OPTION);
				//JOptionPane.showInputDialog(null, "Vehicle: ", vehiclePossibilities);
				//JOptionPane.showInputDialog(null, "CO2 Class: ", contClassPossibilities);
				//JOptionPane.showInputDialog("Ticks: ", ticks);
				//JOptionPane.showMessageDialog(null, JOptionPane.OK_CANCEL_OPTION);
			}
		});

		this.add(co2B);
	}

	//do action performed : same concept as changecontclass
	private void changeWeather() {
	    Icon icon = new ImageIcon("resources/icons/weather.png");
		JButton weatherB = new JButton(icon);
		this.add(weatherB);
	}

	//action performed not done
	private void run() {
	    Icon icon = new ImageIcon("resources/icons/run.png");
		JButton runB = new JButton(icon);
		this.add(runB);
	}

	//action performed not done
	private void stop() {
	    Icon icon = new ImageIcon("resources/icons/stop.png");
		JButton stopB = new JButton(icon);
		this.add(stopB);
	}

	// action performed not done + fix
	private void ticks() {
		 JLabel ticksLabel = new JLabel("Ticks: ");
		 this.add(ticksLabel);
		 //change to choose number button
		 JTextField ticks = new JTextField(4);
		 ticks.setPreferredSize(new Dimension(100, 35));
		 this.add(ticks);
		 //add increase decrease button
		//...


	}

	//Done : works
	private void exit(){
		Icon icon = new ImageIcon("resources/icons/exit.png");
		JButton exitB = new JButton(icon);
		exitB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(new JFrame(), "Are you sure you want to exit?", "Exit",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if(n == 0){
					System.exit(0);
				}
			}
		});
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
