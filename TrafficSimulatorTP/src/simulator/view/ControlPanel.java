package simulator.view;


import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.*;
import simulator.model.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
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

	private RoadMap rm;
	private int t; //time

	
	public ControlPanel(Controller _ctrl) {
    	super();
		ctrl = _ctrl;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
    	init();
		ctrl.addObserver(this);
	}
	
	private void init() {
		setLayout(new BorderLayout());
		this.add(toolBar);
		loadEvents();
    	changeContClass();
    	changeWeather();
    	run();
    	stopB();
    	ticks();
    	toolBar.add(Box.createGlue());
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

				changeCO2();

			}
		});

		toolBar.add(co2B);
	}

	//created new method because we can't use Swint.getWindowAncester in the actionlistener
	protected void changeCO2(){
		int state = 0;
		ChangeCO2ClassDialog changeCO2 = new ChangeCO2ClassDialog((Frame) SwingUtilities.getWindowAncestor(this), ctrl);

		state = changeCO2.start(rm);
		System.out.println(state);
		if(state != 0){
			List<Pair<String, Integer>> c = new ArrayList<>();
			c.add(new Pair<String, Integer>(changeCO2.getVehicle().getId(), changeCO2.getCO2Class()));
			try{
				ctrl.addEvent(new SetContClassEvent(t+changeCO2.getTicks(), c));
			} catch(Exception e){
				JOptionPane.showMessageDialog((Frame) SwingUtilities.getWindowAncestor(this), "An error occured while changing the co2(" + e + ")");
			}
		}

	}

	private void changeWeather() {
	    Icon icon = new ImageIcon("resources/icons/weather.png");
		weatherB.setIcon(icon);
		weatherB.setToolTipText("Change the weather of a road");
		
		weatherB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeW();
			}
		});

		toolBar.add(weatherB);
		toolBar.addSeparator();
	}

	protected void changeW(){
		int state = 0;
		ChangeWeatherDialog changeWeatherDialog = new ChangeWeatherDialog((Frame) SwingUtilities.getWindowAncestor(this), ctrl);

		state = changeWeatherDialog.start(rm);
		if(state != 0){
			List<Pair<String, Weather>> w = new ArrayList<>();
			w.add(new Pair<String, Weather>(changeWeatherDialog.getRoad().getId(), changeWeatherDialog.getWeather()));
			try{
				ctrl.addEvent(new SetWeatherEvent(t+changeWeatherDialog.getTicks(), w));
			} catch(Exception e){
				JOptionPane.showMessageDialog((Frame) SwingUtilities.getWindowAncestor(this), "An error occured when we change teh weather (" + e + ")");
			}
		}
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
		 toolBar.add(ticksLabel);
		 ticksSpinner.setPreferredSize(new Dimension(80, 40));
		 ticksSpinner.setMaximumSize(new Dimension(80, 40));
		 toolBar.add(ticksSpinner);

	}

	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				ctrl.run(1);
			} catch (Exception e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JOptionPane.showMessageDialog(null, "An error occurs");
					}
				});
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
		toolBar.add(exitB);
	}
	
	@Override
	public void onAdvanceEnd(RoadMap roadMap, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				rm = roadMap;
				t = time;
			}
		});
		
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				rm = map;
				t = time;
			}
		});
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				rm = map;
				t = time;
			}
		});
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				rm = map;
				t = time;
			}
		});
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				rm = map;
				t = time;
			}
		});
		
	}

	@Override
	public void onError(String err) {
		
	}

}
