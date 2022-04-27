package simulator.view;

import simulator.control.Controller;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.Weather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeWeatherDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private JPanel changeWeather;
    private JLabel instructions;

    private JLabel roadTitle;
    private DefaultComboBoxModel<Road> roadDefaultBox;
    private JComboBox<Road> roads;

    private JLabel weatherTitle;
    private DefaultComboBoxModel<Weather> weatherDefaultBox;
    private JComboBox<Weather> weathers;

    private JLabel ticksTitle;
    private JSpinner ticks;

    private JPanel buttons;
    private JPanel okCancel;
    private JButton cancel;
    private JButton ok;

    private int state = 0;


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

        changeWeather = new JPanel();
        changeWeather.setLayout(new FlowLayout());
        changeWeather.setPreferredSize(new Dimension(500, 200));
        this.setContentPane(changeWeather);

        //instructions
        instructions = new JLabel("Schedule an event to change the weather of a road " +
                "after a given number of simulation ticks from now.");
        changeWeather.add(instructions, BorderLayout.PAGE_START);
        changeWeather.add(Box.createRigidArea(new Dimension(0, 20)));

        //buttons panel
        buttons = new JPanel();
        buttons.setAlignmentX(CENTER_ALIGNMENT);
        changeWeather.add(buttons);

        //select road
        roadTitle = new JLabel("Road:", JLabel.CENTER);
        roadDefaultBox = new DefaultComboBoxModel<Road>();
        roads = new JComboBox<Road>(roadDefaultBox);
        roads.setVisible(true);
        buttons.add(roadTitle);
        buttons.add(roads);

        //select a weather
        weatherTitle = new JLabel("Weather:", JLabel.CENTER);
        weatherDefaultBox = new DefaultComboBoxModel<Weather>();
        weathers = new JComboBox<Weather>(weatherDefaultBox);
        weathers.setVisible(true);
        buttons.add(weatherTitle);
        buttons.add(weathers);

        //ticks
        ticksTitle = new JLabel("Ticks: ", JLabel.CENTER);
        ticks = new JSpinner(new SpinnerNumberModel(10, 1, 1000, 1));
        ticks.setMinimumSize(new Dimension(80, 30));
        ticks.setMaximumSize(new Dimension(200, 30));
        ticks.setPreferredSize(new Dimension(80, 30));
        buttons.add(ticksTitle);
        buttons.add(ticks);

        //ok Cancel panel
        okCancel = new JPanel();
        okCancel.setAlignmentX(CENTER_ALIGNMENT);
        changeWeather.add(okCancel);
        
        //ok and cancel buttons
        cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = 0;
                ChangeWeatherDialog.this.setVisible(false);
            }
        });
        okCancel.add(cancel);
       
        ok = new JButton("Ok");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(weatherDefaultBox.getSelectedItem() != null && roadDefaultBox.getSelectedItem() != null){
                    state = 0;
                    ChangeWeatherDialog.this.setVisible(false);
                }
            }
        });
        okCancel.add(ok);

        setPreferredSize(new Dimension(500, 220));
        pack();
        setResizable(false);
        setVisible(false);

    }

    public int start(RoadMap map){
        for(Road r : map.getRoads()){
            roadDefaultBox.addElement(r);
        }
        for(Weather w : Weather.values()){
            weatherDefaultBox.addElement(w);
        }

        setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);
        setVisible(true);

        return state;
    }

    public Integer getTicks(){
        return (Integer) ticks.getValue();
    }

    public Weather getWeather(){
        return (Weather) weatherDefaultBox.getSelectedItem();
    }

    public Road getRoad(){
        return (Road) roadDefaultBox.getSelectedItem();
    }

}
