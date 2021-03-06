package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {

    private static final long serialVersionUID = 1L;

    private List<Road> _roads;
    private String[] _colNames = { "Id", "Length", "Weather", "Max. Speed", "Speed Limit",
            "Total CO2", "CO2 Limit"};

    public RoadsTableModel(Controller ctrl){
        _roads = new ArrayList<Road>();
        ctrl.addObserver(this);
    }

    public void update() {
        // We need to notify changes, otherwise the table does not refresh.
        fireTableDataChanged();;
    }

    public void setRoadsList(List<Road> roads){
        _roads = roads;
        update();
    }

    //this is for the column header
    @Override
    public String getColumnName(int col) {
        return _colNames[col];
    }

    @Override
    // this is for the number of columns
    public int getColumnCount() {
        return _colNames.length;
    }

    @Override
    // the number of row, like those in the events list
    public int getRowCount() {
        return _roads.size();
    }

    @Override
    // required method
    // this is how the table is going to be loaded from the ArrayList
    // the index of the arrayList is the row number because in this example
    // I want to list them.
    //
    // returns the value of a particular cell
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object s = null;
        switch (columnIndex) {
            case 0:
                s = _roads.get(rowIndex).getId();
                break;
            case 1:
                s = _roads.get(rowIndex).getLength();
                break;
            case 2:
                s = _roads.get(rowIndex).getWeather();
                break;
            case 3 :
                s = _roads.get(rowIndex).getMaxSpeed();
                break;
            case 4 :
                s = _roads.get(rowIndex).getSpeedLimit();
                break;
            case 5:
                s = _roads.get(rowIndex).getTotalCO2();
                break;
            case 6:
                s = _roads.get(rowIndex).getContLimit();
                break;
        }
        return s;
    }

    @Override
    public void onAdvanceEnd(RoadMap roadMap, List<Event> events, int time) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setRoadsList(roadMap.getRoads());
            }
        });

    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setRoadsList(map.getRoads());
            }
        });
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setRoadsList(map.getRoads());
            }
        });
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setRoadsList(map.getRoads());
            }
        });
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setRoadsList(map.getRoads());
            }
        });
    }

    @Override
    public void onError(String err) {

    }
}
