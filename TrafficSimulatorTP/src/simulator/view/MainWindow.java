package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWindow extends JFrame implements TrafficSimObserver {

    private Controller control;
    private JTextArea log;

    public MainWindow(Controller c){
        super("[.]");
        control = c;
        initGUI();
        control.addObserver(this);
    }

    private void initGUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        this.setContentPane(mainPanel);

        log = new JTextArea();
        log.setPreferredSize(new Dimension(400, 200));
        mainPanel.add(log);

        JButton step = new JButton("Step");
        mainPanel.add(step, BorderLayout.PAGE_START);
        step.addActionListener( (e) -> {
            control.run(1, null);
        });

        pack();
        setVisible(true);
    }

    @Override
    public void onAdvanceEnd(RoadMap roadMap, List<Event> events, int time) {

    }
}
