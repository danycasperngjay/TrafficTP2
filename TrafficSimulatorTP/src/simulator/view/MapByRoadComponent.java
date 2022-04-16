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
        this.setPreferredSize(new Dimension(300, 200));
    }

    private void initGUI(){
        //initialising image of the car
        _car = loadImage("car_front.png");
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

        if (_map == null || _map.getJunctions().size() == 0) {
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
    }


    private void drawRoads(Graphics g) {
        int i = 0; //index of the road
        for (Road r : _map.getRoads()) {
            //the line for the i-th road
            g.drawLine( 50, (i+1)*50, getWidth()-100, (i+1)*50);

            //road identifier
            g.drawString(r.getId(), 10, (i+1)*10);
            i++; //index increases to change roads
        }
    }

    private void drawJunctions(Graphics g){
        for (Junction j : _map.getJunctions()) {

            int x = j.getX();
            int y = j.getY();

            if (j == _map.getRoad(j.getId()).getSrc()) {
                //circle representing the junction
                g.setColor(_JUNCTION_COLOR);
            } else {
                if (j.getGreenLightIndex() != -1) {
                    g.setColor(_GREEN_LIGHT_COLOR);
                } else {
                    g.setColor(_RED_LIGHT_COLOR);
                }
            }
            g.fillOval(x - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
            //junction's identifier
            g.setColor(_JUNCTION_LABEL_COLOR);
            g.drawString(j.getId(), x, y);

        }
    }

    private void drawVehicles(Graphics g) {

        for (Vehicle v : _map.getVehicles()) {
            if (v.getStatus() != VehicleStatus.ARRIVED) {
                Road r = v.getRoad();

                //coordinates of the vehicle on the corresponding road
                int x1 = r.getSrc().getX();
                int y1 = r.getSrc().getY();
                int x2 = r.getDest().getX();
                int y2 = r.getDest().getY();

                int vX = x1 + (int) ((x2 - x1) * (double) v.getLocation() / (double) r.getLength());
                int vY = y1 + (int) ((y2 - y1) * (double) v.getLocation() / (double) r.getLength());

                //label of the vehicle (depending on its contamination class)
                int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
                g.setColor(new Color(0, vLabelColor, 0));

                //image of the car
                g.drawImage(_car, vX, vY - 6, 16, 16, this);

                //vehicle's identifier
                g.drawString(v.getId(), vX, vY - 6);

            }
        }
    }

    private void drawWeather(Graphics g){
        for (Road r : _map.getRoads()){
            //get weather of the road
            Weather w = r.getWeather();

            //coordinates of the junctions (in order to position the weather on their right)
            int x = r.getDest().getX();
            int y = r.getDest().getY();

            //adding the weather image corresponding to the weather condition
            if(w == Weather.SUNNY){
                g.drawImage(_sun, x+10, y, 32, 32, this);
            } else if(w == Weather.CLOUDY){
                g.drawImage(_cloud, x+10, y, 32, 32, this);
            } else if(w == Weather.STORM){
                g.drawImage(_storm, x+10, y, 32, 32, this);
            } else if(w == Weather.RAINY){
                g.drawImage(_rain, x+10, y, 32, 32, this);
            } else{
                g.drawImage(_wind, x+10, y, 32, 32, this);
            }

        }
    }

    private void drawContLevel(Graphics g){
        for(Road r : _map.getRoads()) {
            //coordinates of the junctions (in order to position the contamination levels on their right(after the weather))
            int x = r.getDest().getX();
            int y = r.getDest().getY();

            //calculation of the contamination level on the r road
            int c = (int) Math.floor(Math.min((double) r.getTotalCO2() / (1.0 + (double) r.getContLimit()), 1.0) / 0.19);

            //image corresponding to the contamination level
            switch(c){
                case 0 : g.drawImage(_cont_0, x+20, y, 32, 32, this);
                case 1 : g.drawImage(_cont_1, x+20, y, 32, 32, this);
                case 2 : g.drawImage(_cont_2, x+20, y, 32, 32, this);
                case 3 : g.drawImage(_cont_3, x+20, y, 32, 32, this);
                case 4 : g.drawImage(_cont_4, x+20, y, 32, 32, this);
                case 5 : g.drawImage(_cont_5, x+20, y, 32, 32, this);
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
    public void onAdvanceEnd(RoadMap roadMap, List<Event> events, int time) {

    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
        update(map);
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
