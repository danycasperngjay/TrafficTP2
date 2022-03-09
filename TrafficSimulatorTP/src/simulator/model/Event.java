package simulator.model;

public abstract class Event implements Comparable<Event> {

	protected int _time;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("Time must be positive (" + time + ")");
		else
			_time = time;
	}

	int getTime() {
		return _time;
	}

	@Override
	public int compareTo(Event o) {
		if(this.getTime() < o.getTime())
    		return -1;
    	else if (this.getTime() > o.getTime())
    		return 1;
    	else
    		return 0;
	}

	abstract void execute(RoadMap map);
}
