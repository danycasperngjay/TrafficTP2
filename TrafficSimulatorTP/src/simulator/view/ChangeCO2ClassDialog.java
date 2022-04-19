package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class  ChangeCO2ClassDialog extends JDialog implements TrafficSimObserver {

    private static final long serialVersionUID = 1L;

    private Controller _ctrl;

    public ChangeCO2ClassDialog(Frame parent, Controller ctrl){
        super(parent, "Change CO2 Class");
        _ctrl = ctrl;


        //position of the window
        setLocation(400, 400);

        init();

    }

    public void init(){

        String[] vehiclesList = {};

        JPanel changeCO2 = new JPanel();
        changeCO2.setLayout(new FlowLayout());
        this.setContentPane(changeCO2);

        //instructions
        JLabel instructions = new JLabel("Schedule an event to change the CO2 class of a vehicle " +
                "after a given number of simulation ticks from now.");
        changeCO2.add(instructions, BorderLayout.PAGE_START);

        //select vehicle
        JLabel vehicle = new JLabel("Vehicle:");
        changeCO2.add(vehicle, BorderLayout.LINE_START);
        ArrayList<String> vehicleChoices = new ArrayList<>();
        for (Road r : _ctrl.getSimulator().getRoadMap().getRoads()) {
            vehicleChoices.add(r.toString());
        }
        vehiclesList = vehicleChoices.toArray(new String[0]);
        JComboBox<String> comboRoad = new JComboBox<String> (vehiclesList);
        comboRoad.setPreferredSize(new Dimension(60, 20));
        changeCO2.add(comboRoad, BorderLayout.LINE_START);

        //select a CO2 class
        JLabel co2Class = new JLabel("CO2 Class:");
        changeCO2.add(co2Class, BorderLayout.CENTER);
        String[] co2Choices = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        JComboBox<String> comboCO2 = new JComboBox<String>(co2Choices);
        co2Class.setPreferredSize(new Dimension(60, 20));
        changeCO2.add(comboCO2, BorderLayout.CENTER);

        //ticks
        JLabel ticksLabel = new JLabel("Ticks: ");
        changeCO2.add(ticksLabel, BorderLayout.LINE_END);
        JSpinner ticksSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 10000, 1));
        ticksSpinner.setPreferredSize(new Dimension(80, 40));
        changeCO2.add(ticksSpinner, BorderLayout.LINE_END);

        //ok and cancel buttons
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCO2.setVisible(false);
            }
        });
        changeCO2.add(cancel, BorderLayout.PAGE_END);

        JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //idk
            }
        });
        changeCO2.add(ok, BorderLayout.PAGE_END);

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
