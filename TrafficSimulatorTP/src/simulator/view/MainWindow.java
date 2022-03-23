package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;



import java.awt.*;
import java.util.List;

public class MainWindow extends JFrame implements TrafficSimObserver {

    private Controller _ctrl;
    private JTextArea log;

    public MainWindow(Controller ctrl){
    	super("Traffic Simulator");
    	_ctrl = ctrl;
    	initGUI();
    }

    private void initGUI() {
    		JPanel mainPanel = new JPanel(new BorderLayout());
    		this.setContentPane(mainPanel);
    		mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
    		mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);
    		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
    		mainPanel.add(viewsPanel, BorderLayout.CENTER);
    		JPanel tablesPanel = new JPanel();
    		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
    		viewsPanel.add(tablesPanel);
    		JPanel mapsPanel = new JPanel();
    		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
    		viewsPanel.add(mapsPanel);
    		// tables
    		JPanel eventsView =
    		createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
    		eventsView.setPreferredSize(new Dimension(500, 200));
    		tablesPanel.add(eventsView);
    		// TODO add other tables
    		// ...
    		// maps
    		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
    		mapView.setPreferredSize(new Dimension(500, 400));
    		mapsPanel.add(mapView);
    		// TODO add a map for MapByRoadComponent
    		// ...
    		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    		this.pack();
    		this.setVisible(true);
    		}
//        log = new JTextArea();
//        log.setPreferredSize(new Dimension(400, 200));
//        mainPanel.add(log);
//
//        JButton step = new JButton("Step");
//        mainPanel.add(step, BorderLayout.PAGE_START);
//        step.addActionListener( (e) -> {
//            control.run(1, null);
//        });


    private JPanel createViewPanel(JComponent c, String title) {
    	JPanel p = new JPanel( new BorderLayout() );
    	// TODO add a framed border to p with title
    	p.add(new JScrollPane(c));
    	return p;
    }


    @Override
    public void onAdvanceEnd(RoadMap roadMap, List<Event> events, int time) {

    }

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
}
