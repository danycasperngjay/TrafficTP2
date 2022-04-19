package simulator.view;

import simulator.control.Controller;
import simulator.model.*;
import simulator.model.Event;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {

    private static final long serialVersionUID = 1L;

    private static final int _JRADIUS = 10;

    private static final Color _BG_COLOR = Color.WHITE;
    private static final Color _JUNCTION_COLOR = Color.BLUE;
    private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
    private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
    private static final Color _RED_LIGHT_COLOR = Color.RED;

    private RoadMap _map;

    private Image _car; //car image
    private Image _sun, _rain, _wind, _storm, _cloud; // different weather images
    private Image _cont_0, _cont_1, _cont_2, _cont_3, _cont_4, _cont_5; //different contamination level images


    MapByRoadComponent(Controller ctrl){
        initGUI();
        ctrl.addObserver(this);
        //this._map = ctrl.getSimulator().getRoadMap();
        //this.setPreferredSize(new Dimension(300, 200));
    }

    private void initGUI(){
        //initialising image of the car
        _car = loadImage("car.png");
        //initialising images of the different weathers
        _sun = loadImage("sun.png");
        _rain = loadImage("rain.png");
        _wind = loadImage("wind.png");
        _storm = loadImage("storm.png");
        _cloud = loadImage("cloud.png");
        //initialising images of the different contamination levels
        _cont_0 = loadImage("cont_0.png");
        _cont_1 = loadImage("cont_1.png");
        _cont_2 = loadImage("cont_2.png");
        _cont_3 = loadImage("cont_3.png");
        _cont_4 = loadImage("cont_4.png");
        _cont_5 = loadImage("cont_5.png");
    }

    private Image loadImage(String img) {
        Image i = null;
        try {
            return ImageIO.read(new File("resources/icons/" + img));
        } catch (IOException e) {
        }
        return i;
    }

    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setColor(_BG_COLOR);
        g.clearRect(0, 0, getWidth(), getHeight());

        if (_map == null || _map.getJunctions().size() == 0) 
        	{
            g.setColor(Color.red);
            g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
        } else {
            updatePrefferedSize();
            drawMap(graphics);
        }
    }

    private void updatePrefferedSize() {
        int maxW = 200;
        int maxH = 200;
        for (Junction j : _map.getJunctions()) {
            maxW = Math.max(maxW, j.getX());
            maxH = Math.max(maxH, j.getY());
        }
        maxW += 20;
        maxH += 20;
        if (maxW > getWidth() || maxH > getHeight()) {
            setPreferredSize(new Dimension(maxW, maxH));
            setSize(new Dimension(maxW, maxH));
        }
    }

    private void drawMap(Graphics g) {
        drawRoads(g);
        drawVehicles(g);
        drawJunctions(g);
        drawWeather(g);
        drawContLevel(g);
    }


    private void drawRoads(Graphics g) {
        
        for (Road r : _map.getRoads()) {
        	int index = _map.getRoads().indexOf(r);
        	int x = 50;
            int y = index*100 + 70;
            
        	g.setColor(Color.BLACK);
			g.drawLine(x, y, x + 400, y);
            g.drawString(r.getId(), x + 200, y - 15);
        }
    }

    private void drawJunctions(Graphics g){
    	for (Road r : _map.getRoads()) {

    		int index = _map.getRoads().indexOf(r);
            int x = 50;
            int y = index*100 + 70;
           
             //circle representing the src junction
             g.setColor(_JUNCTION_COLOR);
             g.fillOval(x - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
             
             g.setColor(_JUNCTION_LABEL_COLOR);
             g.drawString(r.getSrc().getId(), x, y -15 );

             // Dest Junction
             x += 400;
             if (r.getDest().getGreenLightIndex() != -1) {
            	 if(r.getDest().getInRoads().get(r.getDest().getGreenLightIndex()) == r)
                    g.setColor(_GREEN_LIGHT_COLOR);   }
             else 
                    g.setColor(_RED_LIGHT_COLOR);

             g.fillOval(x - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
             g.setColor(_JUNCTION_LABEL_COLOR);
             g.drawString(r.getDest().getId(), x, y -15 );            
            }
        }    
    private void drawVehicles(Graphics g) {
    	for (Road r : _map.getRoads()) {

    		int index = _map.getRoads().indexOf(r);
            int x = 50;
            int y = index*100 + 70;
            int s = 25;
            for (Vehicle v : r.getVehicles()) {
            	if (v.getStatus() != VehicleStatus.ARRIVED) {
            		
            		x = 400*v.getLocation()/v.getRoad().getLength();
                //label of the vehicle (depending on its contamination class)
                int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
                g.setColor(new Color(0, vLabelColor, 0));

                //image of the car
                g.drawImage(_car, x + 20, y - 10, s, s, this);

                //vehicle's identifier
                g.drawString(v.getId(), x + 27, y - 10);
            	}
            }
    	}
    }
    

    private void drawWeather(Graphics g){
        for (Road r : _map.getRoads()){
            //get weather of the road
            Weather w = r.getWeather();
            int index = _map.getRoads().indexOf(r);
            //coordinates of the junctions (in order to position the weather on their right)
            int s = 35;
            int x = 550;
            int y = index*100 + 50;
            //adding the weather image corresponding to the weather condition
            if(w == Weather.SUNNY){
                g.drawImage(_sun, x, y, s, s, this);
            } else if(w == Weather.CLOUDY){
                g.drawImage(_cloud, x, y, s, s, this);
            } else if(w == Weather.STORM){
                g.drawImage(_storm, x, y, s, s, this);
            } else if(w == Weather.RAINY){
                g.drawImage(_rain, x, y, s, s, this);
            } else{
                g.drawImage(_wind, x, y, s, s, this);
            }
        }
    }

    private void drawContLevel(Graphics g){
        for(Road r : _map.getRoads()) {
            //coordinates of the junctions (in order to position the contamination levels on their right(after the weather))
        	int index = _map.getRoads().indexOf(r);
        	int s = 35;
            int x = 650;
            int y = index*100 + 50;

            //calculation of the contamination level on the r road
            int c = (int) Math.floor(Math.min((double) r.getTotalCO2() / (1.0 + (double) r.getContLimit()), 1.0) / 0.19);

            //image corresponding to the contamination level
            switch(c){
                case 0 : g.drawImage(_cont_0, x, y, s, s, this);
                case 1 : g.drawImage(_cont_1, x, y, s, s, this);
                case 2 : g.drawImage(_cont_2, x, y, s, s, this);
                case 3 : g.drawImage(_cont_3, x, y, s, s, this);
                case 4 : g.drawImage(_cont_4, x, y, s, s, this);
                case 5 : g.drawImage(_cont_5, x, y, s, s, this);
                default : return;
            }
        }
    }

    public void update(RoadMap map) {
        SwingUtilities.invokeLater(() -> {
            _map = map;
            repaint();
        });
    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
    	 update(map);
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
       
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        update(map);
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        update(map);
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        update(map);
    }

    @Override
    public void onError(String err) {

    }
}
