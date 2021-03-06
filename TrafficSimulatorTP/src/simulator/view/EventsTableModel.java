package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;


	private List<Event> _events;
	private String[] _colNames = { "Time", "Desc." };

	private Controller _ctrl;

	public EventsTableModel(Controller ctrl) {
		_ctrl = ctrl;
		_events = new ArrayList<>();
		_ctrl.addObserver(this);

	}

	public void update() {
		// We need to notify changes, otherwise the table does not refresh.
		fireTableDataChanged();
	}

	public void setEventsList(List<Event> events){
		_events = events;
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
		return _events.size();
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
				break;
			case 1:
				s = _events.get(rowIndex).toString();
				break;
		}
		return s;
	}

	@Override
	public void onAdvanceEnd(RoadMap roadMap, List<Event> events, int time) {
		List<Event> aux = new ArrayList<>();
		
		for (Event e : events) {
			if (e.getTime() > time)
				aux.add(e);
		}
		_events = aux;
		update();
		
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setEventsList(events);
			}
		});
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setEventsList(events);
			}
		});

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setEventsList(events);
			}
		});
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setEventsList(events);
			}
		});
	}

	@Override
	public void onError(String err) {

	}
}
