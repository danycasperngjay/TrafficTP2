package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChangeWeatherDialog extends JDialog implements TrafficSimObserver {

    private static final long serialVersionUID = 1L;

    private Controller _ctrl;

    public ChangeWeatherDialog(Frame parent, Controller ctrl){
        super(parent, "Change Road Weather");
        _ctrl = ctrl;


        //position of the window
        setLocation(400, 400);

        init();

    }

    public void init(){

        String[] roadList = {};

        JPanel changeWeather = new JPanel();
        changeWeather.setLayout(new FlowLayout());
        this.setContentPane(changeWeather);

        //String title = "Change Road Weather";
        //Border border = BorderFactory.createTitledBorder(title);
        //changeWeather.setBorder(border);

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
        comboRoad.setPreferredSize(new Dimension(60, 20));
        changeWeather.add(comboRoad, BorderLayout.LINE_START);

        //select a weather
        JLabel weather = new JLabel("Weather:");
        changeWeather.add(weather, BorderLayout.CENTER);
        String[] weatherChoices = {"SUNNY", "WINDY", "CLOUDY", "STORMY", "RAINY"};
        JComboBox<String> comboWeather = new JComboBox<String>(weatherChoices);
        comboWeather.setPreferredSize(new Dimension(60, 20));
        changeWeather.add(comboWeather, BorderLayout.CENTER);

        //ticks
        JLabel ticksLabel = new JLabel("Ticks: ");
        changeWeather.add(ticksLabel, BorderLayout.LINE_END);
        JSpinner ticksSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 10000, 1));
        ticksSpinner.setPreferredSize(new Dimension(80, 40));
        changeWeather.add(ticksSpinner, BorderLayout.LINE_END);


        JDialog j = new JDialog();
        //cancel and ok button
        j.setDefaultCloseOperation(JOptionPane.OK_CANCEL_OPTION);
        j.setModal(true);
        j.add(changeWeather);
        j.setSize(200, 100);
        j.setTitle("Change Road Weather");
        j.setVisible(true);
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
