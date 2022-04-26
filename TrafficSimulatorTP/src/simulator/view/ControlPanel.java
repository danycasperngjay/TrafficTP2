package simulator.view;


import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;


public class ControlPanel extends JPanel implements TrafficSimObserver {

	
	private Controller ctrl;
	private boolean _stopped;
	
    private JSpinner ticksSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 10000, 1));
	private JButton loadB = new JButton();
	private JButton co2B = new JButton();
	private JButton weatherB = new JButton();
	private JButton runB = new JButton();
	private JButton stopB = new JButton();


	JToolBar toolBar = new JToolBar();

	
	public ControlPanel(Controller _ctrl) {
    	super();
		ctrl = _ctrl;
		ctrl.addObserver(this);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
    	init();
	}
	
	private void init() {
		this.add(toolBar);
		loadEvents();
    	changeContClass();
    	changeWeather();
    	run();
    	stopB();
    	ticks();
    	this.add(Box.createHorizontalStrut(1000));
		exit();
	}
	
	

	private void loadEvents() {
	    Icon icon = new ImageIcon("resources/icons/open.png");
		loadB.setIcon(icon);
		loadB.setToolTipText("Loads the file");

		loadB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser eventsFile = new JFileChooser("resources/examples");
					int loadDialog = eventsFile.showOpenDialog(loadB);
					if (loadDialog == JFileChooser.APPROVE_OPTION) {
						try {
						File fileSelected = eventsFile.getSelectedFile();
						InputStream input = new FileInputStream(fileSelected);
						ctrl.reset();
						ctrl.loadEvents(input);
						}
						catch (Exception ex) 
						{
							JOptionPane.showMessageDialog(null, "File does not exist.");
						}
					}
				}
		});
		
		toolBar.add(loadB);
		toolBar.addSeparator();
	}


	private void changeContClass() {
	    Icon icon = new ImageIcon("resources/icons/co2class.png");
	    co2B.setIcon(icon);
	    co2B.setToolTipText("Change the contamination class of a vehicle");

		co2B.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Frame f = new Frame();
				ChangeCO2ClassDialog c = new ChangeCO2ClassDialog(f, ctrl);

				c.setVisible(true);

				// Remove all this comments?
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

		toolBar.add(co2B);
	}

	//parent class?? fix that
	private void changeWeather() {
	    Icon icon = new ImageIcon("resources/icons/weather.png");
		weatherB.setIcon(icon);
		weatherB.setToolTipText("Change the weather of a road");
		
		weatherB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Frame parent = new Frame();
				ChangeWeatherDialog w = new ChangeWeatherDialog(parent, ctrl);
				w.setVisible(true);
			}
		});

		toolBar.add(weatherB);
		toolBar.addSeparator();
	}

	
	private void run() {
	    Icon icon = new ImageIcon("resources/icons/run.png");
		runB.setIcon(icon);
		runB.setToolTipText("Run the simulation");
		
		runB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_stopped = false;
				enableToolBar(_stopped);
				run_sim((int)ticksSpinner.getValue());
			}
			
		});
		toolBar.add(runB);
	}

	
	private void stopB() {
	    Icon icon = new ImageIcon("resources/icons/stop.png");
		stopB.setIcon(icon);
		stopB.setToolTipText("Stop the simulation");
		
		stopB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		toolBar.add(stopB);
		toolBar.addSeparator();
	}

	private void ticks() {
		 JLabel ticksLabel = new JLabel("Ticks: ");
		 this.add(ticksLabel);
		 ticksSpinner.setPreferredSize(new Dimension(80, 40));
		 this.add(ticksSpinner);

	}

	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				ctrl.run(1);
			} catch (Exception e) {
				// TODO show error message
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(() -> run_sim(n - 1));
		} else {
			enableToolBar(true);
			_stopped = true;
		}
	}

	private void enableToolBar(boolean b) {
			loadB.setEnabled(b);
			co2B.setEnabled(b);
			weatherB.setEnabled(b);
			runB.setEnabled(b);
	}

	private void stop() {
		_stopped = true;
	}

	 
	private void exit(){
		Icon icon = new ImageIcon("resources/icons/exit.png");
		JButton exitB = new JButton(icon);
		exitB.setToolTipText("Exit the simulation");
		
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
