package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {

	private List<Vehicle> aux;

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		
		aux = new ArrayList<>();
	
		if (q.isEmpty())
			return aux;
			 
		aux.add(q.get(0));

		return aux;
	}

}
