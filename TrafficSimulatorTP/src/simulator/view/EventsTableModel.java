package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	private List<Event> _events;
	private String[] _colNames = { "Time", "Desc." };

	private Controller _ctrl;

	public EventsTableModel(Controller ctrl) {
		_ctrl = ctrl;
		ctrl.addObserver(this);
		_events = null;
	}

	public void update() {
		// We need to notify changes, otherwise the table does not refresh.
		fireTableDataChanged();;
	}

	public void setEventsList(TrafficSimulator s) {
		_events = s.getEvents();
		update();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
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
		return _events == null ? 0 : _events.size();
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
				s = _events.get(rowIndex).getTime();
				;
				break;
			case 1:
				s = _events.get(rowIndex).toString();
				break;
		}
		return s;
	}

	@Override
	public void onAdvanceEnd(RoadMap roadMap, List<Event> events, int time) {

	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {

	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {

	}

	@Override
	public void onError(String err) {

	}
}
