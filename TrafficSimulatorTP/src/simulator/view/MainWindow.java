package simulator.view;

import simulator.control.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JFrame {


	private Controller _ctrl;

    public MainWindow(Controller ctrl){
    	super("Traffic Simulator");
    	_ctrl = ctrl;
    	initGUI();
    }

    private void initGUI() {
    	//window Icon
   	 try {
			this.setIconImage(ImageIO.read(new File("resources/icons/car_front.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
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

    		// map for MapByRoadComponent

    		JPanel mapByRoadView = createViewPanel(new MapByRoadComponent(_ctrl), "Map By Road");
			mapByRoadView.setPreferredSize(new Dimension(500, 200));
		   	mapsPanel.add(mapByRoadView);
			mapByRoadView.setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Map By Road", TitledBorder.LEFT,
							TitledBorder.TOP));

    		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    		setPreferredSize(new Dimension(1700, 1000));
    		this.pack();
    		this.setVisible(true);
    		}


    private JPanel createViewPanel(JComponent c, String title) {
    	JPanel p = new JPanel( new BorderLayout() );
    	
    	Border b = BorderFactory.createLineBorder(Color.black, 3, true);
    	p.setBorder(b);
    	
    	p.add(new JScrollPane(c));
    	return p;
    }
}
