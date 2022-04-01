package simulator.model;

public class NewJunctionEvent extends Event {
	
	private Junction j;
	
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
			super(time);
			j = new Junction(id, lsStrategy, dqStrategy, xCoor, yCoor);
			j._id = id;
			j._lss = lsStrategy;
			j._dqs = dqStrategy;
			j.xCoor = xCoor;
			j.yCoor = yCoor;
			}

	@Override
	void execute(RoadMap map) {
		map.addJunction(j);	
	}

	public String toString(){
		return "New Junction '" + j._id + "'";
	}

}
