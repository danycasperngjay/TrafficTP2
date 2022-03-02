package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy {

	private List<Vehicle> aux;
	
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		
		  aux = new ArrayList<>();
		  
		  for (Vehicle temp : q) 
		  {
	            aux.add(temp);
		  }
			
		return aux;
	}

}
