package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {

    private static final long serialVersionUID = 1L;

    private List<Junction> _junctions;
    private String[] _colNames = { "Id", "Green", "Queues"};

    private Controller _ctrl;

    public JunctionsTableModel(Controller ctrl){
        _ctrl = ctrl;
        _junctions = new ArrayList<Junction>();
        ctrl.addObserver(this);

    }

    public void update() {
        // We need to notify changes, otherwise the table does not refresh.
        fireTableDataChanged();;
    }

    public void setJunctionList(List<Junction> junctions){
        _junctions = junctions;
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
        return _junctions.size();
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
        String string = "  ";
        switch (columnIndex) {
            case 0:
                s = _junctions.get(rowIndex).getId();
                break;
            case 1:
                if(_junctions.get(rowIndex).getGreenLightIndex() == -1) {
                    s = "NONE";
                } else {
                    s = _junctions.get(rowIndex).getInRoads().get(_junctions.get(rowIndex).getGreenLightIndex());
                }
                break;
            case 2:
            	for(int i= 0; i < _junctions.get(rowIndex).getInRoads().size(); i++)
            	{
            		string += _junctions.get(rowIndex).getInRoads().get(i) + " :" + _junctions.get(rowIndex).getQueues().get(i);
            	}
            	s = string;
                break;
        }
        return s;
    }

    @Override
    public void onAdvanceEnd(RoadMap roadMap, List<Event> events, int time) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setJunctionList(roadMap.getJunctions());
            }
        });
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setJunctionList(map.getJunctions());
            }
        });
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setJunctionList(map.getJunctions());
            }
        });
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setJunctionList(map.getJunctions());
            }
        });
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setJunctionList(map.getJunctions());
            }
        });
    }

    @Override
    public void onError(String err) {

    }
}
