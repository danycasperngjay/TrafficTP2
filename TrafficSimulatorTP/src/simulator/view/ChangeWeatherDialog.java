package simulator.view;

import simulator.control.Controller;
import simulator.model.Road;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChangeWeatherDialog extends JDialog {

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
        Frame f = new Frame();
        JDialog changeWeather = new JDialog(f, "Change Road Weather");

        //instructions
        JLabel instructions = new JLabel("Schedule an event to change the weather of a road " +
                "after a given number of simulation ticks from now.");
        changeWeather.add(instructions, BorderLayout.PAGE_START);

        //select road
        JLabel road = new JLabel("Road:");
        changeWeather.add(road, BorderLayout.LINE_START);
        List<String> roadChoices = new ArrayList<>();
        for(Road r : _ctrl.getSimulator().getRoadMap().getRoads()){
            roadChoices.add(r.toString());
        }
        JComboBox comboRoad = new JComboBox((ComboBoxModel) roadChoices);
        comboRoad.setPreferredSize(new Dimension(60, 20));
        changeWeather.add(comboRoad, BorderLayout.LINE_START);

        //select a weather
        JLabel weather = new JLabel("Weather:");
        changeWeather.add(weather, BorderLayout.CENTER);
        String[] weatherChoices = {"SUNNY", "WINDY", "CLOUDY", "STORMY", "RAINY"};
        JComboBox<String> comboWeather= new JComboBox<String>(weatherChoices);
        comboWeather.setPreferredSize(new Dimension(60, 20));
        changeWeather.add(comboWeather, BorderLayout.CENTER);

        //ticks
        JLabel ticksLabel = new JLabel("Ticks: ");
        changeWeather.add(ticksLabel, BorderLayout.LINE_END);
        JSpinner ticksSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 10000, 1));
        ticksSpinner.setPreferredSize(new Dimension(80, 40));
        changeWeather.add(ticksSpinner, BorderLayout.LINE_END);

        //cancel and ok button
        int okCancelOption = JOptionPane.OK_CANCEL_OPTION;

    }

}
