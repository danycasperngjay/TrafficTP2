package simulator.view;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ChangeWeatherDialog extends JDialog implements TrafficSimObserver {

    private static final long serialVersionUID = 1L;

    private Controller _ctrl;

    public ChangeWeatherDialog(Frame parent, Controller ctrl){
        super(parent, "Change Road Weather");
        _ctrl = ctrl;
        //position of the window
        setLocation(500, 500);
        setPreferredSize(new Dimension(1050, 200));
        setSize(getPreferredSize());
        init();
    }

    public void init(){

        String[] roadList = {};

        JPanel changeWeather = new JPanel();
        changeWeather.setLayout(new FlowLayout());
        changeWeather.setPreferredSize(new Dimension(500, 200));
        this.setContentPane(changeWeather);

        //instructions
        JLabel instructions = new JLabel("Schedule an event to change the weather of a road " +
                "after a given number of simulation ticks from now.");
        changeWeather.add(instructions, BorderLayout.PAGE_START);

        //select road
        JLabel road = new JLabel("Road:");
        changeWeather.add(road, BorderLayout.LINE_START);
        ArrayList<String> roadChoices = new ArrayList<>();
        for (Road r : _ctrl.getSimulator().getRoadMap().getRoads()) {
            roadChoices.add(r.toString());
        }
        roadList = roadChoices.toArray(new String[0]);
        JComboBox<String> comboRoad = new JComboBox<String> (roadList);
        comboRoad.setPreferredSize(new Dimension(100, 40));
        changeWeather.add(comboRoad, BorderLayout.LINE_START);

        //select a weather
        JLabel weather = new JLabel("Weather:");
        changeWeather.add(weather, BorderLayout.CENTER);
        String[] weatherChoices = {"SUNNY", "WINDY", "CLOUDY", "STORMY", "RAINY"};
        JComboBox<String> comboWeather = new JComboBox<String>(weatherChoices);
        comboWeather.setPreferredSize(new Dimension(100, 40));
        changeWeather.add(comboWeather, BorderLayout.CENTER);

        //ticks
        JLabel ticksLabel = new JLabel("Ticks: ");
        changeWeather.add(ticksLabel, BorderLayout.LINE_END);
        JSpinner ticksSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 10000, 1));
        ticksSpinner.setPreferredSize(new Dimension(80, 40));
        changeWeather.add(ticksSpinner, BorderLayout.LINE_END);
        
        changeWeather.add(Box.createVerticalStrut(20));
        //ok and cancel buttons
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeWeather.setVisible(false);
                ChangeWeatherDialog.this.setVisible(false);
            }
        });
        changeWeather.add(cancel, BorderLayout.PAGE_END);
       
        JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Road selectedRoad = (Road) comboRoad.getSelectedItem();
                Weather selectedWeather = (Weather) comboWeather.getSelectedItem();
                for(Road r : _ctrl.getSimulator().getRoadMap().getRoads()){
                    if(r == selectedRoad ){
                    	List<Pair<String,Weather>> weatherEvent = new ArrayList<>();
                    	Pair<String,Weather> weatherPair = new Pair<String, Weather> (r.toString(),selectedWeather);
                    	weatherEvent.add(weatherPair);
                    	SetWeatherEvent setw = new SetWeatherEvent(_ctrl.getSimulator().getTime() + ticksSpinner.getComponentCount(), weatherEvent);
                    	_ctrl.getSimulator().addEvent(setw);
                    }
                }
            changeWeather.setVisible(false);
            ChangeWeatherDialog.this.setVisible(false);
            }
        });
        changeWeather.add(ok, BorderLayout.PAGE_END);

    }


    @Override
    public void onAdvanceEnd(RoadMap roadMap, List<Event> events, int time) {

    	
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {

    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onError(String err) {

    }
}
