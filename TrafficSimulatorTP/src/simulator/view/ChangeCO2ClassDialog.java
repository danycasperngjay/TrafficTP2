package simulator.view;

import simulator.control.Controller;
import simulator.model.RoadMap;
import simulator.model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class  ChangeCO2ClassDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    //new?
    private JPanel changeCO2;
    private JLabel instructions;

    private JPanel buttons;

    private JLabel vehicleTitle;
    private DefaultComboBoxModel<Vehicle> vehicleBox;
    private JComboBox<Vehicle> vehicles;

    private JLabel CO2Title;
    private DefaultComboBoxModel<Integer> co2Box;
    private JComboBox<Integer> co2s;

    private JLabel ticksTitle;
    private JSpinner ticks;

    private JPanel OkCancel;
    private JButton cancel;
    private JButton ok;

   volatile private int state = 0;


    private Controller _ctrl;

    public ChangeCO2ClassDialog(Frame parent, Controller ctrl){
        super(parent, true);
        _ctrl = ctrl;
        setTitle("Change CO2 Class");
        init();
    }

    public void init(){

        String[] vehiclesList = {};

        changeCO2 = new JPanel();
        changeCO2.setLayout(new BorderLayout());
        this.setContentPane(changeCO2);

        //instructions
        instructions = new JLabel("Schedule an event to change the CO2 class of a vehicle " +
                "after a given number of simulation ticks from now.", JLabel.CENTER);
        changeCO2.add(instructions, BorderLayout.NORTH);
        
        changeCO2.add(Box.createVerticalGlue());
        //select vehicle
        //JLabel vehicle = new JLabel("Vehicle:");
        //changeCO2.add(vehicle, BorderLayout.LINE_START);
        //ArrayList<String> vehicleChoices = new ArrayList<>();
        //for (Road r : _ctrl.getSimulator().getRoadMap().getRoads()) {
        //    vehicleChoices.add(r.toString());
        //}
        //vehiclesList = vehicleChoices.toArray(new String[0]);
        //JComboBox<String> comboVehicle = new JComboBox<String> (vehiclesList);
        //comboVehicle.setPreferredSize(new Dimension(60, 20));
        //changeCO2.add(comboVehicle, BorderLayout.LINE_START);

        //new panel
        buttons = new JPanel();
        buttons.setAlignmentX(CENTER_ALIGNMENT);
        changeCO2.add(buttons, BorderLayout.CENTER);

        //select vehicle
        vehicleTitle = new JLabel("Vehicle:", JLabel.CENTER);
        vehicleBox = new DefaultComboBoxModel<>();
        vehicles = new JComboBox<>(vehicleBox);
        vehicles.setVisible(true);
        buttons.add(vehicleTitle);
        buttons.add(vehicles);

        //select a CO2 class
        //JLabel co2Class = new JLabel("CO2 Class:");
        //changeCO2.add(co2Class, BorderLayout.CENTER);
        //String[] co2Choices = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        //JComboBox<String> comboCO2 = new JComboBox<String>(co2Choices);
        //co2Class.setPreferredSize(new Dimension(60, 20));
        //changeCO2.add(comboCO2, BorderLayout.CENTER);

        //select a co2 class
        CO2Title = new JLabel("CO2 Class:", JLabel.CENTER);
        co2Box = new DefaultComboBoxModel<>();
        co2s = new JComboBox<>(co2Box);
        co2s.setVisible(true);
        buttons.add(CO2Title);
        buttons.add(co2s);

        //ticks
        //JLabel ticksLabel = new JLabel("Ticks: ");
        //changeCO2.add(ticksLabel, BorderLayout.LINE_END);
        //JSpinner ticksSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 10000, 1));
        //ticksSpinner.setPreferredSize(new Dimension(80, 40));
        //changeCO2.add(ticksSpinner, BorderLayout.LINE_END);

        //ticks
        ticksTitle = new JLabel("Ticks: ", JLabel.CENTER);
        ticks = new JSpinner(new SpinnerNumberModel(10, 1, 1000, 1));
        ticks.setMinimumSize(new Dimension(80, 30));
        ticks.setMaximumSize(new Dimension(200, 30));
        ticks.setPreferredSize(new Dimension(80, 30));
        buttons.add(ticksTitle);
        buttons.add(ticks);

        //OkCancel Panel
        OkCancel = new JPanel();
        OkCancel.setAlignmentX(CENTER_ALIGNMENT);
        changeCO2.add(OkCancel, BorderLayout.SOUTH);

        //ok and cancel buttons
        cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = 0;
             //   changeCO2.setVisible(false);
            	ChangeCO2ClassDialog.this.setVisible(false);
            }
        });
        OkCancel.add(cancel);

        ok = new JButton("Ok");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//Vehicle selectedVehicle = (Vehicle) comboVehicle.getSelectedItem();
                //String selectedCO2 = (String) comboCO2.getSelectedItem();
                //for(Vehicle v : _ctrl.getSimulator().getRoadMap().getVehicles()){
                //    if(v == selectedVehicle ){
                //    	List<Pair<String,Integer>> contClass = new ArrayList<>();
                //    	Pair<String,Integer> pair = new Pair<String,Integer> (v.toString(),Integer.parseInt(selectedCO2));
                //    	contClass.add(pair);
                //    	SetContClassEvent setc = new SetContClassEvent(_ctrl.getSimulator().getTime() + ticksSpinner.getComponentCount(), contClass);
                //    	_ctrl.getSimulator().addEvent(setc);
                //    }
                //}
                //ChangeCO2ClassDialog.this.setVisible(false);

                if(vehicleBox.getSelectedItem() != null && co2Box.getSelectedItem() != null){
                    state = 1;
                    System.out.println(state);
                    ChangeCO2ClassDialog.this.setVisible(false);
                }

            }
        });
        OkCancel.add(ok);

        setPreferredSize(new Dimension(700, 200));
        pack();
        setResizable(true);
        setVisible(false);
    }

    public int start(RoadMap map){
        for(Vehicle v : map.getVehicles()){
            vehicleBox.addElement(v);
        }
        for(int i = 0; i < 11; i++){
            co2Box.addElement(i);
        }

        setLocation(getParent().getLocation().x + 350, getParent().getLocation().y + 350);
        setVisible(true);
        System.out.println(state);
        return state;

    }

    public Integer getTicks(){
        return (Integer) ticks.getValue();
    }

    public Integer getCO2Class(){
        return (Integer) co2Box.getSelectedItem();
    }

    public Vehicle getVehicle(){
        return (Vehicle) vehicleBox.getSelectedItem();
    }

}
