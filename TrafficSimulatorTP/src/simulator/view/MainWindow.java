package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
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
			Border _defaultBorder = BorderFactory.createLineBorder(Color.black, 1);
			//events table model
    		JPanel eventsView =
    		createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
    		eventsView.setPreferredSize(new Dimension(500, 200));
    		tablesPanel.add(eventsView);
			eventsView.setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Events", TitledBorder.LEFT,
					TitledBorder.TOP));

			//vehicles table model
			JPanel vehiclesView =
					createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles");
			vehiclesView.setPreferredSize(new Dimension(500, 200));
			tablesPanel.add(vehiclesView);
			vehiclesView.setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Vehicles", TitledBorder.LEFT,
					TitledBorder.TOP));

			//roads table model
			JPanel roadsView =
					createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Roads");
			roadsView.setPreferredSize(new Dimension(500, 200));
			tablesPanel.add(roadsView);
			roadsView.setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Roads", TitledBorder.LEFT,
					TitledBorder.TOP));

			//junctions table model
			JPanel junctionsView =
					createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Junctions");
			junctionsView.setPreferredSize(new Dimension(500, 200));
			tablesPanel.add(junctionsView);
			junctionsView.setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Junctions",
					TitledBorder.LEFT, TitledBorder.TOP));

    		// maps
    		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
    		mapView.setPreferredSize(new Dimension(500, 200));
    		mapsPanel.add(mapView);
			mapView.setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Map", TitledBorder.LEFT,
					TitledBorder.TOP));

    		// TODO add a map for MapByRoadComponent

    		JPanel mapByRoadView = createViewPanel(new MapByRoadComponent(_ctrl), "Map By Road");
			mapByRoadView.setPreferredSize(new Dimension(500, 200));
		   	mapsPanel.add(mapByRoadView);
			mapByRoadView.setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Map By Road", TitledBorder.LEFT,
							TitledBorder.TOP));

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
