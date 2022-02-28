package simulator.model;

import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {

	private List<Vehicle> aux;

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
	
		if (q.isEmpty())
    		return q;
		aux.add(q.get(0));

		return aux;
	}

}
