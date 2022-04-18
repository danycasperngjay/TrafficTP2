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
        System.out.println("Schedule an event to change the weather of a road " +
                "after a given number of simulation ticks from now.");
        //position of the window
        setLocation(400, 400);

        init();
    }

    public void init(){
        Frame f = new Frame("Change Road Weather");
        selectRoad();
    }

    public void selectRoad(){

        JLabel roadLabel = new JLabel("Road: ");
        this.add(roadLabel);

        List<String> roadChoices = new ArrayList<>();
        for(Road r : _ctrl.getSimulator().getRoadMap().getRoads()){
            roadChoices.add(r.toString());
        }

       JComboBox roadList = new JComboBox((ComboBoxModel) roadChoices);
        this.add(roadList);

    }

}
