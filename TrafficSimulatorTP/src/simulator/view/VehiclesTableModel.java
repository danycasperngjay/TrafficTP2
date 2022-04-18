package simulator.view;

import simulator.control.Controller;
import simulator.model.*;

import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;
import java.util.List;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

    private static final long serialVersionUID = 1L;

    private List<Vehicle> _vehicles;
    private String[] _colNames = { "Id", "Location", "Itinerary", "CO2 Class", "Max. Speed",
                                    "Speed", "Total CO2", "Distance"};

    private Controller _ctrl;

    public VehiclesTableModel(Controller ctrl){
        _ctrl = ctrl;
        ctrl.addObserver(this);
        _vehicles = ctrl.getSimulator().getRoadMap().getVehicles();
    }

    public void update() {
        // We need to notify changes, otherwise the table does not refresh.
        fireTableDataChanged();;
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
        return _vehicles.size();
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
                s = _vehicles.get(rowIndex).getId();
                break;
            case 1:
                if(_vehicles.get(rowIndex).getStatus() == VehicleStatus.PENDING){
                    s = "Pending";
                } else if(_vehicles.get(rowIndex).getStatus() == VehicleStatus.TRAVELING){
                    s = _vehicles.get(rowIndex).getRoad() + ":" + _vehicles.get(rowIndex).getLocation();
                } else if(_vehicles.get(rowIndex).getStatus() == VehicleStatus.WAITING){
                    s = "Waiting:" + _vehicles.get(rowIndex).getLastSeenJunction();
                } else {
                    s = "Arrived";
                }
                break;
            case 2:
                s = _vehicles.get(rowIndex).getItinerary();
                break;
            case 3 :
                s = _vehicles.get(rowIndex).getContClass();
                break;
            case 4 :
                s = _vehicles.get(rowIndex).getMaxSpeed();
                break;
            case 5:
                s = _vehicles.get(rowIndex).getSpeed();
                break;
            case 6:
                s = _vehicles.get(rowIndex).getTotalCO2();
                break;
            case 7:
                s = _vehicles.get(rowIndex).getTotalTraveledDistance();
                break;
        }
        return s;
    }

    @Override
    public void onAdvanceEnd(RoadMap roadMap, List<Event> events, int time) {

		update();
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
    	update();
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
    	_vehicles.clear();
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
    	update();
    }

    @Override
    public void onError(String err) {

    }
}
