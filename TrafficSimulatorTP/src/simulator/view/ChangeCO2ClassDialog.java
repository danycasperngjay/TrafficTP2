package simulator.view;

import simulator.control.Controller;
import simulator.model.RoadMap;
import simulator.model.Vehicle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class  ChangeCO2ClassDialog extends JDialog {

    private static final long serialVersionUID = 1L;

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
    	
    	//window Icon
    	 try {
 			this.setIconImage(ImageIO.read(new File("resources/icons/co2class.png")));
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
    	 
    	//main panel
        changeCO2 = new JPanel();
        changeCO2.setLayout(new BorderLayout());
        this.setContentPane(changeCO2);

        //instructions text
        instructions = new JLabel("Schedule an event to change the CO2 class of a vehicle " +
                "after a given number of simulation ticks from now.", JLabel.CENTER);
        changeCO2.add(instructions, BorderLayout.NORTH);

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

        //select a co2 class
        CO2Title = new JLabel("CO2 Class:", JLabel.CENTER);
        co2Box = new DefaultComboBoxModel<>();
        co2s = new JComboBox<>(co2Box);
        co2s.setVisible(true);
        buttons.add(CO2Title);
        buttons.add(co2s);

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
            	ChangeCO2ClassDialog.this.setVisible(false);
            }
        });
        OkCancel.add(cancel);

        ok = new JButton("Ok");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
